package com.whitebit.handler;

import com.whitebit.handler.impl.PingPongHandler;
import com.whitebit.handler.impl.SimpleMessageHandler;
import com.whitebit.handler.impl.UpdateEventMessageHandler;
import com.whitebit.service.UpdateEventService;

import java.util.HashMap;
import java.util.Map;

public class HandlerStrategy {

    private Map<String, MessageHandler> messageHandlers;

    private MessageHandler defaultHandler;

    public HandlerStrategy(UpdateEventService updateEventService) {
        defaultHandler = new SimpleMessageHandler();
        this.messageHandlers = new HashMap<>();
        messageHandlers.put("depth_update", new UpdateEventMessageHandler(updateEventService));
        messageHandlers.put("pingPong", new PingPongHandler());
    }

    public MessageHandler getMessageHandler(String handlerName) {
        return messageHandlers.getOrDefault(handlerName, defaultHandler);
    };
}
