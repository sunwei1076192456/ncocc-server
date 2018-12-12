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

}
