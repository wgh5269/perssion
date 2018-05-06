package com.wgh.controller;

import com.wgh.common.ApplicationContextHelper;
import com.wgh.common.JsonData;
import com.wgh.dao.SysAclModuleMapper;
import com.wgh.exception.ParamException;
import com.wgh.exception.PermissionException;
import com.wgh.model.SysAclModule;
import com.wgh.param.TestVo;
import com.wgh.util.BeanValidator;
import com.wgh.util.JsonMapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/4/20.
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class Test {
    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        //throw new PermissionException("test exception");
        return JsonData.success("hello, permission");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }
}
