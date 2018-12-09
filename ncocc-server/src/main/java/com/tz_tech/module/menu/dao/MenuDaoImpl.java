package com.tz_tech.module.menu.dao;

import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MenuDaoImpl extends CommonDao implements MenuDao{

    @Override
    public List<Menu> findTreeByLoginName(User user) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select sm.id,sm.name,sm.url,sm.parent_id,sm.remark,");
        sb.append(" sm.icon,sm.sort from sys_menu sm ");
        sb.append(" left join sys_role_menu srm on srm.menu_id = sm.id");
        sb.append(" left join sys_user_role sur on sur.role_id = srm.role_id");
        sb.append(" where sur.user_login_name = :loginName");
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("loginName", user.getLoginName());
//        return super.queryForList(sb.toString(),paramMap,Menu.class);
        return super.query(sb.toString(),paramMap,new BeanPropertyRowMapper<Menu>(Menu.class));
    }
}
