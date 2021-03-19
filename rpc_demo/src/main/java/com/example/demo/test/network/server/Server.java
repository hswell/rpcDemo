package com.example.demo.test.network.server;

import com.example.demo.test.config.LocalServerInfo;
import com.example.demo.test.network.codec.Decoder;
import com.example.demo.test.network.codec.Encoder;
import com.example.demo.test.network.msg.Request;
import com.example.demo.test.network.msg.Response;
import com.example.demo.test.util.NetUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 22:06
 */
@Slf4j
public class Server implements Runnable {
    private ChannelFuture channelFuture;

    private transient ApplicationContext applicationContext;

    public Server(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean isActiveServer() {
        try {
            if (!Objects.isNull(channelFuture)) {
                return channelFuture.channel().isActive();
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public void run() {
        //bossGroup就是parentGroup，是负责处理TCP/IP连接的
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup就是childGroup,是负责处理Channel(通道)的I/O事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            /*
              指定线程池
              指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
              初始化服务端可连接队列,指定了队列的大小128
              绑定客户端连接时候触发操作
             */

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //ch的编码器 解码器 以及业务处理
                            ch.pipeline().addLast(new Decoder(Request.class)
                                    , new Encoder(Response.class)
                                    , new ServerHandler(applicationContext));
                        }
                    });
            int port = 23333;
            while (NetUtil.isPortUsing(port)) {
                port++;
            }
            LocalServerInfo.LOCAL_HOST = NetUtil.getHost();
            LocalServerInfo.LOCAL_PORT = port;
            this.channelFuture = bootstrap.bind(port).sync();
            this.channelFuture.channel().closeFuture().sync();
        } catch (UnknownHostException | InterruptedException e) {
            log.error(e.toString());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
