package com.tz_tech.module.menu.controller;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.menu.service.MenuManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/menuManger")
public class MenuManagerController {

    @Autowired
    private MenuManagerService menuManagerService;

    @RequestMapping(value = "/getMenuByLoginName.do",method = RequestMethod.GET)
    @ResponseBody
    public Result getMenuByLoginName(@RequestParam("loginName") String loginName, HttpServletRequest request){
        return menuManagerService.getMenuByLoginName(loginName);
    }
}
