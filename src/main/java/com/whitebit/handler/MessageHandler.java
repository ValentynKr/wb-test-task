package com.whitebit.handler;

import java.util.Map;

public interface MessageHandler {

    void handleMessage(Map<String, Object> messageParsed);
}