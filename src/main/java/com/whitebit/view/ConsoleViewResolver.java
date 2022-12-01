package com.whitebit.view;

import com.whitebit.cache.DepthCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleViewResolver implements ViewResolver {

    private DepthCache depthCache;
    private static final Logger LOGGER = LoggerFactory.getLogger("STDOUT");

    public ConsoleViewResolver(DepthCache depthCache) {
        this.depthCache = depthCache;
    }

    @Override
    public void show(String message) {

        LOGGER.info(message);
    }
}
