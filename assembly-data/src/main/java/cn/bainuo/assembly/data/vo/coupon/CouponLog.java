package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User: zyc
 * Date: 2018-05-15
 * Time: 19:22
 * Version ：1.0
 * Description:
 */
@Data
public class CouponLog implements Serializable {
        private String operateId;
        private String couponId;
        private Date createtime;
        private String remark;
        private Integer source;
        private Date startTime;
        private Date endTime;
}
