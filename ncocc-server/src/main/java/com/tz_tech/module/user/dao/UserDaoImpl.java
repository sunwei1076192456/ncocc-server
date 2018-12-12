package com.tz_tech.module.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.user.dao.UserDao;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CommonDao implements UserDao
{

	@Override
	public Map<String,Object> qryUserByUserName(User user) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,name,login_name,password,email,phone,active,active_date,expired_date ");
		sb.append(" from sys_user where login_name = :login_name ");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("login_name", user.getLoginName());
		return super.queryForMap(sb.toString(), paramMap);
	}

	/*
	@Override
	public List<Map<String, Object>> listAllUser(User user,String userId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select MU.user_id,MU.user_name,MU.user_pwd,MU.user_phone,MU.user_mail,US.status ");
		sb.append(" from MQ_USER MU left join USER_STATE US on US.user_id = MU.user_id where MU.user_id != :userId ");
		if(null != user.getUserName() && !"".equals(user.getUserName()))
		{
			sb.append(" and MU.user_name like CONCAT('%',:userName,'%') ");
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userId", userId);
		if(null != user.getUserName() && !"".equals(user.getUserName()))
		{
			paramMap.put("userName", user.getUserName());
		}
		return super.queryForList(sb.toString(), paramMap);
	}
	*/

	@Override
	public List<Map<String, Object>> qryUserRoleByUserName(String userName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select sr.id,sr.role,sr.name from sys_user_role sur ");
		sb.append(" left join sys_role sr on sr.id=sur.role_id ");
		sb.append(" where sur.user_login_name=:userName ");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userName",userName);
		return super.queryForList(sb.toString(),paramMap);
	}

	@Override
	public List<Map<String, Object>> qryAllUserByLoginName(Map<String, Object> paramMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		/*Map<String,Object> paramMap = new HashMap<String,Object>();*/
		sb.append(" select su.login_name as loginName, su.phone as phone,su.name as name,");
		sb.append(" (case when sr.role='management' then 0 when sr.role='financial_department' then 1 ");
		sb.append(" when sr.role='dispatcher' then 2 when sr.role='operator' then 3 else 4 end) ");
		sb.append(" as usertype from sys_user su");
		sb.append(" left join sys_user_role sur on sur.user_login_name=su.login_name ");
		sb.append(" left join sys_role sr on sr.id = sur.role_id ");
		if(null != MapUtils.getString(paramMap,"state")){
			sb.append(" where su.active=:state");
		}else{
			sb.append(" where su.active in (0,1)");
		}
		if(null != MapUtils.getString(paramMap,"loginName")){
			sb.append(" and su.login_name like CONCAT('%',:loginName,'%')");
		}
		if(null != MapUtils.getString(paramMap,"userType")){
			sb.append(" and sr.role=:userType");
		}
		return super.queryForList(sb.toString(),paramMap);
	}
}
