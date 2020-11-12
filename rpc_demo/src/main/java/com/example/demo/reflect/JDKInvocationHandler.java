package com.example.demo.reflect;

import com.example.demo.network.future.SyncWrite;
import com.example.demo.network.msg.Request;
import com.example.demo.network.msg.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:16
 */
//实现动态代理接口
public class JDKInvocationHandler implements InvocationHandler {
    private Request request;

    public JDKInvocationHandler(Request request) {
        this.request = request;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class[] paramTypes = method.getParameterTypes();
        if ("toString".equals(methodName) && paramTypes.length == 0) {
            return request.toString();
        } else if ("hashCode".equals(methodName) && paramTypes.length == 0) {
            return request.hashCode();
        } else if ("equals".equals(methodName) && paramTypes.length == 1) {
            return request.equals(args[0]);
        }

        request.setMethodName(methodName);
        request.setParamTypes(paramTypes);
        request.setArgs(args);
        request.setRef(request.getRef());
        Response response = new SyncWrite().writeAndSync(request.getChannel(), request, 5000);
        return response.getResult();
    }
}
