package com.tz_tech.module.role.service;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.role.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleManagerService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 查询所有角色
     * @return
     * @throws Exception
     */
    public Result getAllRoles() throws Exception{
        Result result = Result.fail();
        List<Map<String,Object>> roleList = roleDao.qryAllRoles();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("role",roleList);
        result = Result.success(data);
        return result;
    }

    public Result getAllRolesByLoginName(String loginName) throws Exception{
        Result result = Result.fail();
        List<Map<String,Object>> roleList = roleDao.qryAllRoles();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("userRole",roleList);
        result = Result.success(data);
        return result;
    }


}
