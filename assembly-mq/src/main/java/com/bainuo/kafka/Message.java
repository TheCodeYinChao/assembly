package com.bainuo.kafka;

import lombok.Data;

import java.util.Date;

/**
 * Created by Admin on 2019/4/13.
 */
@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳
}
