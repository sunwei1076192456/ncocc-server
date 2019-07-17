package com.tz_tech.module.common.utils;

import com.tz_tech.module.common.dao.CommonHelper;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseInfoLoadFromDB {

    private static final Logger log = Logger.getLogger(BaseInfoLoadFromDB.class);

    //加载所有角色信息
    public static Map<String,Object> roleMap = new HashMap<String, Object>();

    public static Map<String,Object> dispatcherMap = new HashMap<String, Object>();

    public void init()throws Exception{
        List<Map<String,Object>> role = CommonHelper.getCommonDao().queryForList("select id,role,name,modules from sys_role",new HashMap<String,Object>());
        if(null != role){
            roleMap.put("role",role);
            log.info("=====角色信息初始化完成=====");
        }
        List<Map<String,Object>> dispatcher = CommonHelper.getCommonDao().queryForList("select su.login_name as executor_id,su.name as executor_name,sr.role as executor_type " +
                "from sys_user su left join sys_user_role sur on sur.user_login_name=su.login_name left join " +
                "sys_role sr on sr.id=sur.role_id where sr.id=2;",new HashMap<>());
        if(null != dispatcher){
            dispatcherMap.put("dispatcher",dispatcher);
            log.info("=====调度员信息初始化完成=====");
        }
    }
}
