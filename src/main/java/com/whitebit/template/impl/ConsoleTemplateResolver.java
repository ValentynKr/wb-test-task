package com.whitebit.template.impl;

import com.whitebit.template.TemplateResolver;

import java.math.BigDecimal;
import java.util.Map;

public class ConsoleTemplateResolver implements TemplateResolver {
    @Override
    public String resolve(String format, Map.Entry<BigDecimal, BigDecimal> entry) {
        return String.format(format, entry.getKey().toString(), entry.getValue().toString());
    }
}
