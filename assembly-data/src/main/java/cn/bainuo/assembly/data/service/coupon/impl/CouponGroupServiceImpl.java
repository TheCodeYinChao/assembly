package cn.bainuo.assembly.data.service.coupon.impl;

import cn.bainuo.assembly.data.dao.coupon.CouponGroupMapper;
import cn.bainuo.assembly.data.dao.coupon.CouponLogMapper;
import cn.bainuo.assembly.data.service.coupon.CouponGroupService;
import cn.bainuo.assembly.data.vo.coupon.CouponLog;
import cn.bainuo.assembly.data.vo.coupon.Coupons;
import cn.bainuo.assembly.data.vo.coupon.CouponsGroup;
import cn.bainuo.exception.DBException;
import cn.bainuo.exception.ServiceException;
import cn.bainuo.exception.exceptionenum.GlobalErrorCode;
import cn.bainuo.plugin.ParameterMap;
import cn.bainuo.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: zyc
 * Date: 2018-05-16
 * Time: 16:06
 * Version ：1.0
 * Description:
 */
@Service
@Slf4j
@Transactional
public class CouponGroupServiceImpl implements CouponGroupService {
    @Autowired
    private CouponGroupMapper couponGroupMapper;
    @Autowired
    private CouponLogMapper couponLogMapper;

