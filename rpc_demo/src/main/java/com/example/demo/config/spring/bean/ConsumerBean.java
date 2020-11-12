package com.example.demo.config.spring.bean;

import com.alibaba.fastjson.JSON;
import com.example.demo.config.ConsumerConfig;
import com.example.demo.config.ProviderConfig;
import com.example.demo.config.RpcProviderConfig;
import com.example.demo.network.client.Client;
import com.example.demo.network.msg.Request;
import com.example.demo.reflect.JDKProxy;
import com.example.demo.registry.RedisRegistryCenter;
import com.example.demo.util.ClassLoaderUtils;
import io.netty.channel.ChannelFuture;
import io.protostuff.runtime.Accessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:38
 */
@Slf4j
public class ConsumerBean<T> extends ConsumerConfig<T> implements FactoryBean<T> {

    private ChannelFuture channelFuture;
    private RpcProviderConfig rpcProviderConfig;
    @Override
    public T getObject() throws Exception {
        //从redis获取链接
        if (null == rpcProviderConfig) {
            String infoStr = RedisRegistryCenter.obtainProvider(nozzle, alias);
            rpcProviderConfig = JSON.parseObject(infoStr, RpcProviderConfig.class);
        }

        //获取通信channel
        if (null == channelFuture) {
            Client clientSocket = new Client(rpcProviderConfig.getHost(), rpcProviderConfig.getPort());
            new Thread(clientSocket).start();
            for (int i = 0; i < 100; i++) {
                if (null != channelFuture) {
                    break;
                }
                Thread.sleep(500);
                channelFuture = clientSocket.getFuture();
            }
        }
        Assert.isTrue(null != channelFuture);

        Request request = new Request();
        request.setChannel(channelFuture.channel());
        request.setNozzle(nozzle);
        request.setRef(rpcProviderConfig.getRef());
        request.setAlias(alias);
        log.info("注册消费者：{} {} {}", nozzle, alias);
        return (T) JDKProxy.getProxy(ClassLoaderUtils.forName(nozzle), request);
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return ClassLoaderUtils.forName(nozzle);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
