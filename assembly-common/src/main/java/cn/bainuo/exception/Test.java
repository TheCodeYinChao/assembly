package cn.bainuo.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * User: zyc
 * Date: 2018-05-09
 * Time: 19:00
 * Version ：1.0
 * Description:
 */
@Slf4j
public class Test {
    public static void main(String[] args) {

        ServiceException se = new ServiceException("400","异常");
        log.info("错误码:{},错误信息:{}",se.getErrorCode(),se.getExceptionMessage());


    }
}
