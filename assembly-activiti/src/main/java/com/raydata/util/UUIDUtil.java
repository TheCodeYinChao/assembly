package com.raydata.util;

import java.util.UUID;

// +----------------------------------------------------------------------
// | ProjectName: usercenter
// +----------------------------------------------------------------------
// | Date: 2018/8/4
// +----------------------------------------------------------------------
// | Time: 11:47
// +----------------------------------------------------------------------
// | Author: yinshuang.meng <yinshuang.meng@raykite.com>
// +----------------------------------------------------------------------
public class UUIDUtil {

    /**
     *
     * description:
     *
     * @param: []
     * @return: java.lang.String
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/4 11:58
     * @updateBy:
     * @updateTime:
     */
    public static String getUUID(){

        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

   /* public static void main(String[] args) {
        System.out.println(getUUID());
    }*/
}
