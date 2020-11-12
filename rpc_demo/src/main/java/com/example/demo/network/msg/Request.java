package com.example.demo.network.msg;

import io.netty.channel.Channel;
import lombok.Data;


/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 22:06
 */

@Data
public class Request {
    /**
     * 消息管道 不能序列化
     */
    private transient Channel channel;
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 方法
      */
    private String methodName;
    /**
     * 属性
     */
    private Class[] paramTypes;
    /**
     *入参
     */
    private Object[] args;
    /**
     *接口
     */
    private String nozzle;
    /**
     *实现类
     */
    private String ref;
    /**
     *别名
     */
    private String alias;


}
