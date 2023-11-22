package com.lixl.study.beautyController.utils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class MethodUtils {

    private static ConcurrentHashMap<String, Method> methodMap = new ConcurrentHashMap<>();

    public static Method getMethod(Class controllerClass, String methodName) {
        String key = controllerClass.getSimpleName() + "-" + methodName;
        Method method = methodMap.get(key);
        if (method == null) {
            Method[] methodArr = controllerClass.getMethods();
            for (Method m : methodArr) {
                if (methodName.equals(m.getName())) {
                    method = m;
                    methodMap.put(key, method);
                    break;
                }
            }
        }
        return method;
    }
}
