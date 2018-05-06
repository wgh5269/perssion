package com.wgh.Dto;

import com.google.common.collect.Lists;
import com.wgh.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */
@Getter
@Setter
@ToString
public class DeptLevelDto  extends SysDept{
    private List<DeptLevelDto> deptList= Lists.newArrayList();
    public  static  DeptLevelDto adept(SysDept dept){
        DeptLevelDto dto=new DeptLevelDto();
        BeanUtils.copyProperties(dept,dto);
        return  dto;
    }
}
