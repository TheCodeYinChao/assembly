package cn.bainuo.util;

import java.util.UUID;

public class UuidUtil {
    public static String createUUID(){
        return UUID.randomUUID().toString();
    }
    public static String createDeleteLineUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
