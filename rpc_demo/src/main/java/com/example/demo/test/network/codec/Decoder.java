package com.example.demo.test.network.codec;

import com.example.demo.test.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author huangs
 * @version 1.0
 * @date 2020/11/7 21:02
 */
@Slf4j
/**
 * 解码器，将消息使用Protostuff进行解码
 */
public class Decoder extends ByteToMessageDecoder {
    private Class<?> simpleClass;
    public Decoder( Class<?> simpleClass){
        this.simpleClass = simpleClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)  {
        if (in.readableBytes() < 0){
            return;
        }
        try {
            in.markReaderIndex();
            int dataLength=in.readInt();
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            byte[] data=new byte[dataLength];
            in.readBytes(data);
            out.add(SerializationUtil.deserialize(data,simpleClass));
        }catch (Exception e) {
            log.error(e.toString());
        }
    }
}
