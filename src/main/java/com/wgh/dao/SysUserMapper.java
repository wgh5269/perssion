package com.wgh.dao;

import com.wgh.beans.PageQuery;
import com.wgh.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByTelephone(@Param("telephone") String telephone,@Param("id") Integer userId);

    int countByMail(@Param("mail")String mail, @Param("id")Integer userId);

    SysUser findByKeyword(@Param("keyword")String keyword);

    int countByDeptId(@Param("deptId")int deptId);

    List<SysUser> getPageByDeptId(@Param("deptId")int deptId,@Param("page") PageQuery page);

    List<SysUser> getAll();
}