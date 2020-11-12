package com.example.demo.config;

import lombok.Data;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:35
 */
@Data
public class ConsumerConfig<T> {
    /**
     *接口
     */

    protected String nozzle;
    /**
     *别名
     */

    protected String alias;
}
