package com.wgh.service;

import com.google.common.base.Preconditions;
import com.wgh.common.RequestHolder;
import com.wgh.dao.SysRoleMapper;
import com.wgh.exception.ParamException;
import com.wgh.model.SysRole;
import com.wgh.param.RoleParam;
import com.wgh.util.BeanValidator;
import com.wgh.util.IpUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    public  void  save(RoleParam param){
        BeanValidator.check(param);
        if (checkExit(param.getName(),param.getId())){
            throw  new ParamException("角色名称已存在");
        }
        SysRole role=SysRole.builder().name(param.getName()).status(param.getStatus())
                .type(param.getType()).remark(param.getRemark()).build();
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);
    }

    public  void  update(RoleParam param){
        BeanValidator.check(param);
        if (checkExit(param.getName(),param.getId())){
            throw  new ParamException("角色名称已存在");
        }
        SysRole  before=sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的角色不存在 ");

        SysRole  after=SysRole.builder().name(param.getName()).status(param.getStatus())
                .type(param.getType()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);

    }

    public List<SysRole> getAll(){
        return  sysRoleMapper.getAll();
    }
    public  boolean checkExit(String name,Integer id){

        return  sysRoleMapper.countByName(name,id)>0;
    }

}
