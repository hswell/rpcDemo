package com.example.demo.test.network.future;

import com.example.demo.test.network.msg.Response;

import java.util.concurrent.Future;


/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 21:20
 */
public interface WriteFuture<T> extends Future<T> {

    Throwable cause();

    void setCause(Throwable cause);

    boolean isWriteSuccess();

    void setWriteResult(boolean result);

    String requestId();

    T response();

    void setResponse(Response response);

    boolean isTimeout();

}
