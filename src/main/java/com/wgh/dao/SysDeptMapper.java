package com.wgh.dao;

import com.wgh.model.SysDept;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(@Param("id")Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(@Param("id")Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getAllDept();

    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    void  batchUpdateLevel(@Param("SysDeptList") List<SysDept> SysDeptList);

    int countByNameANDParentId(@Param("parentId") Integer parentId,@Param("name") String deptName,@Param("id") Integer id);
}