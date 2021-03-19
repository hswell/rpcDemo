package com.example.demo.test.config;

import lombok.Data;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 22:22
 */
@Data
public class LocalServerInfo {
    /**
     *本地启动地址
     */

    public static String LOCAL_HOST;
    /**
     *本地启动IP
     */

    public static int LOCAL_PORT;
}
