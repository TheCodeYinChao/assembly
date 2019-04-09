package com.nettyframe.seril.userdefined;

/**
 * @author zyc
 * @date 2018/8/15 18:35
 * @Description:
 */
public enum  MessageType {
    /*0 业务请求消息 1 业务响应消息 2 业务ONE WAY 消息（即使请求又是响应）
    3握手请求消息 4 握手应答消息 5 心跳请求消息 6 心跳应答消息*/
    BUS_REQ((byte)0),BUS_RESP((byte)1),LOGIN_REQ((byte) 3) ,HEARTBEAT_REQ((byte)5),HEARTBEAT_RESP((byte)6);
    public byte value;

    MessageType(byte value) {
        this.value = value;
    }
}
