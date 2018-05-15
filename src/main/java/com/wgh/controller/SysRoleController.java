package com.wgh.controller;



import com.wgh.common.JsonData;
import com.wgh.param.RoleParam;
import com.wgh.service.SysRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2018/5/10.
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("role.page")
    public ModelAndView page(){
        return  new ModelAndView("role");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param){
        sysRoleService.save(param);
        return  JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public  JsonData  updateRole(RoleParam param){
        sysRoleService.update(param);
        return  JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public  JsonData  List(){
        return JsonData.success(sysRoleService.getAll());
    }

    @RequestMapping("/roleTree.json")
    @ResponseBody
    public  JsonData roleTree(@RequestParam("roleId") int roleId){
        return JsonData.success();
    }

}
