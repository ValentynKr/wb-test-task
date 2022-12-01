package com.whitebit.view;

import com.whitebit.cache.DepthCache;
import com.whitebit.template.ConsoleTemplates;
import com.whitebit.template.TemplateResolver;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TimerTask;

public class ShowMessagesTask extends TimerTask {

    private DepthCache depthCache;

    private ViewResolver viewResolver;

    private TemplateResolver templateResolver;

    public ShowMessagesTask(DepthCache depthCache, ViewResolver viewResolver, TemplateResolver templateResolver) {
        this.depthCache = depthCache;
        this.viewResolver = viewResolver;
        this.templateResolver = templateResolver;
    }

    @Override
    public void run() {
        Map.Entry<BigDecimal, BigDecimal> bestBid = depthCache.getBestBid();
        Map.Entry<BigDecimal, BigDecimal> bestAsk = depthCache.getBestAsk();

        String messageForBestBid = templateResolver.resolve(ConsoleTemplates.BEST_BID_FORMAT, bestBid);
        String messageForBestAsk = templateResolver.resolve(ConsoleTemplates.BEST_ASK_FORMAT, bestAsk);

        viewResolver.show(messageForBestBid);
        viewResolver.show(messageForBestAsk);
//        LoggerFactory.getLogger("TEST").debug(depthCache.getAll());
    }
}
