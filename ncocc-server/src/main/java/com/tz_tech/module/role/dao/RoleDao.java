package com.tz_tech.module.role.dao;

import com.tz_tech.module.common.model.Role;
import com.tz_tech.module.common.model.UserRoleDto;

import java.util.List;
import java.util.Map;

public interface RoleDao {

    public List<Map<String,Object>> qryAllRoles() throws Exception;

    public List<Map<String,Object>> qryRolesByLoginName(String loginName)throws Exception;

    public int addRoleForUserName(UserRoleDto userRoleDto) throws Exception;

    public int clearRolesForUser(String userName)throws Exception;

    public List<Map<String,Object>> qryAllRolesByPage(Map<String,Object> paramMap)throws Exception;

    public Long qryAllRolesCount()throws Exception;

    public boolean checkRoleExist(String role)throws Exception;

    public int addRole(Role role)throws Exception;

    public int modifyRole(Role role)throws Exception;

    public int delRoleById(String ids) throws Exception;

    public int delRoleMenuById(String ids) throws Exception;

    public int delRoleUserById(String ids) throws Exception;

    public int clearMenusForRole(Role role)throws Exception;

    public int updateRoleMenu(Role role) throws Exception;

    public int qryParentMenuById(Long id)throws Exception;

    public void addMenusForRole(Role role) throws Exception;
}
