package com.tz_tech.module.menu.dao;

import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.User;
import org.apache.commons.collections4.MapUtils;
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

    @Override
    public List<Map<String, Object>> qryAllSecMenus() throws Exception {
        String sql = "select id,name from sys_menu where parent_id <> 0 order by parent_id,sort";
        return super.queryForList(sql,new HashMap<String, Object>());
    }

    @Override
    public List<Map<String, Object>> qryAllMenuByPage(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id,name,url,parent_id,sort,remark,icon from sys_menu ");
        if(null != MapUtils.getString(paramMap,"name")){
            sb.append(" where name like CONCAT('%',:name,'%')");
        }
        sb.append(" order by parent_id,sort limit :fromPageSize,:pageSize ");
        paramMap.put("fromPageSize",(MapUtils.getLong(paramMap,"page")-1)*MapUtils.getLong(paramMap,"pageSize"));
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public Long qryAllMenuCount() throws Exception {
        String sql = "select count(1) from sys_menu";
        return super.queryForObject(sql,new HashMap<String, Object>(),Long.class);
    }

    @Override
    public boolean checkMenuExist(String name) throws Exception {
        String sql = "select count(1) from sys_menu where name=:name";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name",name);
        int i = super.queryForInt(sql,paramMap);
        if(i > 0){
            //存在
            return true;
        }
        return false;
    }

    @Override
    public int addMenu(Menu menu) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into sys_menu(name,url,parent_id,sort,remark,icon)");
        sb.append(" values (:name,:url,:parent_id,:sort,:remark,:icon)");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name",menu.getName());
        paramMap.put("url",menu.getUrl());
        paramMap.put("parent_id",menu.getParent_id());
        paramMap.put("sort",menu.getSort());
        paramMap.put("remark",menu.getRemark());
        paramMap.put("icon",menu.getIcon());
        return super.update(sb.toString(),paramMap);
    }

    @Override
    public int delMenuById(String ids) throws Exception {
        String sql = "delete from sys_menu where id in (" + ids + ")";
        return super.update(sql,new HashMap<String,Object>());
    }

    @Override
    public int modifyMenu(Menu menu) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" update sys_menu set name=:name,url=:url,parent_id=:parent_id, ");
        sb.append(" sort=:sort,remark=:remark,icon=:icon ");
        sb.append("  where id=:id");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name",menu.getName());
        paramMap.put("url",menu.getUrl());
        paramMap.put("parent_id",menu.getParent_id());
        paramMap.put("sort",menu.getSort());
        paramMap.put("remark",menu.getRemark());
        paramMap.put("icon",menu.getIcon());
        paramMap.put("id",menu.getId());
        return super.update(sb.toString(),paramMap);
    }

    @Override
    public int delMenuRoleById(String ids) throws Exception {
        String sql = "delete from sys_role_menu where menu_id in(" + ids + ")";
        return super.update(sql,new HashMap<String,Object>());
    }
}
