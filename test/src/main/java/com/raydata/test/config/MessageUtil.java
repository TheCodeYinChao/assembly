package com.raydata.test.config;

import java.io.UnsupportedEncodingException;

/**
 * description: MessageUtil <br>
 * date: 2020/6/16 19:18 <br>
 * author: zyc <br>
 * version: 1.0 <br>
 */
public class MessageUtil {

    public static String byte2Str(byte[] str){
        String keyword = "";
        try {
            keyword = new String(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return keyword;
    }
}
