package com.example.demo.reflect;

import com.example.demo.network.msg.Request;
import com.example.demo.util.ClassLoaderUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:16
 */
//根据类型获取动态代理
public class JDKProxy {
    public static <T> T getProxy(Class<T> clazz, Request request){
        InvocationHandler handler=new  JDKInvocationHandler(request);
        ClassLoader classLoader = ClassLoaderUtils.getCurrentClassLoader();
        T result = (T) Proxy.newProxyInstance(classLoader,new Class[]{clazz},handler);
        return result;
    }
}
