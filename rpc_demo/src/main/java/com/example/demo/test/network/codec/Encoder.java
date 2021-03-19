package com.example.demo.test.network.codec;

import com.example.demo.test.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 21:03
 */
@Slf4j
/**
 * 编码器，将消息使用Protostuff进行编码
 */
public class Encoder extends MessageToByteEncoder {
    private Class<?> simpleClass;
    public Encoder(Class<?> simpleClass) {
        this.simpleClass = simpleClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        try {
            if (simpleClass.isInstance(msg)) {
                byte[] data = SerializationUtil.serialize(msg);
                out.writeInt(data.length);
                out.writeBytes(data);
            }
        }catch (Exception e) {
            log.error(e.toString());
        }
    }
}
