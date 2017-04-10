package com.example.marce.luckypuzzle.ui.recyclerViews;

import java.lang.reflect.Method;

/**
 * Created by marce on 09/04/17.
 */

public class ReflectHelper {
    @SuppressWarnings("unchecked")
    public static void invokeMethodIfExists(String methodName, Object target, Class<?>[] parameterTypes, Object[] parameters) {
        Class c = target.getClass();
        try {
            Method method = c.getMethod(methodName, parameterTypes);
            method.invoke(target, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}