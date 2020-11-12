package com.example.demo.network.server;
import com.example.demo.network.msg.Request;
import com.example.demo.network.msg.Response;
import com.example.demo.util.ClassLoaderUtils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sun.misc.ClassLoaderUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 21:44
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    //实例化applicationContext 获取对应bean
    private ApplicationContext applicationContext;

    ServerHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    //channel通过Selector监听到来的io事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        try {
            //解析 request
            Request request=(Request)obj;
            log.info("接收到客户端请求");
            Class<?> classType = ClassLoaderUtils.forName(request.getNozzle());
            Method addMethod = classType.getMethod(request.getMethodName(), request.getParamTypes());
            Object objectBean = applicationContext.getBean(request.getRef());
            //通过反射的方式拿到bena代理实现真实的类
            Object result = addMethod.invoke(objectBean, request.getArgs());
            Response response=new Response();
            response.setRequestId(request.getRequestId());
            response.setResult(result);
            //返回处理结果
            ctx.writeAndFlush(response);
        } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            log.error(e.toString());
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("服务端接收数据完毕");
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("获取到异常");
        log.error(cause.toString());
        ctx.close();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(1);
    }
}
