package com.nettyframe.seril.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Admin on 2019/4/9.
 */
@Slf4j
public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq buf = (SubscribeReq) msg;
        log.info("recevice msg ：{}" ,buf.toString());
        dosent(ctx,buf.getSubReqID());
    }

    /**
     * 发送响应信息
     * @param ctx
     * @param id
     */
    private void dosent(ChannelHandlerContext ctx,Integer id) {
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setSubReqID(id);
        subscribeResp.setRespCode(0);
        subscribeResp.setDesc("this server response msg");
        ctx.writeAndFlush(subscribeResp);
    }
}
