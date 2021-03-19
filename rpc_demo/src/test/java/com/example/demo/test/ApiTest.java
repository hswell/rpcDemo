package com.example.demo.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApiTest {

    public static void main(String[] args) {
        String[] configs = {"demo-rpc-center.xml", "demo-rpc-provider.xml", "demo-rpc-consumer.xml"};
        new ClassPathXmlApplicationContext(configs);
    }

}
