package com.example.demo.config.spring.bean;

import com.example.demo.config.LocalServerInfo;
import com.example.demo.config.ServerConfig;
import com.example.demo.network.server.Server;
import com.example.demo.registry.RedisRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 22:26
 */
@Slf4j
public class ServerBean extends ServerConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //启动注册中心
        log.info("启动注册中心 ...");
        RedisRegistryCenter.init(host, port);
        log.info("启动注册中心完成 {} {}", host, port);

        //初始化服务端
        log.info("初始化生产端服务 ...");
        Server server = new Server(applicationContext);
        Thread thread = new Thread(server);
        thread.start();
        while (!server.isActiveServer()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }

        log.info("初始化生产端服务完成 {} {}", LocalServerInfo.LOCAL_HOST, LocalServerInfo.LOCAL_PORT);
    }

}
