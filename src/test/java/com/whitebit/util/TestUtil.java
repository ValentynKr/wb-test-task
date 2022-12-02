package com.whitebit.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtil {

    public static List<List<String>> getListOfDeals(String... args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Number of argument should be even. " +
                    "In other words args.length % 2 == 0 should be true");
        }
        List<List<String>> result = new ArrayList();
        for (int i = 0; i < args.length; i += 2) {
            result.add(Arrays.asList(args[i], args[i + 1]));
        }
        return result;
    }
}
