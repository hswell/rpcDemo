package com.example.demo.service.impl;


import com.example.demo.service.HelloService;
import org.springframework.stereotype.Service;


@Service("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public void echo() {
        System.out.println("hi demo demo rpc");
    }

}
