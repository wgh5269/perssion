package com.wgh.controller;

import com.wgh.beans.PageQuery;
import com.wgh.beans.PageResult;
import com.wgh.common.JsonData;
import com.wgh.model.SysUser;
import com.wgh.param.DeptParam;
import com.wgh.param.UserParam;
import com.wgh.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/4/29.
 */
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    private JsonData savaDept(UserParam param){
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    private JsonData updateDept(UserParam param){
        sysUserService.update(param);
        return JsonData.success();
    }
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }
}
