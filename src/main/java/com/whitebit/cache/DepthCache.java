package com.whitebit.cache;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class DepthCache {

    private NavigableMap<BigDecimal, BigDecimal> asksStorage;

    private NavigableMap<BigDecimal, BigDecimal> bidsStorage;

    public DepthCache() {
        asksStorage = new ConcurrentSkipListMap<>();
        bidsStorage = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
    }

    public Map.Entry<BigDecimal, BigDecimal> getBestAsk() {
        return asksStorage.firstEntry();
    }

    public Map.Entry<BigDecimal, BigDecimal> getBestBid() {
        return bidsStorage.firstEntry();
    }

    public void updateAsks(List<List<String>> asks) {
        doUpdate(asks, asksStorage);
    }

    public void updateBids(List<List<String>> bids) {
        doUpdate(bids, bidsStorage);
    }

    private void doUpdate(List<List<String>> data, NavigableMap<BigDecimal, BigDecimal> storage) {
        Set<Map.Entry<BigDecimal, BigDecimal>> entries = getEntrySet(data);

        storage.entrySet().retainAll(entries);

        putEntriesToStorage(storage, entries);
    }

    public void reloadAsksCache(List<List<String>> asks) {
        doReload(asks, asksStorage);
    }

    public void reloadBidsCache(List<List<String>> bids) {
        doReload(bids, bidsStorage);
    }

    private void doReload(List<List<String>> data, NavigableMap<BigDecimal, BigDecimal> storage) {
        Set<Map.Entry<BigDecimal, BigDecimal>> entries = getEntrySet(data);
        storage.clear();
        putEntriesToStorage(storage, entries);

    }

    private void putEntriesToStorage(NavigableMap<BigDecimal, BigDecimal> storage, Set<Map.Entry<BigDecimal, BigDecimal>> entries) {
        entries.stream()
                .parallel()
                .forEach(entry -> storage.put(entry.getKey(), entry.getValue()));
    }

    private static Set<Map.Entry<BigDecimal, BigDecimal>> getEntrySet(List<List<String>> data) {
        return data.stream()
                .parallel()
                .collect(Collectors.toMap(ask -> new BigDecimal(ask.get(0)), ask -> new BigDecimal(ask.get(1))))
                .entrySet();
    }
}
