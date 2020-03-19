package com.raydata.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// +----------------------------------------------------------------------
// | ProjectName: usercenter
// +----------------------------------------------------------------------
// | Date: 2018/8/4
// +----------------------------------------------------------------------
// | Time: 17:13
// +----------------------------------------------------------------------
// | Author: yinshuang.meng <yinshuang.meng@raykite.com>
// +----------------------------------------------------------------------
public class CheckUtils {
    /**
     *
     * description:校验邮箱：@之前必须有内容且只能是字母（大小写）、数字、下划线(_)、减号（-）、点（.）@
     * 和最后一个点（.）之间必须有内容且只能是字母（大小写）、数字、点（.）、减号（-），且两个点不能挨着
     * 最后一个点（.）之后必须有内容且内容只能是字母（大小写）、数字且长度为大于等于2个字节，小于等于6个字节
     *
     * @param: [eamil]
     * @return: boolean
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/4 16:10
     * @updateBy:
     * @updateTime:
     */
    public static boolean checkEmail(String eamil) {

        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        return check(regex,eamil);
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147、182
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189、177
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^[1][3456789][0-9]{9}$";
        return check(regex,cellphone);
    }
    /**
     * 验证固话号码
     * @param telephone
     * @return
     */
    public static boolean checkTelephone(String telephone) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(regex,telephone);
    }


    /**
     *
     * description: 校验密码
     *
     * @param: [password]
     * @return: boolean
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/4 17:19
     * @updateBy:
     * @updateTime:
     */
    public static boolean checkPassword(String password){
        return StringUtils.isNotBlank(password);
        /*String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
        return check(regex,password);*/
    }

    /**
     *
     * description:校验正整数
     *
     * @param: [number]
     * @return: boolean
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/6 15:10
     * @updateBy:
     * @updateTime:
     */
    public static boolean checkPositiveInteger(String number){

        String regex = "^[0-9]*[1-9][0-9]*$";
        return check(regex,number);
    }


    /**
     *
     * description:校验自然数
     *
     * @param: [number]
     * @return: boolean
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/7 18:42
     * @updateBy:
     * @updateTime:
     */
    public static boolean checkNaturalNumber(String number){

        String regex = "^\\d+$";
        return check(regex,number);
    }

    /**
     *
     * description: 校验id集合, 数字用,隔开并且以数字开头
     *
     * @param: [ids]
     * @return: boolean
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/27 16:34
     * @updateBy:
     * @updateTime:
     */

    public static boolean checkIds(String ids){
        String regex = "^(\\d+,)*\\d+$";
        return check(regex,ids);
    }

    /**
     * @description: 校验：由8~16位数字和26个英文字母大小写组成的字符串
     *
     * @param: 
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/7/4 16:37
     */
    
    public static boolean checkCode(String code){

        String regex = "^[A-Za-z0-9]{8,16}$";
        return check(regex,code);
    }

    /**
     * @description: 判断小数点后2位的数字
     *
     * @param: 
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/7/4 16:36
     */
    public static boolean isNumber(String str){
        String regex = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"; // 判断小数点后2位的数字的正则表达式
        return check(regex,str);
    }

    /**
     *
     * description:校验是否匹配的通用方法
     *
     * @param: [regex, value]
     * @return: boolean
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/6 11:11
     * @updateBy:
     * @updateTime:
     */
    private static boolean check(String regex ,String value){
        if(StringUtils.isBlank(value)){
            return false;
        }
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(value);
        return matcher.matches();
    }
}
