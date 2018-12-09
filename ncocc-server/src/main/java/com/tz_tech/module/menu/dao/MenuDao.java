package com.tz_tech.module.menu.dao;

import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.User;
import java.util.List;

public interface MenuDao {
    /**
     * 通过登录名查菜单
     * @param user
     * @return
     * @throws Exception
     */
    public List<Menu> findTreeByLoginName(User user) throws Exception;
}
