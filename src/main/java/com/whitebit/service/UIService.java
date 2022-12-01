package com.whitebit.service;

import com.whitebit.cache.DepthCache;
import com.whitebit.constants.Messages;
import com.whitebit.template.ConsoleTemplates;
import com.whitebit.template.TemplateResolver;
import com.whitebit.view.ShowMessagesTask;
import com.whitebit.view.ViewResolver;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class UIService {

    private DepthCache depthCache;

    private ViewResolver viewResolver;

    private TemplateResolver templateResolver;

    public UIService(DepthCache depthCache, ViewResolver viewResolver, TemplateResolver templateResolver) {
        this.viewResolver = viewResolver;
        this.depthCache = depthCache;
        this.templateResolver = templateResolver;
    }

    public void showMessages(long delay, long interval) {
        TimerTask showMessages = new ShowMessagesTask(depthCache, viewResolver, templateResolver);
        Timer timer = new Timer(true);
        timer.schedule(showMessages, delay, interval);

    }
}
