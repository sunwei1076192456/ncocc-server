package com.tz_tech.module.user.controller;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.user.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/userManger")
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Result login(@RequestBody(required=false) User user, HttpServletRequest request) throws Exception{
        return userManagerService.login(user,request);
    }

    @RequestMapping(value = "/getUserByToken.do",method = RequestMethod.GET)
    @ResponseBody
    public Result getUserByToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return userManagerService.getUserByToken(request);
    }
}
