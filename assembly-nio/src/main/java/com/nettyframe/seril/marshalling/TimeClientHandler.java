package com.nettyframe.seril.marshalling;

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
        SubscribeResp byteBuf = (SubscribeResp)msg;
        log.info("recevice server msg :{}",byteBuf.toString());
    }


    private void doWrite(ChannelHandlerContext ctx) {
       for(int i =0 ; i<10;i++){
        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSubReqID(i);
        subscribeReq.setAddress("address");
        subscribeReq.setPhoneNumber("110");
        subscribeReq.setUserName("liming");
        ctx.writeAndFlush(subscribeReq);
       }
    }
}
