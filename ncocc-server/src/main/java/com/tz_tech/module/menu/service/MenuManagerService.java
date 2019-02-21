package com.tz_tech.module.menu.service;

import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.menu.dao.MenuDao;
import com.tz_tech.module.menu.manager.MenuManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    public Result getAllSecMenu()throws Exception{
        Result result = Result.fail();
        List<Map<String, Object>> secMenu = menuDao.qryAllSecMenus();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("secMenu",secMenu);
        result = Result.success(data);
        return result;
    }

    public Result getAllMenuForPage(Map<String,Object> paramMap)throws Exception{
        Result result = Result.fail();
        //分页查询
        List<Map<String, Object>> menuList = menuDao.qryAllMenuByPage(paramMap);
        //查出总的菜单数量
        Long totalCount = menuDao.qryAllMenuCount();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("menu",menuList);
        data.put("totalCount",totalCount);
        result = Result.success(data);
        return result;
    }

    public Result saveMenu(Menu menu)throws Exception{
        Result result = Result.fail();
        try {
            if(menu != null){
                //校验menu是否重复
                if(!checkMenuExist(menu.getName())){
                    int i = menuDao.addMenu(menu);
                    if(i > 0){
                        //成功
                        result = Result.success();
                    }
                }else {
                    result.setResultCode(201);
                    result.setResultMsg("菜单名已存在，请重新输入!");
                }
            }
        } catch (Exception e) {
            result.setResultMsg("添加失败!");
        }
        return result;
    }
    private boolean checkMenuExist(String name)throws Exception{
        return menuDao.checkMenuExist(name);
    }

    public Result deleteMenu(List<Long> groupId)throws Exception{
        Result result = Result.fail();
        if(groupId != null && groupId.size() > 0){
            //list转为以逗号分隔的字符串
            try {
                String ids = StringUtils.join(groupId.toArray(), ",");
                //先把菜单和角色关系表的数据删掉
                menuDao.delMenuRoleById(ids);
                //再删菜单---后期考虑还是在表里加一个状态来标识有效和无效
                menuDao.delMenuById(ids);
                result = Result.success();
            } catch (Exception e) {
                e.printStackTrace();
                result.setResultMsg("删除失败!");
            }
        }else {
            result.setResultMsg("请至少勾选一条记录删除!");
        }
        return result;
    }

    public Result modifyMenu(Menu menu)throws Exception{
        Result result = Result.fail();
        try {
            int i = menuDao.modifyMenu(menu);
            if(i > 0){
                result = Result.success();
            }
        } catch (Exception e) {
            result.setResultMsg("更新失败!");
        }
        return result;
    }

}
