package com.excilys.aop;

@FunctionalInterface
public interface InvocationPoint<T> {
    T invoke() throws Exception;
}