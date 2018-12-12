package com.tz_tech.module.role.dao;

import com.tz_tech.module.common.dao.CommonDao;
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
}
