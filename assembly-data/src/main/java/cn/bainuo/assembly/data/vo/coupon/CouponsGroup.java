package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponsGroup implements Serializable{
    private String id;

    private Integer createtime;

    private Boolean isvalid;

    private Boolean isOut;

    private String groupName;

    private String tips;

    private Boolean isBindGiftPackage;

    private Integer platFlag;
}