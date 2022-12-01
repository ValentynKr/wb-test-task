package com.whitebit.service;

import com.whitebit.client.WebsocketClientEndpoint;
import com.whitebit.constants.Messages;

public class PingService {

    private final WebsocketClientEndpoint websocketClientEndpoint;
    private Thread pingThread;

    public PingService(WebsocketClientEndpoint websocketClientEndpoint) {
        this.websocketClientEndpoint = websocketClientEndpoint;
    }

    public void doPing(long interval) {
        pingThread = new Thread(() -> {
            while (!pingThread.isInterrupted()) {
                websocketClientEndpoint.sendMessage(Messages.PING_REQUEST);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        pingThread.setDaemon(true);
        pingThread.start();
    }
}
