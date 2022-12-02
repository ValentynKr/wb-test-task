package com.whitebit.handler.impl;

import com.whitebit.handler.MessageHandler;
import com.whitebit.service.UpdateEventService;

import java.util.List;
import java.util.Map;

public class UpdateEventMessageHandler implements MessageHandler {

    private UpdateEventService updateEventService;

    public UpdateEventMessageHandler(UpdateEventService updateEventService) {
        this.updateEventService = updateEventService;
    }

    @Override
    public void handleMessage(Map<String, Object> messageParsed) {
        List<Object> params = ((List<Object>) messageParsed.get("params"));

        boolean isFullReload = ((boolean) params.get(0));
        Map<String, List<List<String>>> asksAndBids = ((Map) params.get(1));

        List<List<String>> asks = asksAndBids.get("asks");
        List<List<String>> bids = asksAndBids.get("bids");

        //sometimes message does not contain asks or bids so need null safety here
        if (asks != null) {
            if (isFullReload) {
                updateEventService.reloadAsks(asks);
            } else {
                updateEventService.updateAsks(asks);
            }
        }
        if (bids != null) {
            if (isFullReload) {
                updateEventService.reloadBids(bids);
            } else{
                updateEventService.updateBids(bids);
            }
        }
    }
}
