package com.tz_tech.module.role.dao;

import java.util.List;
import java.util.Map;

public interface RoleDao {

    public List<Map<String,Object>> qryAllRoles() throws Exception;

    public List<Map<String,Object>> qryRolesByLoginName(String loginName)throws Exception;
}
