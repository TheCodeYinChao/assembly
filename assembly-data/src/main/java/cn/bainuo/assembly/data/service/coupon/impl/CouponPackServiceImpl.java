package cn.bainuo.assembly.data.service.coupon.impl;

import cn.bainuo.assembly.data.dao.coupon.CouponPackMapper;
import cn.bainuo.assembly.data.service.coupon.CouponPackService;
import cn.bainuo.assembly.data.vo.coupon.CouponLog;
import cn.bainuo.assembly.data.vo.coupon.PackageCouponRelationship;
import cn.bainuo.exception.DBException;
import cn.bainuo.exception.ServiceException;
import cn.bainuo.exception.exceptionenum.GlobalErrorCode;
import cn.bainuo.plugin.ParameterMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-15
 * Time: 11:12
 * Version ：1.0
 * Description:
 */
@Service
@Slf4j
public class CouponPackServiceImpl implements CouponPackService {
    @Autowired
    private CouponPackMapper couponPackMapper;

    @Override
    public Object bandpacks(ParameterMap parameterMap) {
        Integer packId = null;
        try {
            packId = Integer.valueOf(parameterMap.getString("packId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error("[CouponPackServiceImpl-bandpacks] Param: {},Error:{}",packId,e.toString());
            throw new ServiceException(GlobalErrorCode.PARAM_FORMAT_EXCEPTION.getCode(),"参数格式异常");
        }
        String couponGroupId = parameterMap.getString("couponGroupId");
        if(couponGroupId == null){
            GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
            throw  new ServiceException(paramNotNull.getCode(),"优惠券couponGroupId不能为空");
        }
        if(packId == null){
            GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
            throw  new ServiceException(paramNotNull.getCode(),"大礼包packId不能为空");
        }

        List<Integer> ids = couponPackMapper.selectExistingRecords(packId, couponGroupId);
        int rs = -1;
        PackageCouponRelationship pcrs = new PackageCouponRelationship();
        pcrs.setCreatetime(System.currentTimeMillis()/1000);
        pcrs.setGroupId(couponGroupId);
        pcrs.setPackageId(packId);

        try {
            GlobalErrorCode globalErrorCode = GlobalErrorCode.PARAM_NOT_INSERT_FAIL;
            if(ids.size() == 0){
                //检验没有 直接新增
                rs = couponPackMapper.bindGiftPack(pcrs);
            }
            if(ids.size() > 1){
                //多条删除。新增
                rs = couponPackMapper.deleteByIds(ids);
                rs = couponPackMapper.bindGiftPack(pcrs);
            }
            if(ids.size() ==1){
                //单挑直接更新
                pcrs.setId(ids.get(0));
                rs = couponPackMapper.updateRelation(pcrs);
            }
            if(rs <= 0){
                throw new ServiceException(globalErrorCode.getCode(),globalErrorCode.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-bandpacks] Param {},异常信息{}",parameterMap.toString(),e.toString());
            throw new DBException(e);
        }
        return rs;
    }
}
