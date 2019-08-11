package com.tz_tech.module.common.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class OrderStaticData {

    public final static Map<String,String> ORDER_STATE = ImmutableMap.<String,String>builder()
            .put("10F","已竣工")
            .put("10R","已退单")
            .build();
}
