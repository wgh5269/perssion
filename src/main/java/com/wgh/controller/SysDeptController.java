package com.wgh.controller;

import com.sun.deploy.net.HttpResponse;
import com.wgh.Dto.DeptLevelDto;
import com.wgh.common.JsonData;
import com.wgh.param.DeptParam;
import com.wgh.service.SysDeptService;
import com.wgh.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    private ModelAndView deptPage(){
        return  new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    private JsonData savaDept(DeptParam param){
            System.out.println(param.getParentId()+"得到的父级");
            sysDeptService.save(param);
            return JsonData.success();
    }
    @RequestMapping("/tree.json")
    @ResponseBody
    private JsonData deptTree(){
        List<DeptLevelDto> dtoList=sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }
    @RequestMapping("/update.json")
    @ResponseBody
    private JsonData updateDept(DeptParam param){
        sysDeptService.update(param);
        return JsonData.success();
    }
}
