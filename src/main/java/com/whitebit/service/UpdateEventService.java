package com.whitebit.service;

import com.whitebit.cache.DepthCache;

import java.math.BigDecimal;
import java.util.List;
import java.util.NavigableMap;

public class UpdateEventService {

    private DepthCache depthCache;

    public UpdateEventService(DepthCache depthCache) {
        this.depthCache = depthCache;
    }

    public void updateAsks(List<List<String>> asks) {
        depthCache.updateAsks(asks);
    }

    public void updateBids(List<List<String>> bids) {
        depthCache.updateBids(bids);
    }

    public void reloadAsks(List<List<String>> asks) {
        depthCache.reloadAsksCache(asks);
    }

    public void reloadBids(List<List<String>> bids) {
        depthCache.reloadBidsCache(bids);
    }
}
