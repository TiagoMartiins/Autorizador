package com.destaxa.Autorizador.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class JsonUtil {

    private JsonUtil() {}

    public static String toJson(Object object) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .readValue(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
