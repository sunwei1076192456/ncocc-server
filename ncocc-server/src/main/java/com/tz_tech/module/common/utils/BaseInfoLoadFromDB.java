package com.tz_tech.module.common.utils;

import com.tz_tech.module.common.dao.CommonHelper;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;

import java.util.*;

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
        //初始化省市区县信息
        List<Map<String,Object>> districtInfo = initDistrict();
        if(districtInfo != null && districtInfo.size() != 0){
            dispatcherMap.put("district",districtInfo);
            log.info("=====省市区县信息初始化完成=====");
        }
    }

    public List<Map<String,Object>> initDistrict() throws Exception{
        List<Map<String,Object>> allDistrictInfo = CommonHelper.getCommonDao().queryForList("select id as value,name as label,parent_id,pinyin,sort from dict_district",new HashMap<>());
        //根节点
        List<Map<String,Object>> rootDistrictList = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> district : allDistrictInfo){
            if("0".equals(MapUtils.getString(district,"parent_id"))){
                rootDistrictList.add(district);
            }
        }
        Collections.sort(rootDistrictList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if(!MapUtils.getString(o1,"sort","").equals(MapUtils.getString(o2,"sort",""))){
                    return MapUtils.getInteger(o1,"sort",0) - MapUtils.getInteger(o2,"sort",0);//升序
                }
                return 0;
            }
        });
        for(Map<String,Object> root : rootDistrictList){
            List<Map<String,Object>> childrenDistrictInfo = getChildren(MapUtils.getInteger(root,"value"),allDistrictInfo);
            root.put("children",childrenDistrictInfo);
        }
        return rootDistrictList;
    }

    /**
     * 获取子节点
     * @param id
     * @param allDistrictInfo
     * @return
     * @throws Exception
     */
    private List<Map<String,Object>> getChildren(int id,List<Map<String,Object>> allDistrictInfo)throws Exception{
        List<Map<String,Object>> childDistrictList = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> district : allDistrictInfo){
            if(MapUtils.getInteger(district,"parent_id",0) == id){
                childDistrictList.add(district);
            }
        }
        //递归
        for(Map<String,Object> children : childDistrictList){
            children.put("children",getChildren(MapUtils.getInteger(children,"value"),allDistrictInfo));
        }
        Collections.sort(childDistrictList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if(!MapUtils.getString(o1,"sort","").equals(MapUtils.getString(o2,"sort",""))){
                    return MapUtils.getInteger(o1,"sort",0) - MapUtils.getInteger(o2,"sort",0);//升序
                }
                return 0;
            }
        });
        if(childDistrictList.size() == 0){
            return new ArrayList<Map<String,Object>>();
        }
        return childDistrictList;
    }
}
