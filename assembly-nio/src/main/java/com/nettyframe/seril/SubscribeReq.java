package com.nettyframe.seril;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Admin on 2019/4/9.
 */
@Data
public class SubscribeReq implements Serializable {
    private int subReqID;
    private String userName;
    private String productName;
    private String phoneNumber;
    private String address;

}
