package com.example.demo.config;

import lombok.Data;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:36
 */
@Data
public class ServerConfig {
    /**
     * 注册中心地址
     */

    protected String host;
    /**
     *注册中心端口
     */
    protected int port;

}
