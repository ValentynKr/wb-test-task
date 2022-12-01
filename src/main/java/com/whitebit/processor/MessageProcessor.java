package com.whitebit.processor;

import com.google.gson.Gson;
import com.whitebit.constants.Messages;
import com.whitebit.handler.HandlerStrategy;
import com.whitebit.handler.MessageHandler;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MessageProcessor {

    private Gson gson;
    private Map<Integer, String> responseWithIdTypes;
    private HandlerStrategy handlerStrategy;

    public MessageProcessor(Gson gson, HandlerStrategy handlerStrategy) {
        this.gson = gson;
        this.handlerStrategy = handlerStrategy;
        initResponseWithIdTypes();
    }

    private void initResponseWithIdTypes() {
        responseWithIdTypes = new HashMap<>();
        responseWithIdTypes.put(Messages.PING_PONG_ID, "pingPong");
        responseWithIdTypes.put(Messages.SUBSCRIBE_ID, "subscribe");
    }

    public void process(String message) {
//        LoggerFactory.getLogger(this.getClass()).debug("$$$" + message);
        Map<String, Object> messageParsed = gson.fromJson(message, Map.class);

        String messageType = getMessageType(messageParsed);
        MessageHandler messageHandler = handlerStrategy.getMessageHandler(messageType);
        messageHandler.handleMessage(messageParsed);
    }

    private String getMessageType(Map<String, Object> messageParsed) {
        Object id = messageParsed.get("id");
        if (id != null) {
            int idInt = ((Double) id).intValue();
            return responseWithIdTypes.get(idInt);
        } else {
            return messageParsed.get("method").toString();
        }
    }
}
