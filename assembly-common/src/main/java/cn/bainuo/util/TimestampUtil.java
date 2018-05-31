package cn.bainuo.util;

import java.util.Date;

/**
 * User: zyc
 * Date: 2018-05-22
 * Time: 17:39
 * Version ：1.0
 * Description:
 */
public class TimestampUtil {
    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }
}
