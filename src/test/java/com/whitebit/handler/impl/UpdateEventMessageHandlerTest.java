package com.whitebit.handler.impl;

import com.google.gson.internal.LinkedTreeMap;
import com.whitebit.service.UpdateEventService;
import com.whitebit.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.whitebit.util.TestUtil.getListOfDeals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateEventMessageHandlerTest {



    @Test
    void shouldHandleMessageWithFullReload() {
        UpdateEventService mockUpdateEventService = Mockito.mock(UpdateEventService.class);
        UpdateEventMessageHandler updateEventMessageHandler = new UpdateEventMessageHandler(mockUpdateEventService);
        Map<String, Object> mockMessageParsed = Mockito.mock(Map.class);
        List<List<String>> asks = getListOfDeals(
                "14.0761", "2.0007",
                "13.0001", "5.0007",
                "15.0001", "1.0007");

        List<List<String>> bids = getListOfDeals(
                "14.0755", "1.0012",
                "11.0001", "3.0007",
                "16.0001", "0.0007");

        Map<String, List<List<String>>> asksAndBids = new LinkedTreeMap<>();
        asksAndBids.put("asks", asks);
        asksAndBids.put("bids", bids);
        boolean isFullReload = true;

        when(mockMessageParsed.get("params")).thenReturn(Arrays.asList(isFullReload, asksAndBids, "BTC_USDT"));

        updateEventMessageHandler.handleMessage(mockMessageParsed);

        verify(mockUpdateEventService).reloadAsks(asks);
        verify(mockUpdateEventService).reloadBids(bids);
    }

    @Test
    void shouldHandleMessageWithoutFullReload() {
        UpdateEventService mockUpdateEventService = Mockito.mock(UpdateEventService.class);
        UpdateEventMessageHandler updateEventMessageHandler = new UpdateEventMessageHandler(mockUpdateEventService);
        Map<String, Object> mockMessageParsed = Mockito.mock(Map.class);

        List<List<String>> asks = getListOfDeals(
                "14.0761", "2.0007",
                "13.0001", "5.0007",
                "15.0001", "1.0007");

        List<List<String>> bids = getListOfDeals(
                "14.0755", "1.0012",
                "11.0001", "3.0007",
                "16.0001", "0.0007");

        Map<String, List<List<String>>> asksAndBids = new LinkedTreeMap<>();
        asksAndBids.put("asks", asks);
        asksAndBids.put("bids", bids);
        boolean isFullReload = false;

        when(mockMessageParsed.get("params")).thenReturn(Arrays.asList(isFullReload, asksAndBids, "BTC_USDT"));

        updateEventMessageHandler.handleMessage(mockMessageParsed);

        verify(mockUpdateEventService).updateAsks(asks);
        verify(mockUpdateEventService).updateBids(bids);
    }
}