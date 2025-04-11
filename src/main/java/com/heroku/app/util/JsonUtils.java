package com.heroku.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
    private static final ObjectMapper prettyPrinter = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static String toPrettyJson(String json) {
        try {
            Object jsonObject = prettyPrinter.readValue(json, Object.class);
            return prettyPrinter.writeValueAsString(jsonObject);
        } catch (Exception e) {
            return json;
        }
    }
}
