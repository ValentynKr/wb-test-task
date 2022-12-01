package com.whitebit.client;

import com.whitebit.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import java.io.IOException;
import java.net.URI;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */

@ClientEndpoint
public class WebsocketClientEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketClientEndpoint.class);

    private Session userSession = null;
    private MessageProcessor messageProcessor;

    public WebsocketClientEndpoint(URI endpointURI) {

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, endpointURI);
        } catch (DeploymentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        LOGGER.info("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        LOGGER.info("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageProcessor != null) {
            messageProcessor.process(message);
        }
    }

    /**
     * register message processor
     *
     * @param messageProcessor
     */
    public void addMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    /**
     * Send a message.
     *
     * @param message message to be sent
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
}
