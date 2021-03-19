package com.example.demo.test.config.spring.bean;

import com.alibaba.fastjson.JSON;
import com.example.demo.test.config.LocalServerInfo;
import com.example.demo.test.config.ProviderConfig;
import com.example.demo.test.config.RpcProviderConfig;
import com.example.demo.test.registry.RedisRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 22:23
 */
@Slf4j
public class ProviderBean extends ProviderConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RpcProviderConfig rpcProviderConfig = new RpcProviderConfig();
        rpcProviderConfig.setNozzle(nozzle);
        rpcProviderConfig.setRef(ref);
        rpcProviderConfig.setAlias(alias);
        rpcProviderConfig.setHost(LocalServerInfo.LOCAL_HOST);
        rpcProviderConfig.setPort(LocalServerInfo.LOCAL_PORT);
        long count = RedisRegistryCenter.registryProvider(nozzle, alias, JSON.toJSONString(rpcProviderConfig));
        log.info("注册生产者：{} {} {}", nozzle, alias, count);
    }
}
