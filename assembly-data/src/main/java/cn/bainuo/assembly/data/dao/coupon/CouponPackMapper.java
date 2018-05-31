package cn.bainuo.assembly.data.dao.coupon;

import cn.bainuo.assembly.data.vo.coupon.PackageCouponRelationship;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-15
 * Time: 11:13
 * Version ：1.0
 * Description:
 */
@Repository
public interface CouponPackMapper {
    int bindGiftPack(PackageCouponRelationship pcrs);

    int updateRelation(PackageCouponRelationship pcrs);

    List<Integer> selectExistingRecords(@Param("packId") Integer packId,@Param("groupId") String groupId);

    int deleteByIds(@Param("ids") List<Integer> ids);
}
