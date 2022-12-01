package com.whitebit.handler.impl;

import com.whitebit.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class PingPongHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingPongHandler.class);

    private static final String EXPECTED_PING_RESPONSE_RESULT = "pong";

    private static final int DEFAULT_ATTEMPTS_QTY = 3;

    private int retryCounter = DEFAULT_ATTEMPTS_QTY;

    private boolean previousAttemptFailed = false;

    @Override
    public void handleMessage(Map<String, Object> messageParsed) {
        String pingResult = messageParsed.get("result").toString();
        LOGGER.info("Ping result is [" + pingResult + "]");
        if (isPingSuccessful(pingResult)) {
            if (previousAttemptFailed) {
                retryCounter = DEFAULT_ATTEMPTS_QTY;
                previousAttemptFailed = false;
            }
        } else {
            retryCounter--;
            previousAttemptFailed = true;
        }

        if (retryCounter == 0) {
            LOGGER.error("Endpoint is unavailable. App cannot work any longer.");
            System.exit(0);
        }
    }

    private static boolean isPingSuccessful(String result) {
        return Objects.equals(EXPECTED_PING_RESPONSE_RESULT, result);
    }
}
