package com.example.demo.test.network.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 21:34
 */
public class  SyncWriteMap {
    public static Map<String, WriteFuture> syncKey = new ConcurrentHashMap<String, WriteFuture>();

}
