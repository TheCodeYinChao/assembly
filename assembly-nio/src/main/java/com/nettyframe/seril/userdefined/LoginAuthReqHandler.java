package com.nettyframe.seril.userdefined;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Admin on 2019/4/9.
 */
@Slf4j
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyMessage n = new NettyMessage();
        Header h = new Header();
        h.setType(MessageType.LOGIN_REQ.value);
        n.setHeader(h);
        ctx.writeAndFlush(n);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage n =(NettyMessage) msg;
        if(n.getHeader() != null && n.getHeader().getType() == MessageType.LOGIN_REQ.value){
            byte loginResult = (byte)n.getBody();
            if(loginResult != MessageType.LOGIN_REQ.value){
                ctx.close();//握手失败关闭连接
            }else{
                log.info("Login is ok :",n);
                ctx.fireChannelRead(msg);
            }
        }
        else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
