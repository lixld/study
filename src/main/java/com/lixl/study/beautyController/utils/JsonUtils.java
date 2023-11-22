package com.lixl.study.beautyController.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtils {

    private static Gson gson = new Gson();

    public static String toJsonStr(Object o) {
        String str = gson.toJson(o);
        return str;
    }

    public static <T> T toObject(String jsonStr, Type type) {
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }

        // 如果转换成 String 则原样输出
        if (type.getTypeName().equals(new TypeToken<String>(){}.getType().getTypeName())) {
            return (T) jsonStr;
        }

        T t = gson.fromJson(jsonStr, type);
        return t;
    }

    public static <T> T toObject(String jsonStr, TypeToken<T> typeToken) {
        return toObject(jsonStr, typeToken.getType());
    }
}
