package com.tz_tech.module.menu.service;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.menu.dao.MenuDao;
import com.tz_tech.module.menu.manager.MenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MenuManagerService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuManager menuManager;

    public Result getMenuByLoginName(String loginName){
        Result menuResult = Result.fail();
        //通过登录名查询该用户下的所有菜单以及子菜单
        User user = new User();
        user.setLoginName(loginName);
        Map<String,Object> data = menuManager.findTree(user);
        menuResult = Result.success(data);

        return menuResult;
    }

}
