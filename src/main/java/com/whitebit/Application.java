package com.whitebit;

import com.google.gson.Gson;
import com.whitebit.cache.DepthCache;
import com.whitebit.client.WebsocketClientEndpoint;
import com.whitebit.constants.EndpointURL;
import com.whitebit.constants.Messages;
import com.whitebit.handler.HandlerStrategy;
import com.whitebit.processor.MessageProcessor;
import com.whitebit.service.PingService;
import com.whitebit.service.UIService;
import com.whitebit.service.UpdateEventService;
import com.whitebit.template.TemplateResolver;
import com.whitebit.template.impl.ConsoleTemplateResolver;
import com.whitebit.view.ConsoleViewResolver;
import com.whitebit.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        LOGGER.info("Starting the application");
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(EndpointURL.WHITEBIT_WEBSOCKET_ENDPOINT));

            //init application`s components
            DepthCache depthCache = new DepthCache();
            ViewResolver viewResolver = new ConsoleViewResolver(depthCache);
            TemplateResolver templateResolver = new ConsoleTemplateResolver();

            UIService uiService = new UIService(depthCache, viewResolver, templateResolver);
            UpdateEventService updateEventService = new UpdateEventService(depthCache);

            HandlerStrategy handlerStrategy = new HandlerStrategy(updateEventService);

            Gson gson = new Gson();
            MessageProcessor messageProcessor = new MessageProcessor(gson, handlerStrategy);
            PingService pingService = new PingService(clientEndPoint);
            clientEndPoint.addMessageProcessor(messageProcessor);

            //start pinging endpoint with time interval
            pingService.doPing(5_000);
            // send message to websocket for subscribe
            clientEndPoint.sendMessage(Messages.SUBSCRIBE_MESSAGE);
            //start showing best deals
            uiService.showMessages(3_000, 10_000);

            // wait to demonstrate how app works
            Thread.sleep(25_000);
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}