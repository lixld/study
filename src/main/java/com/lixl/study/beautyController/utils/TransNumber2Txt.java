package com.lixl.study.beautyController.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class TransNumber2Txt extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            BigDecimal round = NumberUtils.round(Double.valueOf(value), 0);
            jsonGenerator.writeString(round + "%");
        } else {
//            jsonGenerator.writeNull();
            jsonGenerator.writeString("0%");
        }
    }
}