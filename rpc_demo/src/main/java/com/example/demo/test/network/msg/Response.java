package com.example.demo.test.network.msg;

import lombok.Data;

import java.nio.channels.Channel;
/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 22:06
 */
@Data
public class Response {
    /**
     * 消息管道 不能序列化
     */
    private transient Channel channel;
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 返回结果
     */
    private Object result;
}
