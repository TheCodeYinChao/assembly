package com.nettyframe.seril.userdefined;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2019/4/9.
 */
@Data
public class Header {
    private int crcCode = 0xabef0101;
    private int length;//消息长度
    private long sessionID;//会话id
    private byte type;//消息类型
    private byte priority;//优先级
    private Map<String,Object> attachment = new HashMap<String,Object>();

}
