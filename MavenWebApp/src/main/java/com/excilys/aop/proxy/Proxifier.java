package com.excilys.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Proxifier {

    @SuppressWarnings("unchecked")
    public <T> T proxify(Class<T> clazz, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, invocationHandler);
    }
}