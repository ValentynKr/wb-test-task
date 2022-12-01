package com.whitebit.handler.impl;

import com.whitebit.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SimpleMessageHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMessageHandler.class);

    @Override
    public void handleMessage(Map<String, Object> messageParsed) {
        LOGGER.info(messageParsed.toString());
    }
}
