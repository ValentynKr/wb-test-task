package com.whitebit.template;

import java.math.BigDecimal;
import java.util.Map;

public interface TemplateResolver {

    String resolve(String format, Map.Entry<BigDecimal, BigDecimal> entry);
}
