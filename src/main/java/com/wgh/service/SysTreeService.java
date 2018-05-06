package com.wgh.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wgh.Dto.DeptLevelDto;
import com.wgh.dao.SysDeptMapper;
import com.wgh.model.SysDept;
import com.wgh.util.Levelutil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018/3/25.
 */
@Service
public class SysTreeService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree(){
           //从数据库拿出部门数据 封装进   SysDept
           List<SysDept> depts=sysDeptMapper.getAllDept();
           //声明一个适配的DeptLevelDto
           List<DeptLevelDto> dtoList= Lists.newArrayList();
           //遍历部门数据--->depts
        for (SysDept dept:depts) {
             //转换成dto  -->dept--dto
             DeptLevelDto dto=DeptLevelDto.adept(dept);
             //添加到dto列表
             dtoList.add(dto);
        }
        return  deptToTree(dtoList);
    }

    public List<DeptLevelDto> deptToTree( List<DeptLevelDto> deptLeveList) {
        if (CollectionUtils.isEmpty(deptLeveList)) {
            return Lists.newArrayList();
        }
        //声明一个leve-->{dept1,dept2....}列表存储数据
        Multimap<String, DeptLevelDto> leveDeptMap = ArrayListMultimap.create();
        //专门声明一个根部门的存储列表存储
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLeveList
                ) {
            leveDeptMap.put(dto.getLevel(), dto);
            //判断为根部门 存入根部门列表
            if (Levelutil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //声明一个排序的方法
        //按照seq从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        transformDeptTree(rootList, Levelutil.ROOT, leveDeptMap);
        return rootList;
    }



    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            // 遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = Levelutil.calcuteLevel(level, deptLevelDto.getId());
            // 处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

}
