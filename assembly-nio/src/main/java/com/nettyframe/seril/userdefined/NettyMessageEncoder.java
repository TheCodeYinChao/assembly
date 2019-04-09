package com.nettyframe.seril.userdefined;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2019/4/9.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws Exception{
         this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage msg, List<Object> list) throws Exception {
        if(msg == null || msg.getHeader() == null){throw new Exception("The encode message is null");}

        ByteBuf buffer = Unpooled.buffer();
        Header h = msg.getHeader();

        buffer.writeInt(h.getCrcCode());
        buffer.writeInt(h.getLength());
        buffer.writeLong(h.getSessionID());
        buffer.writeByte(h.getPriority());
        buffer.writeByte(h.getType());
        buffer.writeInt(h.getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for(Map.Entry<String,Object> param: h.getAttachment().entrySet()){
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            buffer.writeInt(keyArray.length);
            buffer.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(value, buffer);
        }
        key = null;
        keyArray = null;
        value = null;
        if(msg.getBody() != null){
            marshallingEncoder.encode(msg.getBody(),buffer);
        }else{
            buffer.writeInt(0);
            buffer.setIndex(4,buffer.readableBytes());
        }
    }
}
