package cn.bainuo.assembly.data.vo.coupon;

import lombok.Data;

import java.io.Serializable;

@Data
public class PackageCouponRelationship implements Serializable {
    private Integer id;

    private Integer packageId;

    private Long createtime;

    private String groupId;
}