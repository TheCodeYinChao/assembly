package com.nettyframe.seril.userdefined;

import ch.qos.logback.classic.gaffer.NestingType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Admin on 2019/4/9.
 */
@Slf4j
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
    private Map<String ,Boolean> nodeCheck = new ConcurrentHashMap<String,Boolean>();
    private String [] whitekList = {"127.0.0.1","192.168.0.100"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage n = (NettyMessage)msg;
        if(n.getHeader() != null && n.getHeader().getType()==MessageType.LOGIN_REQ.value){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            if(nodeCheck.containsKey(nodeIndex)){
                loginResp = bulidResponse((byte)-1);
            }else{
                InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for(String wip:whitekList){
                    if(wip.equals(ip)){
                        isOk = true;
                        break;
                    }
                }
                loginResp = isOk ? bulidResponse((byte)0):bulidResponse((byte)-1);

                if(isOk){
                    nodeCheck.put(nodeIndex,true);
                }
                log.info("The login response is :{}",n);
                ctx.writeAndFlush(loginResp);
            }
        }else {
            ctx.fireChannelRead(msg);
        }


    }

    /**
     * 构造响应信息
     * @param b
     * @return
     */
    private NettyMessage bulidResponse(byte b) {
        NettyMessage resp = new NettyMessage();
        Header h = new Header();
        h.setType(MessageType.LOGIN_REQ.value);
        resp.setHeader(h);
        resp.setBody(b);
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());//删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
