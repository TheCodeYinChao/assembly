package com.nettyframe.seril.userdefined;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Admin on 2019/4/9.
 */
@Data
public final class NettyMessage implements Serializable {
    private Header header;//消息头
    private Object body;//消息体

}
