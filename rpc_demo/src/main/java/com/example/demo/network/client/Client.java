package com.example.demo.network.client;

import com.example.demo.network.codec.Decoder;
import com.example.demo.network.codec.Encoder;
import com.example.demo.network.msg.Request;
import com.example.demo.network.msg.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 22:25
 */
@Data
public class Client implements Runnable{
    private ChannelFuture future;
    private String inetHost;
    private int inetPort;

    /**
     * 使用server host和port初始化client
     * @param inetHost 使用server地址
     * @param inetPort 端口
     */
    public Client(String inetHost, int inetPort) {
        this.inetHost = inetHost;
        this.inetPort = inetPort;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            /*
              指定线程池
              指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
              初始化服务端可连接队列,指定了队列的大小128
              绑定客户端连接时候触发操作
             */
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(
                            new Decoder(Response.class),
                            new Encoder(Request.class),
                            new ClientHandler());
                }
            });
            //异步获取返回的消息
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            this.future = f;
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    }

