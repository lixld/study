package com.lixl.study.beautyController.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class Retain2Decimal extends JsonSerializer<Double> {

//    @Override
//    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        if (value != null) {
//            BigDecimal bigDecimal = new BigDecimal(value.toString());
//            BigDecimal bigDecimal1 = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数（四舍五入)
//            jsonGenerator.writeNumber(bigDecimal1.toString());
//        } else {
//            jsonGenerator.writeNumber(value); //  此处 NullPointException
//        }
//    }

    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            jsonGenerator.writeNumber(NumberUtils.round(value, 2));
        } else {
            jsonGenerator.writeNull();
        }
    }
}