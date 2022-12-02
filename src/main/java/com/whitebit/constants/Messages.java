package com.whitebit.constants;

public class Messages {

    public static final int PING_PONG_ID = 1;

    public static final int SUBSCRIBE_ID = 2;

    public static final String PING_REQUEST = "{" +
            "\"id\":" + PING_PONG_ID +"," +
            "\"method\": \"ping\"," +
            "\"params\": []" +
            "}";

    public static final String SUBSCRIBE_MESSAGE = "{" +
            "\"id\":" + SUBSCRIBE_ID + "," +
            "\"method\": \"depth_subscribe\"," +
            "\"params\": [\"BTC_USDT\", 100, \"0\", true]" +
            "}";

}
