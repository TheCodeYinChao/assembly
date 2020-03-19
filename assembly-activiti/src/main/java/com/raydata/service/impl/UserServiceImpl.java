package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.raydata.core.ResultVo;
import com.raydata.dao.UserMapper;
import com.raydata.model.*;
import com.raydata.service.*;
import com.raydata.util.*;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jnlp.IntegrationService;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Value("${pm.default.passwrod}")
    private String defaultPwd;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PmDictionarieService pmDictionarieService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;

    public Object getByUserId(Integer userId){
        Assert.objNotNull(userId,"userId");
        User user = userMapper.selectById(userId);
        return  ResultVo.success(user);
    }

    @Override
    @Transactional
    public Object insertUser(String params) {
        User user = JacksonUtil.toObj(params, User.class);
        Map<String,Object> map = JacksonUtil.toObj(params, Map.class);;
        insertValidAndSetValue(params,user,map);

        userMapper.insert(user);


        saveUserRole(user, map);
        return ResultVo.success();
    }



    @Override
    @Transactional
    public Object updateUser(String params) {
        User user = JacksonUtil.toObj(params, User.class);
        Assert.objNotNull(user.getUserId(),"userId");
        userMapper.update(user,new UpdateWrapper<User>().eq("user_id", user.getUserId()));
        Map<String,Object> map = JacksonUtil.toObj(params, Map.class);
        saveUserRole(user, map);
        return ResultVo.success();
    }



    @Override
    public Object getUsers(String param,IPage page) {
        User user = JacksonUtil.toObj(param, User.class);
        page = userMapper.selectPage(page,
                        new QueryWrapper<User>().eq(false,"user_id", user.getUserId())
                                                .like(false,"user_name", user.getUserName())
                                                .eq(false,"user_login",user.getUserLogin())
                                                .eq(false,"telphone",user.getTelphone())
                                                .eq("status",0)
                                                .orderByDesc("create_time")
                                            );

        List<User> records = page.getRecords();
                records.forEach(x->{
                    //补全角色
                    BaseMapper<UserRole> baseMapper = userRoleService.getBaseMapper();
                    List<UserRole> urs = baseMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", x.getUserId()));
                    if(!CollectionUtils.isEmpty(urs)){
                        List<Integer> roles = Lists.newArrayList();
                        urs.forEach(r->{
                            roles.add(r.getRoleId());
                        });
                        BaseMapper<Role> roleBaseMapper = roleService.getBaseMapper();
                        List<Role> rolesAdd = roleBaseMapper.selectList(new QueryWrapper<Role>().in(false, "role_id", roles));
                        x.setRoles(rolesAdd);
                    }
                    //补全行业
                    String industry = x.getIndustry();
                    if(!StringUtils.isEmpty(industry)){
                        List<String> industryIds = Splitter.on(",").trimResults().splitToList(industry);
                        List<PmDictionaries> industrys = pmDictionarieService.getBaseMapper().selectList(new QueryWrapper<PmDictionaries>().in("id", industryIds));
                        x.setIndustrys(industrys);
                    }

                    //补全区域
                    String area = x.getArea();
                    if(!StringUtils.isEmpty(area)){
                        List<String> areaIds = Splitter.on(",").trimResults().splitToList(area);
                        List<PmDictionaries> areas = pmDictionarieService.getBaseMapper().selectList(new QueryWrapper<PmDictionaries>().in("id", areaIds));
                        x.setAreas(areas);
                    }
                });
        return ResultVo.success(page);
    }


    /**
     * 新增校验
     * @param params
     * @param user
     * @param map
     */
    private void insertValidAndSetValue(String params,User user , Map<String,Object> map) {
        Assert.paramsNotNullValid(user,"userName","telphone","creator");
        CheckUtils.checkCellphone(user.getTelphone());
        if(!Objects.isNull(user.getCompanyEmail())){
            CheckUtils.checkEmail(user.getCompanyEmail());
        }
        user.setCreateTime(new Date());
        user.setPassword(MD5Util.md5(defaultPwd));
        user.setUserLogin(PinyinUtil.getPinyinToLowerCase(user.getUserName()));
        user.setDictionName(user.getUserLogin());

        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("user_name", user.getUserName())
                .orderByDesc("create_time"));

        if(CollectionUtils.isNotEmpty(users)){//处理登录名重名
            User user1 = users.get(0);
            String userLogin = user1.getUserLogin();
            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(userLogin);
            int end = 1;
            if(matcher.find()){
                String group = matcher.group();
                end = Integer.valueOf(group).intValue();
                end += 1;
            }else {
                userLogin = userLogin;
            }
            userLogin = userLogin + end;
            user.setUserLogin(userLogin);
        }
        Object roleIds = map.get("roleIds");
        Assert.objNotNull(roleIds,"roleIds");

    }

    /**
     * 保存角色
     * @param user
     * @param map
     */
    private void saveUserRole(User user, Map<String, Object> map) {
        Object roleIds = map.get("roleIds");
        if(Objects.isNull(roleIds)) return;

        List<String> rIds = Splitter.on(",").trimResults().splitToList(map.get("roleIds").toString());
        List<UserRole> saveUserRoles = new ArrayList<>();
        userRoleService.delete(user.getUserId());

        for(String rId:rIds){
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Integer.valueOf(rId));
            saveUserRoles.add(ur);
        }
        userRoleService.saveBatch(saveUserRoles,saveUserRoles.size());
    }


    @Override
    public Object selectUserPer(Integer userId) {
        Assert.objNotNull(userId,"userId");
        List<UserRole> roles = userRoleService.getBaseMapper().selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        PerPermission rs = null;
        if(!CollectionUtils.isEmpty(roles)){
             List<Integer> roleIds = Lists.newArrayList();
             roles.forEach(x->{
                 roleIds.add(x.getRoleId());
             });
            if(!CollectionUtils.isEmpty(roleIds)){
                List<PerRolePermission> pers = rolePermissionService.getBaseMapper().selectList(new QueryWrapper<PerRolePermission>().in("role_id", roleIds));
                if(!CollectionUtils.isEmpty(pers)){
                    List<Integer> perIds = Lists.newArrayList();
                    pers.forEach(y->{
                        perIds.add(y.getPerId());
                    });
                    List<PerPermission> permissions = permissionService.getBaseMapper().selectList(new QueryWrapper<PerPermission>().in("per_id", perIds));
                    rs = new DataFormatPer().handlerDataForMater(permissions);
                }
            }
        }
        return ResultVo.success(rs);
    }



 class DataFormatPer{
    /**
     * 　处理返回数据格式
     * @param permissions
     * @return
     */
    private PerPermission handlerDataForMater(List<PerPermission> permissions) {
        PerPermission first = new PerPermission();
        first.setPerId(0);

        PerPermission perPermission = firstPer(first, permissions);
        return perPermission;

    }

     /**
      * 子节点
      * @param totle
      * @param perPermission
      * @return
      */
    public List<PerPermission> subPers(List<PerPermission> totle,PerPermission perPermission){
        List<PerPermission> subList = Lists.newArrayList();
        for (PerPermission permission : totle) {
            if(permission.getPerPid().compareTo(perPermission.getPerId()) == 0){
                subList.add(permission);
            }
        }
        return subList;
    }

     /**
      * 递归处理
      * @param totle
      * @return
      */
    public PerPermission firstPer(PerPermission firstPer,List<PerPermission> totle){
        firstPer.setMenus(Lists.newArrayList());
        firstPer.setButtons(Lists.newArrayList());

        List<PerPermission> perPermissions = subPers(totle, firstPer);
        if(CollectionUtils.isEmpty(perPermissions)){
            return firstPer;
        }
        for (PerPermission permission : perPermissions) {
            if(permission.getPerPid().compareTo(firstPer.getPerId()) == 0){

                if(permission.getPerType()==0){
                    firstPer.getMenus().add(permission);
                }
                if(permission.getPerType()==1){
                    firstPer.getButtons().add(permission);
                }
                firstPer(permission,totle);
            }
        }
        return firstPer;
    }
 }
}
