package com.example.demo.test.config;

import lombok.Data;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:30
 */

@Data
public class ProviderConfig {
    /**
     * reflect 接口
     */
    protected String nozzle ;
    /**
     *  reflect 映射
     */
    protected String ref;
    /**
     *   alias 别名
     */
    protected String alias;

}
