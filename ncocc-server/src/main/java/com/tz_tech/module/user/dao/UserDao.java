package com.tz_tech.module.user.dao;

import java.util.List;
import java.util.Map;
import com.tz_tech.module.common.model.User;

public interface UserDao 
{
	public Map<String,Object> qryUserByUserName(User user) throws Exception;

	public List<Map<String, Object>> qryUserRoleByUserName(String userName) throws Exception;

	/*public List<>*/
	public List<Map<String, Object>> qryAllUserByLoginName(Map<String, Object> paramMap) throws Exception;

	public int addUser(User user) throws Exception;

	public Long qryAllUserCount(Map<String, Object> paramMap) throws Exception;

	public int delUserById(String ids) throws Exception;

	public int delUserRolesById(String ids) throws Exception;

	public boolean checkUserExist(String loginName) throws Exception;

	public int modifyUser(User user) throws Exception;

}
