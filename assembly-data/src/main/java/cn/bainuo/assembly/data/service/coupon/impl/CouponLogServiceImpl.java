package cn.bainuo.assembly.data.service.coupon.impl;

import cn.bainuo.assembly.data.dao.coupon.CouponLogMapper;
import cn.bainuo.assembly.data.service.coupon.CouponLogService;
import cn.bainuo.assembly.data.vo.coupon.CouponLog;
import cn.bainuo.plugin.ParameterMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-18
 * Time: 14:27
 * Version ：1.0
 * Description:
 */
@Service
@Slf4j
@Transactional
public class CouponLogServiceImpl implements CouponLogService {

    @Autowired
    private CouponLogMapper couponLogMapper;

    @Override
    public Object queryData(ParameterMap parameterMap) {
        List<CouponLog> cl = couponLogMapper.queryDataPage(parameterMap);
        parameterMap.put("data",cl);
        return parameterMap;
    }
}
