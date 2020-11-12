package com.example.demo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 22:21
 */
@Data
public class RpcProviderConfig {
    /**
     * 接口
     */
    private String nozzle;
    /**
     *映射
     */
    private String ref;
    /**
     *别名
     */
    private String alias;
    /**
     *ip
     */
    private String host;
    /**
     *端口
     */
    private int port;
}
