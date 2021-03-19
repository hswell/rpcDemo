package com.example.demo.test.config.spring;

import com.example.demo.test.config.spring.bean.ConsumerBean;
import com.example.demo.test.config.spring.bean.ProviderBean;
import com.example.demo.test.config.spring.bean.ServerBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/9 21:36
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("consumer", new MyBeanDefinitionParser(ConsumerBean.class));
        registerBeanDefinitionParser("provider", new MyBeanDefinitionParser(ProviderBean.class));
        registerBeanDefinitionParser("server", new MyBeanDefinitionParser(ServerBean.class));
    }
}
