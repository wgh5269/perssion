package com.wgh.service;

import com.google.common.collect.Lists;
import com.wgh.common.RequestHolder;
import com.wgh.dao.*;
import com.wgh.model.SysAcl;
import com.wgh.model.SysRoleAcl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/5/13.
 */
@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    //1.获取当前用户权限列表
    public List<SysAcl> getCurrentUserAclList(){
           int userId= RequestHolder.getCurrentUser().getId();

           return getUserAclList(userId);
    }

    //2.获取某一个角色当前已分配的权限点
    public List<SysAcl> getRoleAclList(int roleId){
        List<Integer> aclList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclList)){
            return Lists.newArrayList();
        }
        return  sysAclMapper.getByIdList(aclList);
    }


    //3.查询某个用户已分配的权限
    public List<SysAcl> getUserAclList(int userId){
           if (isSuperRoot()){
               return  sysAclMapper.getAll();
           }
           //取出用户已经分配的角色ID
           List<Integer> userRoleIdList=sysRoleUserMapper.getRoleIdListByUserId(userId);
           if (CollectionUtils.isEmpty(userRoleIdList)){
               return Lists.newArrayList();
           }
           List<Integer> AclIdList=sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
           if (CollectionUtils.isEmpty(AclIdList)){
               return  Lists.newArrayList();
           }
           return  sysAclMapper.getByIdList(AclIdList);


    }

    //4.判断是否是超级管理员
    public  boolean isSuperRoot(){

        return  true;
    }
}
