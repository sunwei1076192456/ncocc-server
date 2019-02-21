package com.tz_tech.module.role.dao;

import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.Role;
import com.tz_tech.module.common.model.UserRoleDto;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl extends CommonDao implements RoleDao {

    @Override
    public List<Map<String, Object>> qryAllRoles() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id, role, name, describer,role as value,name as label from sys_role");
        return super.queryForList(sb.toString(),new HashMap<String,Object>());
    }

    @Override
    public List<Map<String, Object>> qryRolesByLoginName(String loginName) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select sur.role_id as roleId from sys_user_role sur ");
        sb.append(" where sur.user_login_name=:loginName ");
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("loginName",loginName);
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public int addRoleForUserName(UserRoleDto userRoleDto) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into sys_user_role(user_login_name,role_id) ");
        sb.append(" values(:userName,:roleId)");
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userName",userRoleDto.getUserName());
        paramMap.put("roleId",userRoleDto.getRoleId());
        return super.update(sb.toString(),paramMap);
    }

    @Override
    public int clearRolesForUser(String userName) throws Exception {
        String sql = "delete from sys_user_role where user_login_name=:userName";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userName",userName);
        return super.update(sql,paramMap);
    }

    @Override
    public List<Map<String, Object>> qryAllRolesByPage(Map<String, Object> paramMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" select id,role,name,describer,modules from sys_role ");
        sb.append(" limit :fromPageSize,:pageSize ");
        paramMap.put("fromPageSize",(MapUtils.getLong(paramMap,"page")-1)*MapUtils.getLong(paramMap,"pageSize"));
        return super.queryForList(sb.toString(),paramMap);
    }

    @Override
    public Long qryAllRolesCount() throws Exception {
        String sql = "select count(1) from sys_role";
        return super.queryForObject(sql,new HashMap<String, Object>(),Long.class);
    }

    @Override
    public boolean checkRoleExist(String role) throws Exception {
        String sql = "select count(1) from sys_role where role=:role";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("role",role);
        int i = super.queryForInt(sql,paramMap);
        if(i > 0){
            //存在
            return true;
        }
        return false;
    }

    @Override
    public int addRole(Role role) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into sys_role(role,name,describer)");
        sb.append(" values (:role,:name,:describer)");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("role",role.getRole());
        paramMap.put("name",role.getName());
        //paramMap.put("modules",role.getName());这个权限初始值为空
        paramMap.put("describer",role.getDescriber());
        return super.update(sb.toString(),paramMap);
    }

    @Override
    public int modifyRole(Role role) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" update sys_role set name=:name,modules=:modules,describer=:describer ");
        sb.append("  where id=:id");
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name",role.getName());
        paramMap.put("modules",role.getName());
        paramMap.put("describer",role.getDescriber());
        paramMap.put("id",Long.valueOf(role.getId()));
        return super.update(sb.toString(),paramMap);
    }

    @Override
    public int delRoleById(String ids) throws Exception {
        String sql = "delete from sys_role where id in (" + ids + ")";
        return super.update(sql,new HashMap<String,Object>());
    }

    @Override
    public int delRoleMenuById(String ids) throws Exception {
        //删除角色权限
        String sql = "delete from sys_role_menu where role_id in (" + ids + ")";
        return super.update(sql,new HashMap<String, Object>());
    }

    @Override
    public int delRoleUserById(String ids) throws Exception {
        String sql = "delete from sys_user_role where role_id in (" + ids + ")";
        return super.update(sql,new HashMap<String, Object>());
    }

    @Override
    public int clearMenusForRole(Role role) throws Exception {
        String sql = "delete from sys_role_menu where role_id=:id";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",role.getId());
        return super.update(sql,paramMap);
    }

    @Override
    public int updateRoleMenu(Role role) throws Exception {
        String sql = "update sys_role set modules=:modules where id=:id";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",role.getId());
        paramMap.put("modules",role.getModules());
        return super.update(sql,paramMap);
    }

    @Override
    public int qryParentMenuById(Long id) throws Exception {
        String sql = "select parent_id from sys_menu where id=:id";
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",id);
        return super.queryForInt(sql,paramMap);
    }

    @Override
    public void addMenusForRole(Role role) throws Exception {
        if(role != null && role.getModules() != null && !"".equals(role.getModules())){
            String[] menu = role.getModules().split(";");
            Map[] batchMap=new Map[menu.length];
            for(int i=0;i<menu.length;i++){
                Map tempMap = new HashMap();
                tempMap.put("roleId",role.getId());
                tempMap.put("menuId",Long.valueOf(menu[i]));
                batchMap[i] = tempMap;
            }
            String sql = "insert into sys_role_menu(role_id,menu_id) values(:roleId,:menuId)";
            super.batchUpdate(sql,batchMap);
        }
    }
}
