package com.nettyframe.seril.marshalling;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Admin on 2019/4/9.
 */
@Data
public class SubscribeResp implements Serializable {
    private int subReqID;
    private int respCode;
    private String desc;
}
