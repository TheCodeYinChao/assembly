package com.nettyframe.seril.userdefined;


import com.nettyframe.seril.marshalling.MarshallingCodeCFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author zyc
 * @date 2018/8/15 16:23
 * @Description:
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws Exception {
        marshaller = MarshallingCodeCFactory.bulidMarshalling();
    }

    // 使用marshall对Object进行编码，并且写入bytebuf...
    protected void encode(Object msg, ByteBuf out) throws Exception {
        try {
            //1. 获取写入位置
            int lengthPos = out.writerIndex();
            //2. 先写入4个bytes，用于记录Object对象编码后长度
            out.writeBytes(LENGTH_PLACEHOLDER);
            //3. 使用代理对象，防止marshaller写完之后关闭byte buf
            ByteOutput output = new ChannelBufferByteOutput(out);
            //4. 开始使用marshaller往bytebuf中编码
            marshaller.start(output);
            marshaller.writeObject(msg);
            //5. 结束编码
            marshaller.finish();
            //6. 设置对象长度
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}
