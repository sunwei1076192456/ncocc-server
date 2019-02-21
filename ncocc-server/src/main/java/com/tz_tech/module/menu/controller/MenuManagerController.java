package com.tz_tech.module.menu.controller;

import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.Role;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.menu.service.MenuManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/getAllSecMenu.do",method = RequestMethod.GET)
    @ResponseBody
    public Result getAllSecMenu(HttpServletRequest request) throws Exception{
        return menuManagerService.getAllSecMenu();
    }

    @RequestMapping(value = "/getAllMenuForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public Result getAllMenuForPage(@RequestParam("page") String page,
                                     @RequestParam("pageSize") String pageSize,@RequestParam(value = "name",required=false) String name) throws Exception{
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("page",Long.valueOf(page));
        paramMap.put("pageSize",Long.valueOf(pageSize));
        paramMap.put("name",name);
        return menuManagerService.getAllMenuForPage(paramMap);
    }

    @RequestMapping(value = "/saveMenu.do",method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenu(@RequestBody(required=false) Menu menu, HttpServletRequest request) throws Exception{
        return menuManagerService.saveMenu(menu);
    }

    @RequestMapping(value = "/deleteMenu.do",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteMenu(@RequestBody List<Long> groupId, HttpServletRequest request) throws Exception{
        return menuManagerService.deleteMenu(groupId);
    }

    @RequestMapping(value = "/modifyMenu.do",method = RequestMethod.POST)
    @ResponseBody
    public Result modifyMenu(@RequestBody(required=false) Menu menu, HttpServletRequest request) throws Exception{
        return menuManagerService.modifyMenu(menu);
    }
}
