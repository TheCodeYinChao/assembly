package cn.bainuo.assembly.data.service.coupon.impl;

import cn.bainuo.assembly.data.dao.coupon.CouponLogMapper;
import cn.bainuo.assembly.data.dao.coupon.CouponMapper;
import cn.bainuo.assembly.data.service.coupon.CouponService;
import cn.bainuo.assembly.data.service.coupon.CouponUserService;
import cn.bainuo.assembly.data.vo.coupon.*;
import cn.bainuo.exception.DBException;
import cn.bainuo.exception.ServiceException;
import cn.bainuo.exception.exceptionenum.GlobalErrorCode;
import cn.bainuo.plugin.ParameterMap;
import cn.bainuo.util.NumberArithmeticUtils;
import cn.bainuo.util.TimestampUtil;
import cn.bainuo.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * User: zyc
 * Date: 2018-05-08
 * Time: 16:09
 * Version ：1.0
 * Description:
 */
@Service
@Slf4j
@Transactional
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponLogMapper couponLogMapper;
    @Autowired
    private CouponUserService couponUserService;

    @Override
    public Coupons selectCoupon(String id) {
        if(StringUtils.isEmpty(id)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"优惠券ID Not Null");
        }
        return couponMapper.selectCouponById(id);
    }

    @Override
    public List<CouponVo> selectCouponByUserId(String userId) {
        return couponMapper.selectCouponByUserId(userId);
    }

    @Override
    public List<Coupons> selectCoupons(ParameterMap jsonParseMap) {
        return couponMapper.selectCouponPage(jsonParseMap);
    }

    @Override
    public Object updateCouponStatus(ParameterMap parameterMap) {
        String id = parameterMap.getString("id");
        if(StringUtils.isEmpty(id)){
            log.error("[CouponServiceImpl-updateCouponStatus]{}","参数id不能为空");
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"优惠券ID不能为空");
        }
        String status = parameterMap.getString("status");
        if(StringUtils.isEmpty(status)){
            log.error("[CouponServiceImpl-updateCouponStatus]{}","参数id不能为空");
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"优惠券STATUS不能为空");
        }
        String userId = parameterMap.getString("userId");
        if(StringUtils.isEmpty(userId)){
            log.error("[CouponServiceImpl-updateCouponStatus]{}","userId Not Null");
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"userId Not Null");
        }
        List<String> ids = Arrays.asList(id.split(","));
        int rs=0;
        try {
             rs = couponMapper.updataStatus(ids,status,userId);
             if(status.equals(1) || status.equals(2)){
               rs = couponMapper.deleteUserCoupon(ids,userId);
             }

            if(rs <= 0){
                GlobalErrorCode code = GlobalErrorCode.PARAM_NOT_TABLE;
                throw  new DBException(code.getCode(),code.getMessage());
            }
        } catch (Exception e) {
            log.error("[CouponServiceImpl-updateCouponStatus] 参数:{},异常信息:{}",parameterMap.toString(),e.toString());
            e.printStackTrace();
            throw new DBException(e);
        }
        return rs;
    }

    @Override
    public Object deleteCoupon(ParameterMap parameterMap) {
        String couponId = parameterMap.getString("couponIds");
        if(StringUtils.isEmpty(couponId)){
            log.error("[CouponServiceImpl-updateCouponStatus]{}","参数id不能为空");
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"优惠券STATUS不能为空");
        }
        List<String> couponIds = Arrays.asList(couponId.split(","));
        int rs = 0;
        try {
             rs = couponMapper.delete(couponIds);
            if(rs <= 0){
                GlobalErrorCode code = GlobalErrorCode.PARAM_NOT_TABLE;
                throw  new DBException(code.getCode(),code.getMessage());
            }

            //插入操作日志
            List<CouponLog> cls = new ArrayList<CouponLog>();
            CouponLog cl = new CouponLog();
            cl.setCouponId("");
            cl.setCreatetime(new Date());
            cl.setOperateId(parameterMap.getString("userId"));
            cl.setRemark("delete"+JSON.toJSONString(couponId));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-updateCouponStatus] Param{},error{}",parameterMap.toString(),e.toString());
            throw  new DBException(e);
        }
        return rs;
    }

    @Override
    public Object saveOrUpdate(ParameterMap parameterMap) {
        String id = parameterMap.getString("id");
        String userId = parameterMap.getString("userId");
        if(StringUtils.isEmpty(userId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"userId Not Null");
        }
        if(StringUtils.isEmpty(id)){
            parameterMap.put("id",UuidUtil.createDeleteLineUUID());
            parameterMap.put("isvalid",true);
            parameterMap.put("createtime",new Long(System.currentTimeMillis() / 1000).intValue());
            id = String.valueOf(insert(parameterMap));
        }else{
            updateCoupon(parameterMap);
        }
        return id;
    }

    private int insert(ParameterMap parameterMap){
        int  id = 0;
        try {
            id =couponMapper.insertCoupon(parameterMap);
            if(id<=0){
                GlobalErrorCode globalErrorCode = GlobalErrorCode.PARAM_NOT_INSERT_FAIL;
                throw new ServiceException(globalErrorCode.getCode(),globalErrorCode.getMessage());
            }
            //插入操作日志
            List<CouponLog> cls = new ArrayList<CouponLog>();
            Coupons coupons = couponMapper.selectCouponById(parameterMap.getString("id") );
            CouponLog cl = new CouponLog();
            cl.setCouponId(coupons.getId());
            cl.setCreatetime(new Date());
            cl.setOperateId(parameterMap.getString("userId"));
            cl.setRemark("insert"+JSON.toJSONString(coupons));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-Insert] Param {},异常信息{}",parameterMap.toString(),e.toString());
            throw new DBException(e);
        }
        return id;
    }

    private int updateCoupon(ParameterMap parameterMap){
        String id = parameterMap.getString("id");
        if(StringUtils.isEmpty(id)){
            GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
            throw  new ServiceException(paramNotNull.getCode(),"参数id不能为空");
        }
        int rs = 0;
        try {
            rs =couponMapper.updateCoupon(parameterMap);
            if(rs <= 0){
                GlobalErrorCode globalErrorCode = GlobalErrorCode.PARAM_NOT_UPDATA_FAIL;
                throw new ServiceException(globalErrorCode.getCode(),globalErrorCode.getMessage());
            }

            //插入操作日志
            List<CouponLog> cls = new ArrayList<CouponLog>();
            Coupons coupons = couponMapper.selectCouponById(id);
            CouponLog cl = new CouponLog();
            cl.setCouponId(coupons.getId());
            cl.setCreatetime(new Date());
            cl.setOperateId(parameterMap.getString("userId"));
            cl.setRemark("update"+JSON.toJSONString(coupons));
            cls.add(cl);
            couponLogMapper.insertCouponLog(cls);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-Update] Param {},异常信息{}",parameterMap.toString(),e.toString());
            throw new DBException(e);
        }
        return rs;
    }

    @Override
    public Object sendStamps(ParameterMap parameterMap) {
        parameterMap.put("id", UuidUtil.createDeleteLineUUID());
        ParameterMap param = validAddParams(parameterMap);//校验参数
        int id = 0;
        try {
            id = couponMapper.insertUserStamp(param);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-sendStamps] Param{},error{}",parameterMap.toString(),e.toString());
            throw  new DBException(e);
        }
        return id;
    }

    private ParameterMap validAddParams(ParameterMap parameterMap){
        String userId = parameterMap.getString("userId");
        String couponId = parameterMap.getString("couponId");
        GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
        if(StringUtils.isEmpty(userId)){
            throw  new ServiceException(paramNotNull.getCode(),"userId Not Null");
        }
        if(StringUtils.isEmpty(couponId)){
            throw  new ServiceException(paramNotNull.getCode(),"couponId Not Null");
        }

        Coupons coupons = couponMapper.selectCouponById(couponId);
        if(coupons == null){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_TABLE.getCode(),"Param Error !");
        }
        parameterMap.put("deadline",coupons.getDays());
        parameterMap.put("createtime",coupons.getCreatetime());
        parameterMap.put("needMoney",coupons.getNeedmoney());
        parameterMap.put("classType",coupons.getClassType());
        parameterMap.put("connectedId",coupons.getConnectedId());
        parameterMap.put("startline",coupons.getStartline());
        return parameterMap;
    }


    @Override
    public Object sendUserStamps(String userId, String couponId,Integer source) {
        //发放优惠券
        GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
        if(StringUtils.isEmpty(userId)){
            throw  new ServiceException(paramNotNull.getCode(),"userId Not Null");
        }
        if(StringUtils.isEmpty(couponId)){
            throw  new ServiceException(paramNotNull.getCode(),"couponId Not Null");
        }
        if(source == null){
            throw  new ServiceException(paramNotNull.getCode(),"source Not Null");
        }
        Coupons coupons = couponMapper.selectCouponById(couponId);

        validCouponLimitNum(userId,couponId,coupons.getLimitNum());//校验优惠券领取数量

        CouponUser cu = new CouponUser();
            cu.setId(UuidUtil.createDeleteLineUUID());
            cu.setCreatetime(coupons.getCreatetime());
            cu.setCouponId(coupons.getId());
            cu.setClassType(coupons.getClassType());
            cu.setConnectedId(coupons.getConnectedId());
            cu.setMoney(coupons.getMoney());
            cu.setNeedMoney(coupons.getNeedmoney());
            cu.setSource(source);
            Integer daystype = coupons.getDaystype();
            if(daystype.intValue() == 0){//按天
                int days = Integer.valueOf(coupons.getDays());
                Integer startline = coupons.getStartline();
                int seccend = days * 24 * 60 * 60;//秒
                cu.setDeadline(startline+seccend);
            }
        int rs;
        try {
            rs = couponMapper.insertUserCoupon(cu);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-sendUserStamps] Param userId :{}, couponId :{}, source:{}; ERROR {}"
                    ,userId,couponId,source,e.toString());
            throw new DBException(e);
        }
        return rs;
    }

    @Override
    public Object getUserCoupons(ParameterMap parameterMap) {
        String userId = parameterMap.getString("userId");
        Integer platFalg = Integer.valueOf( parameterMap.getString("platFalg"));
       if(StringUtils.isEmpty(userId)){
            GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
            throw  new ServiceException(paramNotNull.getCode(),"userId Not Null");
        }
        if(platFalg == null){
            GlobalErrorCode paramNotNull = GlobalErrorCode.PARAM_NOT_NULL;
            throw  new ServiceException(paramNotNull.getCode(),"platFalg Not Null");
        }
        List<UserCoupon>  cs = null;
        try {
            cs = couponMapper.selectUserCouponPage(parameterMap);
            parameterMap.put("data",cs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-sendUserStamps] Param {}, ERROR {}"
                    ,parameterMap.toString(),e.toString());
            throw new  DBException(e);
        }
        return parameterMap;
    }
    @Override
    public  Object addCouponUser(ParameterMap parameterMap){
        String userId = parameterMap.getString("userId");
        String couponId = parameterMap.getString("couponId");
        int count = couponMapper.insertUserStamp(parameterMap);
        if(count<=0){
            GlobalErrorCode globalErrorCode = GlobalErrorCode.PARAM_NOT_INSERT_FAIL;
            throw new ServiceException(globalErrorCode.getCode(),globalErrorCode.getMessage());
        }
        return count;
    }

    @Override
    public Object editCouponUser(CouponUser couponUser) {
        CouponUser cu = couponMapper.selectCouponByCouponUserId(couponUser);
        int count = 0;
        if(cu.getIsUsed()==0){//如果优惠券未使用
         count = couponMapper.updateCouponUser( cu.getId());
        }
        return count;
    }

    @Override
    public Object selectIsUsing(String data, String userId,Integer platFalg) {
        if(StringUtils.isEmpty(userId)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"userId Not Null");
        }
        if(StringUtils.isEmpty(data)){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"data Not Null");
        }
        if(platFalg == null){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"platFalg Not Null");
        }
        List<CouponParam> params = null;//条件
        try {
            params = JSON.parseArray(data, CouponParam.class);
        } catch (Exception e) {
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_JSON.getCode(),"data Not Json");
        }
        List<UserCoupon> usc = couponMapper.getIsUsIngByUserId(userId,platFalg,null);//所有可用优惠券
        if(usc==null || usc.isEmpty()){
            throw new ServiceException(GlobalErrorCode.PARAM_NOT_TABLE.getCode(),"userId Not Coupon_User Record");
        }
        Map<String,CouponParam> mapParam = new HashMap();
        for(CouponParam param:params){//处理相同spuid，不同skuid ，价格合到一起
            if(mapParam.containsKey(param.getSpuId())){
                CouponParam couponParam = mapParam.get(param.getSpuId());
                BigDecimal countPrice = NumberArithmeticUtils.safeMultiply(couponParam.getPerPirce(), couponParam.getCount());
                BigDecimal countPrice2 = NumberArithmeticUtils.safeMultiply(param.getPerPirce(), param.getCount());
                BigDecimal sumCount = NumberArithmeticUtils.safeAdd(countPrice, countPrice2);
                couponParam.setPerPirce(sumCount);
                couponParam.setCount(1);
            }else {
                mapParam.put(param.getSpuId(),param);
            }
        }
        params.clear();
        for(Map.Entry<String, CouponParam> entry : mapParam.entrySet()){
            params.add(entry.getValue());
        }

        Map<String,Set<UserCoupon>> map = new HashMap<String,Set<UserCoupon>>();
        Set<UserCoupon> rs = new HashSet<UserCoupon>();// 结果容器 未用，当前能用
        Set<UserCoupon> rsn = new HashSet<UserCoupon>();//结果容器 未用，当前商品不能用
        map.put("usable",rs);
        map.put("unusable",rsn);
        map = category(singleItem(allCategories(map, params, usc), params, usc), params, usc);
        Set<UserCoupon> usable = map.get("usable");
        for (UserCoupon us:usc){
            if(!usable.contains(us)){
                rsn.add(us);
            }
        }
        map.put("unusable",rsn);
        return map;
    }

    @Override
    public Object getUserCouponList(String userId, Integer platFalg,Integer userType) {
        if(StringUtils.isEmpty(userId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"userId Not Null!");
        }
        if(platFalg == null){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"platFalg Not Null!");
        }
        List<UserCoupon> list = couponMapper.getIsUsIngByUserId(userId, platFalg,userType);
        return list;
    }

    @Override
    public Object saveBatchUserCoupon(String userId, String couponIds,Integer source) {
        if(StringUtils.isEmpty(userId)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"userId Not Null");
        }
        if(StringUtils.isEmpty(couponIds)){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_NULL.getCode(),"couponIds Not Null");
        }
        List<String> ids = Arrays.asList(couponIds.split(","));
        List<Coupons> couponsList = couponMapper.selectCouponsByIds(ids);
        //校验券是否达到数量
        Map<String,Object> os = (Map)couponUserService.validCouponUserCount(userId, couponIds);
        Boolean valid = (Boolean) os.get("valid");
        if(!valid){
            throw  new ServiceException(GlobalErrorCode.COUNT_IS_MAX.getCode(),"部分券达到最大领取数量");
        }

        List<CouponUser> userCoupons = handleUserCoupnData(couponsList,userId,source);
        int rs = 0;
        try {
            rs = couponMapper.saveBatchUserCoupons(userCoupons);
            if(rs <= 0){
                throw new ServiceException(GlobalErrorCode.PARAM_NOT_INSERT_FAIL.getCode(),"Insert Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[CouponServiceImpl-saveBatchUserCoupon] Param: {},Error: {}",
                    JSON.toJSONString(userCoupons),e.toString());
            throw new DBException(e);
        }
        return rs;
    }

    private List<CouponUser> handleUserCoupnData(List<Coupons> coupons,String userId,Integer source){
        List<CouponUser> userCouponList = new LinkedList<CouponUser>();
        if(coupons == null || coupons.isEmpty()){
            throw  new ServiceException(GlobalErrorCode.PARAM_NOT_TABLE.getCode(),"Param Not In table");
        }
        for(Coupons coupon:coupons){
            CouponUser uc = new CouponUser();
            uc.setId(UuidUtil.createDeleteLineUUID());
            uc.setSource(source);
            uc.setMoney(coupon.getMoney());
            uc.setNeedMoney(coupon.getNeedmoney());
            if(coupon.getDaystype()==0){
                Integer day = Integer.valueOf(coupon.getDays());
                Long time = new Date().getTime()/1000 + day*24*60*60;
                uc.setDeadline(time.intValue());//领取时间延后day天
            }else{
                uc.setDeadline(Integer.valueOf(coupon.getDays()));
            }
            Long time = new Date().getTime()/1000;
            uc.setCreatetime(time.intValue());
            uc.setIsUsed(0);
            if(coupon.getClassType() != null){
                uc.setClassType(coupon.getClassType());
            }
            uc.setCouponId(coupon.getId());
            String connectedId = coupon.getConnectedId();
            if(StringUtils.isNotEmpty(connectedId)){
                uc.setConnectedId(connectedId);
            }
            uc.setStartline(coupon.getStartline());
            uc.setUserId(userId);
            userCouponList.add(uc);
        }
        return userCouponList;
    }


    private Map<String ,Set<UserCoupon>> allCategories(Map<String ,Set<UserCoupon>> rs, List<CouponParam> params, List<UserCoupon> usc){//全品类
        Set<UserCoupon> usable =rs.get("usable");
        //移除非去全品类和 已到开始时间
        List<UserCoupon> newusc = new ArrayList<UserCoupon>();
        for(UserCoupon us:usc){
            Integer startline = us.getStartline();//秒
            int timestamp = TimestampUtil.getSecondTimestamp(new Date());
            if((us.getClassType()==null || us.getClassType().intValue()==1) && timestamp > startline){
                newusc.add(us);
            }
        }
        if(newusc.isEmpty()){return rs;}

        BigDecimal countPrice=BigDecimal.ZERO;
        for (CouponParam cp:params){
            countPrice = NumberArithmeticUtils.safeAdd(NumberArithmeticUtils.safeMultiply(cp.getPerPirce(), cp.getCount()),countPrice);
        }
        for(UserCoupon us :newusc){
            if(us.getNeedMoney()==-1){
                usable.add(us);
                continue;
            }
            BigDecimal decimal = NumberArithmeticUtils.safeSubtract(false, countPrice, BigDecimal.valueOf(us.getNeedMoney()));
            if(decimal.compareTo(BigDecimal.ZERO)>=0){
                usable.add(us);
                continue;
            }
        }
        return  rs;
    };

    private Map<String ,Set<UserCoupon>> singleItem(Map<String ,Set<UserCoupon>> rs, List<CouponParam> params, List<UserCoupon> uscc){//单品类
        Set<UserCoupon> usable =rs.get("usable");
        //找到属于单品的券 和 已到开始时间
        List<UserCoupon> usc  = new ArrayList<UserCoupon>();
        for (UserCoupon us:uscc){
            Integer startline = us.getStartline();//秒
            int timestamp = TimestampUtil.getSecondTimestamp(new Date());
            if(us.getClassType().intValue()==4 && timestamp > startline){
                usc.add(us);
            }
        }
        if(usc.isEmpty()){return rs;}

        for(CouponParam cp : params){
            for (UserCoupon uc:usc){
                Double needMoney = uc.getNeedMoney();//需要满足的金额 -1不限金额
                List<String> connectedIds = Arrays.asList(uc.getConnectedId().split(","));
                if(needMoney == -1){//不限金额
                    if(uc.getClassType()==4){ //单品类
                        if(connectedIds.contains(cp.getSpuId())){
                            usable.add(uc);
                            continue;
                        }
                    }
                }else{
                    BigDecimal countPrice = NumberArithmeticUtils.safeMultiply(cp.getPerPirce(), cp.getCount());
                    BigDecimal decimal = NumberArithmeticUtils.safeSubtract(false,countPrice, BigDecimal.valueOf(needMoney));
                    if(uc.getClassType()==4){ //单品类
                        if(connectedIds.contains(cp.getSpuId()) && decimal.compareTo(BigDecimal.ZERO) >= 0){
                            usable.add(uc);
                            continue;
                        }
                    }
                }
            }
        }
        return rs;
    }

    private Map<String ,Set<UserCoupon>> category(Map<String ,Set<UserCoupon>> rs, List<CouponParam> params, List<UserCoupon> uscc){//分类
        Set<UserCoupon> usable =rs.get("usable");
        //移除非分类券
        List<UserCoupon> usc = new ArrayList<UserCoupon>();
        for(UserCoupon us:uscc){
            Integer startline = us.getStartline();//秒
            int timestamp = TimestampUtil.getSecondTimestamp(new Date());
            if(us.getClassType().intValue() == 2 && timestamp > startline){//移除非分类券 和到开始时间
                usc.add(us);
            }
        }
        if(usc.isEmpty()){return rs;}

        for(UserCoupon us:usc){
            List<String> connectedIds = Arrays.asList(us.getConnectedId().split(","));
            BigDecimal price = BigDecimal.ZERO;
            Double needMoney = us.getNeedMoney();
            Set<String> set = new HashSet<String>();
            for(CouponParam param:params){
                if(connectedIds.contains(param.getFirstCategoryId())){
                    price = NumberArithmeticUtils.safeAdd(NumberArithmeticUtils.safeMultiply(param.getPerPirce(), param.getCount()),price);
                    set.add(param.getFirstCategoryId());
                }
                if(connectedIds.contains(param.getTwoCategoryId())){
                    price = NumberArithmeticUtils.safeAdd(NumberArithmeticUtils.safeMultiply(param.getPerPirce(), param.getCount()),price);
                    set.add(param.getTwoCategoryId());
                }
            }
            boolean flag = false;
            if(needMoney == -1){//不限金额
                for(String connectedId:connectedIds){
                    flag = set.contains(connectedId);
                }
                if(flag){
                    usable.add(us);
                    flag = false;
                }
            }
            BigDecimal decimalTwo = NumberArithmeticUtils.safeSubtract(false,price, BigDecimal.valueOf(needMoney));
            if(decimalTwo.compareTo(BigDecimal.ZERO) >= 0){
                for(String connectedId:connectedIds){
                    flag = set.contains(connectedId);
                }
                if(flag){
                    usable.add(us);
                    flag = false;
                }
            }
        }
        return rs;
    }


    private void validCouponLimitNum(String userId,String couponId,Integer limitNum){
        int count = couponMapper.selectUserCouponCount(userId,couponId);
        if(count > 0 && count >= limitNum){
            throw  new ServiceException(GlobalErrorCode.COUNT_IS_MAX.getCode(),"Coupon Count Up Limit");
        }
    }
}
