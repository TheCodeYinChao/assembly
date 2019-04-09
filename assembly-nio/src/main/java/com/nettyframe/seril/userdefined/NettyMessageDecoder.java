package com.nettyframe.seril.userdefined;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2019/4/9.
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    MarshallingDecoder marshallingDecoder;
    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws Exception {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);
        if(frame == null){
            return null;
        }
        NettyMessage n = new NettyMessage();
        Header h = new Header();
        h.setCrcCode(frame.readInt());
        h.setLength(frame.readInt());
        h.setSessionID(frame.readLong());
        h.setPriority(frame.readByte());
        h.setType(frame.readByte());
        int size = frame.readInt();
        if(size > 0){
            Map<String,Object> attch = new HashMap<String,Object>();
            int keySize =0;
            byte[] keyArray = null;
            String key = null;
            for(int i=0;i < size;i++){
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray,"UTF-8");
                attch.put(key,marshallingDecoder.decode(frame));
            }
            keySize =0;
            keyArray = null;
            key = null;
            h.setAttachment(attch);
        }

        if(frame.readableBytes() > 4){
            n.setBody(marshallingDecoder.decode(in));
        }
        n.setHeader(h);
        return n;
    }
}
