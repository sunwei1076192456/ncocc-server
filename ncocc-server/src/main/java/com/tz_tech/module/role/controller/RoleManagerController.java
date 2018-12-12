package com.tz_tech.module.role.controller;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.role.service.RoleManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/roleManger")
public class RoleManagerController {

    @Autowired
    private RoleManagerService roleManagerService;

    @RequestMapping(value = "/getAllRoles.do")
    @ResponseBody
    public Result getAllRoles(HttpServletRequest request)throws Exception{
        return roleManagerService.getAllRoles();
    }

    @RequestMapping(value = "/getAllRolesByLoginName.do")
    @ResponseBody
    public Result getAllRolesByLoginName(@RequestParam("loginName") String loginName)
            throws Exception{
        return roleManagerService.getAllRolesByLoginName(loginName);
    }
}
