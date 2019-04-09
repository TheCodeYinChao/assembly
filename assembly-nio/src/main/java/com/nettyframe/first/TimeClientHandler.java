package com.nettyframe.first;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Admin on 2019/4/9.
 */
@Slf4j
public class TimeClientHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        doWrite(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] b = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);
        String str = new String(b,"UTF-8");
        log.info("resp msg:{}",str);
        byteBuf.clear();
    }


    private void doWrite(ChannelHandlerContext ctx) {
        byte[] bytes = "query time ?".getBytes();
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(byteBuf);
    }
}
