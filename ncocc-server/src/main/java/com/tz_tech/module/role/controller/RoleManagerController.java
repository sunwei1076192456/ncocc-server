package com.tz_tech.module.role.controller;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.Role;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.common.model.UserRoleDto;
import com.tz_tech.module.role.service.RoleManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 为用户配置角色
     * @param userRoleDto
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rolesConfigure.do")
    @ResponseBody
    public Result rolesConfigure(@RequestBody(required=false) List<UserRoleDto> userRoleDto, HttpServletRequest request)throws Exception{
        return roleManagerService.rolesConfigure(userRoleDto);
    }

    @RequestMapping(value = "/getAllRolesForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public Result getAllRolesForPage(@RequestParam("page") String page,
                                     @RequestParam("pageSize") String pageSize) throws Exception{
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("page",Long.valueOf(page));
        paramMap.put("pageSize",Long.valueOf(pageSize));
        return roleManagerService.getAllRolesForPage(paramMap);
    }

    @RequestMapping(value = "/saveRole.do",method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(@RequestBody(required=false) Role role, HttpServletRequest request) throws Exception{
        return roleManagerService.saveRole(role);
    }

    @RequestMapping(value = "/deleteRole.do",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteRole(@RequestBody List<Long> groupId, HttpServletRequest request) throws Exception{
        return roleManagerService.deleteRole(groupId);
    }

    @RequestMapping(value = "/modifyRole.do",method = RequestMethod.POST)
    @ResponseBody
    public Result modifyRole(@RequestBody(required=false) Role role, HttpServletRequest request) throws Exception{
        return roleManagerService.modifyRole(role);
    }

    /**
     * 为角色配置权限
     * @param role
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/menuConfigure.do",method = RequestMethod.POST)
    @ResponseBody
    public Result menuConfigure(@RequestBody(required=false) Role role, HttpServletRequest request)throws Exception{
        return roleManagerService.menuConfigure(role);
    }
}
