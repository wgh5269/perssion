package com.wgh.service;

import com.google.common.base.Preconditions;
import com.wgh.common.RequestHolder;
import com.wgh.dao.SysDeptMapper;
import com.wgh.exception.ParamException;
import com.wgh.model.SysDept;
import com.wgh.param.DeptParam;
import com.wgh.util.BeanValidator;
import com.wgh.util.IpUtil;
import com.wgh.util.Levelutil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import static com.wgh.util.Levelutil.calcuteLevel;


/**
 * Created by Administrator on 2018/4/24.
 */
@Slf4j
@Service
public class SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    public  void save(DeptParam param){
        BeanValidator.check(param);
        if(checeExist(param.getParentId(),param.getName(),param.getId())){
            throw  new ParamException("同一层级下存在相同名称的部门");
        }
        String FartherLevel=getLevel(param.getParentId());
        SysDept dept=SysDept.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark()).build();
        dept.setLevel(calcuteLevel(FartherLevel,param.getParentId()));
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());//todo
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);
    }

    public  void update(DeptParam param){
        BeanValidator.check(param);
        if(checeExist(param.getParentId(),param.getName(),param.getId())){
            throw  new ParamException("同一层级下存在相同名称的部门");
        }
        //判断之前的部门是否存在
        SysDept before=sysDeptMapper.selectByPrimaryKey(param.getId());
        //保证before不为空
        Preconditions.checkNotNull(before,"待更新部门不存在");
        //再判断同一层级下是否存在相同的部门
        if(checeExist(param.getParentId(),param.getName(),param.getId())){
            throw  new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept after=SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(calcuteLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());//todo
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        //做完这些工作  还需声明一个方法updateWithChild()
        //因为更新部门  要同时当前部门和他的自部门
        updateWithChild(before,after);

    }

    @Transactional
    public void updateWithChild(SysDept before,SysDept after){
        //此方法要么同时成功 要么同时失败  所以要放到事务里面控制

        //1.取出新部门的前缀
        String newLevelPrefix=after.getLevel();
        System.out.println("取出新部门的前缀:"+newLevelPrefix);
        //2.取出之前部门的前缀
        String oldLevePrefix=before.getLevel();
        System.out.println("取出之前部门的前缀:"+oldLevePrefix);
        //3.对比新老部门的前缀  如果不一致的时候 我们才需要更新自部门
        if(!after.getLevel().equals(before.getLevel())){
            log.info("执行子部门更新开始");
            //更新操作
            //1.先取出拥有当前被更新部门前缀的所有子部门
            // {比如：我当前部门是0.1  那么0.12 0.13也需要被取出来}
            List<SysDept> deptList=sysDeptMapper.getChildDeptListByLevel(oldLevePrefix);
            System.out.println("取到所有子部门:"+deptList);
            //2.判断当前deptList不为空的时候才进行处理
            if(CollectionUtils.isNotEmpty(deptList)){
               //3.遍历自部门
                for (SysDept dept:deptList) {
                    //1.首先取出当前部门的level
                    String level=dept.getLevel();
                    //2.判断是和被更新部门的前缀一样的话 更新新部门的前缀上去
                    if(level.indexOf(oldLevePrefix)==0){
                        //3.更新自部门的level 然后重新设置dept里面的level
                       level=newLevelPrefix + level.lastIndexOf(oldLevePrefix.length());
                       dept.setLevel(level);
                    }
                    //写完这些 需要到sysDeptMapper中写一个批量更新的方法batchUpdateLevel()
                    //然后传入我们更新完毕的deptList
                    sysDeptMapper.batchUpdateLevel(deptList);
                }
            }
        }
                //然后再执行更新
               sysDeptMapper.updateByPrimaryKey(after);

    }

    private  boolean checeExist(Integer parentId ,String deptName,Integer deptId ){

        return  sysDeptMapper.countByNameANDParentId(parentId,deptName,deptId)>0;
    }
    private  String getLevel(Integer id){
          SysDept dept=sysDeptMapper.selectByPrimaryKey(id);
          if(dept==null){
              return  null;
          }
          return  dept.getLevel();

    }


}
