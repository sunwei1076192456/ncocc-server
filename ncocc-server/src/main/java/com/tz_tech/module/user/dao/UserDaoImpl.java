package com.tz_tech.module.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.tz_tech.module.common.dao.CommonDao;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.user.dao.UserDao;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CommonDao implements UserDao
{
	/*private static final Map<String, String> userColumnPairs = ImmutableMap.<String,String>builder()
			.put("id"			, "id"	          )
			.put("name"	        , "name"		      )
			.put("loginName"	        , "login_name"		      )
			.put("password"	    , "password"		  )
			.put("email"	        , "email"		      )
			.put("phone"	    , "phone"		  )
			.build();*/

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
		sb.append(" select id,loginName,phone,name,usertype,email from (");
		sb.append(" select su.id,su.login_name as loginName, su.phone as phone,su.name as name,su.email,");
		sb.append(" (case when sr.role='management' then 0 when sr.role='financial_department' then 1 ");
		sb.append(" when sr.role='dispatcher' then 2 when sr.role='operator' then 3 when sr.role='dispatcher' then 2 when sr.role='vehicle_management' then 4 else 5 end) ");
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
		sb.append(") as lo limit :fromPageSize,:pageSize ");
		//fromPageSize=(pageNo-1)*pageSize
		paramMap.put("fromPageSize",(MapUtils.getLong(paramMap,"page")-1)*MapUtils.getLong(paramMap,"pageSize"));
		return super.queryForList(sb.toString(),paramMap);
	}

	@Override
	public int addUser(User user) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into sys_user(name,login_name,password,");
		sb.append(" email,phone,active,active_date) values (:name,");
		sb.append(" :login_name,:password,:email,:phone,0,SYSDATE())");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name",user.getName());
		paramMap.put("login_name",user.getLoginName());
		paramMap.put("password",user.getPassword());
		paramMap.put("email",user.getEmail());
		paramMap.put("phone",user.getPhone());
		return super.update(sb.toString(),paramMap);
	}

	@Override
	public Long qryAllUserCount(Map<String, Object> paramMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		/*Map<String,Object> paramMap = new HashMap<String,Object>();*/
		sb.append(" select count(1) from sys_user su");
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
		return super.queryForObject(sb.toString(),paramMap,Long.class);
	}

	@Override
	public int delUserById(String ids) throws Exception {
		String sql = "delete from sys_user where id in (" + ids + ")";
		return super.update(sql,new HashMap<String,Object>());
	}

	@Override
	public int delUserRolesById(String ids) throws Exception {
		String qsql = "select login_name from sys_user where id in (" + ids + ")";
		List<String> loginMap = super.queryForList(qsql,new HashMap<String, Object>(),String.class);
		if(loginMap != null && loginMap.size() > 0){
			//删除角色
			String loginNames = StringUtils.join(loginMap.toArray(), "','");
			String dsql = "delete from sys_user_role where user_login_name in ('" + loginNames + "')";
			return super.update(dsql,new HashMap<String, Object>());
		}
		return 0;
	}

	@Override
	public boolean checkUserExist(String loginName) throws Exception {
		String sql = "select count(1) from sys_user where login_name=:loginName";
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName",loginName);
		int i = super.queryForInt(sql,paramMap);
		if(i > 0){
			//存在
			return true;
		}
		return false;
	}

	@Override
	public int modifyUser(User user) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" update sys_user set name=:name,login_name=:login_name,password=:password,");
		sb.append(" email=:email,phone=:phone where id=:id");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name",user.getName());
		paramMap.put("login_name",user.getLoginName());
		paramMap.put("password",user.getPassword());
		paramMap.put("email",user.getEmail());
		paramMap.put("phone",user.getPhone());
		paramMap.put("id",Long.valueOf(user.getId()));
		return super.update(sb.toString(),paramMap);
	}
}
