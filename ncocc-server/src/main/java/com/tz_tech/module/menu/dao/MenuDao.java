package com.tz_tech.module.menu.dao;

import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.User;
import java.util.List;
import java.util.Map;

public interface MenuDao {
    /**
     * 通过登录名查菜单
     * @param user
     * @return
     * @throws Exception
     */
    public List<Menu> findTreeByLoginName(User user) throws Exception;

    public List<Map<String,Object>> qryAllSecMenus()throws Exception;

    public List<Map<String, Object>> qryAllMenuByPage(Map<String, Object> paramMap)throws Exception;

    public Long qryAllMenuCount()throws Exception;

    public boolean checkMenuExist(String name)throws Exception;

    public int addMenu(Menu menu)throws Exception;

    public int delMenuById(String ids) throws Exception;

    public int modifyMenu(Menu menu)throws Exception;

    public int delMenuRoleById(String ids) throws Exception;
}