    @Override
    public int deletes(ParameterMap param) {
        String ids = param.getString("ids");
        if(StringUtils.isEmpty(ids)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"ids Not Is Null");
        }
        List<String> params = Arrays.asList(ids.split(","));
        int rs = 0;
        try {
            rs = couponGroupMapper.deletes(params);

            //日志记录
            List<CouponLog> cls = new ArrayList<CouponLog>();
            CouponLog cl = new CouponLog();
            cl.setCouponId(ids);
            cl.setCreatetime(new Date());
            cl.setOperateId(param.getString("userId"));
            cl.setRemark("delete"+ JSON.toJSONString(ids));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-deletes] Param: {},Error: {}",param.toString(),e.toString());
            throw  new DBException(e);
        }
        return rs;
    }

    @Override
    public Object queryData(ParameterMap parameterMap) {
        List<CouponsGroup> rs = null;
        try {
            rs = couponGroupMapper.queryDataPage(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-queryData] Param: {},Error: {}",parameterMap.toString(),e.toString());
            throw  new DBException(e);
        }
        parameterMap.put("data",rs);
        return parameterMap;
    }

    @Override
    public Object saveOrUpdate(ParameterMap parameterMap) {
        validParam(parameterMap);
        String id = parameterMap.getString("id");
        int rs = 0;
        if(StringUtils.isEmpty(id)){
            rs = insertGroup(parameterMap);
        }else{
            rs = updateGroup(parameterMap);
        }
        return rs;
    }

    private void validParam(ParameterMap parameterMap){
        String groupName = parameterMap.getString("groupName");
        if(StringUtils.isEmpty(groupName)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"groupName Not Null");
        }
        String tips = parameterMap.getString("tips");
        if(StringUtils.isEmpty(tips)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"tips Not Null");
        }
        String platFlag = parameterMap.getString("platFlag");
        if(StringUtils.isEmpty(platFlag)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"platFlag Not Null");
        }
        String isOut = parameterMap.getString("isOut");
        if(StringUtils.isEmpty(isOut)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"isOut Not Null");
        }
    }

    public int insertGroup(ParameterMap pm){
        int rs = 0;
        try {
            CouponsGroup couponsGroup = new CouponsGroup();
            couponsGroup.setGroupName(pm.getString("groupName"));
            couponsGroup.setIsOut(Boolean.parseBoolean(pm.getString("isOut")));
            couponsGroup.setPlatFlag(Integer.valueOf(pm.getString("platFlag")));
            couponsGroup.setTips(pm.getString("tips"));
            couponsGroup.setId(UuidUtil.createDeleteLineUUID());
            Long l = System.currentTimeMillis() / 1000;
            couponsGroup.setCreatetime(l.intValue());
            rs = couponGroupMapper.insertGroup(couponsGroup);
            if(rs <= 0 ){
                throw  new ServiceException(GlobalErrorCode.PARAM_NOT_INSERT_FAIL.getCode(),"Insert couponGroup Fail");
            }

            //日志记录
            List<CouponLog> cls = new ArrayList<CouponLog>();
            CouponLog cl = new CouponLog();
            cl.setCouponId(l+"");
            cl.setCreatetime(new Date());
            cl.setOperateId(pm.getString("userId"));
            cl.setRemark("insert"+ JSON.toJSONString(couponsGroup));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-insertGroup Param: {},Error: {}]",pm.toString(),e.toString());
            throw  new DBException(e);
        }
        return rs;
    }

    public int updateGroup(ParameterMap pm){
        int rs = 0;
        try {
            CouponsGroup couponsGroup = new CouponsGroup();
            String id = pm.getString("id");
            couponsGroup.setId(id);
            couponsGroup.setGroupName(pm.getString("groupName"));
            couponsGroup.setIsOut(Boolean.parseBoolean(pm.getString("isOut")));
            couponsGroup.setPlatFlag(Integer.valueOf(pm.getString("platFlag")));
            couponsGroup.setTips(pm.getString("tips"));
            rs = couponGroupMapper.updateGroup(couponsGroup);
            if(rs <= 0 ){
                throw  new ServiceException(GlobalErrorCode.PARAM_NOT_UPDATA_FAIL.getCode(),"Update couponGroup Fail");
            }

            //日志记录
            List<CouponLog> cls = new ArrayList<CouponLog>();
            CouponLog cl = new CouponLog();
            cl.setCouponId(id);
            cl.setCreatetime(new Date());
            cl.setOperateId(pm.getString("userId"));
            cl.setRemark("update"+ JSON.toJSONString(couponsGroup));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-updateGroup Param: {},Error: {}]",pm.toString(),e.toString());
            throw  new DBException(e);
        }
        return rs;
    }


    @Override
    public Object bindCoupons(ParameterMap parameterMap) {
        String groupId = parameterMap.getString("groupId");
        String couponIds = parameterMap.getString("couponIds");
        if(StringUtils.isEmpty(groupId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"groupId Not Null");
        }
        if(StringUtils.isEmpty(couponIds)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"couponIds Not Null");
        }
        int rs = 0;
        try {
            Long time = System.currentTimeMillis() / 1000;
            rs = couponGroupMapper.baindCoupons(groupId,Arrays.asList(couponIds.split(",")),time.intValue());
            if(rs <= 0){
                throw new ServiceException(GlobalErrorCode.PARAM_NOT_DELETE_FAIL.getCode(),"组绑定优惠券失败");
            }

            //日志记录
            List<CouponLog> cls = new ArrayList<CouponLog>();
            CouponLog cl = new CouponLog();
            cl.setCreatetime(new Date());
            cl.setCouponId(groupId);
            cl.setOperateId(parameterMap.getString("userId"));
            cl.setRemark("bindCoupon"+ JSON.toJSONString(couponIds));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return rs;
    }


    @Override
    public Object getGroupById(ParameterMap parameterMap) {
        String id = parameterMap.getString("id");
        if(StringUtils.isEmpty(id)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"id Not Null");
        }
        CouponsGroup cg;
        try {
            cg =  couponGroupMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-getGroupById] Param :{},Error:{}",parameterMap.toString(),e.toString());
            throw  new DBException(e);
        }
        return cg;
    }

    @Override
    public Object validGroupName(ParameterMap parameterMap) {
        int count =0;
        String groupName = parameterMap.getString("groupName");
        if(StringUtils.isEmpty(groupName)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"groupName Not Null");
        }
        String groupId = parameterMap.getString("groupId");
        try {
            count = couponGroupMapper.selectCount(groupName,groupId);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  DBException(e);
        }
        if (count <= 0){
            return 0;
        }else{
            return -1;
        }
    }

    @Override
    public Object getCouponListByGroupId(ParameterMap parameterMap) {
        String groupId = parameterMap.getString("groupId");
        if(StringUtils.isEmpty(groupId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"groupId Not Null");
        }
        List<Coupons> coupons = couponGroupMapper.selectCouponGroupListPage(parameterMap);
        parameterMap.put("data",coupons);
        return parameterMap;
    }

    @Override
    public Object deleteCouponFromGroup(ParameterMap parameterMap) {
        String groupId = parameterMap.getString("groupId");
        if(StringUtils.isEmpty(groupId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"groupId Not Null");
        }
        String couponIds = parameterMap.getString("couponIds");
        if(StringUtils.isEmpty(couponIds)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"couponIds Not Null");
        }
        int rs = 0;
        try {
            rs = couponGroupMapper.deleteCouponFromGroup(groupId, Arrays.asList(couponIds.split(",")));

            //日志记录
            List<CouponLog> cls = new ArrayList<CouponLog>();
            CouponLog cl = new CouponLog();
            cl.setCreatetime(new Date());
            cl.setCouponId(groupId);
            cl.setOperateId(parameterMap.getString("userId"));
            cl.setRemark("deleteBindCoupon"+ JSON.toJSONString(couponIds));
            cls.add(cl);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-deleteCouponFromGroup] Param: {},Error :{}",parameterMap.toString(),e.toString());
            throw  new  DBException(e);
        }
        return rs;
    }

    @Override
    public Object selectUnBindCoupon(ParameterMap parameterMap) {
        String groupId = parameterMap.getString("groupId");
        if(StringUtils.isEmpty(groupId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"groupId Not Null");
        }
        CouponsGroup couponsGroup = couponGroupMapper.selectById(groupId);
        if(couponsGroup == null){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_TABLE.getCode(),GlobalErrorCode.PARAM_NOT_TABLE.getMessage());
        }
        parameterMap.put("platFlag",couponsGroup.getPlatFlag());
        List<Coupons> coupons = null;
        try {
            coupons = couponGroupMapper.selectUnBindByGroupIdPage(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponGroupServiceImpl-selectUnBindCoupon] Param: {},Error :{}",parameterMap.toString(),e.toString());
            throw  new DBException(e);
        }
        parameterMap.put("data",coupons);
        return parameterMap;
    }
}
