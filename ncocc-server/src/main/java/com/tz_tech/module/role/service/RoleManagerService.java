package com.tz_tech.module.role.service;

import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.Role;
import com.tz_tech.module.common.model.UserRoleDto;
import com.tz_tech.module.role.dao.RoleDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<Map<String,Object>> roleList = roleDao.qryRolesByLoginName(loginName);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("userRole",roleList);
        result = Result.success(data);
        return result;
    }

    public Result rolesConfigure(List<UserRoleDto> userRoleDto) throws Exception{
        Result result = Result.fail();
        try {
            //先清除之前的角色
            roleDao.clearRolesForUser(userRoleDto.get(0).getUserName());
            for(UserRoleDto temp : userRoleDto){
                roleDao.addRoleForUserName(temp);
            }
            result = Result.success();
        } catch (Exception e) {
            result.setResultMsg("配置失败!");
        }
        return result;
    }

    public Result getAllRolesForPage(Map<String,Object> paramMap)throws Exception{
        Result result = Result.fail();
        //分页查询
        List<Map<String, Object>> roleList = roleDao.qryAllRolesByPage(paramMap);
        //查出总的角色数量
        Long totalCount = roleDao.qryAllRolesCount();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("role",roleList);
        data.put("totalCount",totalCount);
        result = Result.success(data);
        return result;
    }

    public Result saveRole(Role role)throws Exception{
        Result result = Result.fail();
        try {
            if(role != null){
                //校验role是否重复
                if(!checkRoleExist(role.getRole())){
                    int i = roleDao.addRole(role);
                    if(i > 0){
                        //成功
                        result = Result.success();
                    }
                }else {
                    result.setResultCode(201);
                    result.setResultMsg("角色已存在，请重新输入!");
                }
            }
        } catch (Exception e) {
            result.setResultMsg("添加失败!");
        }
        return result;
    }

    private boolean checkRoleExist(String role)throws Exception{
        return roleDao.checkRoleExist(role);
    }

    public Result deleteRole(List<Long> groupId)throws Exception{
        Result result = Result.fail();
        if(groupId != null && groupId.size() > 0){
            //list转为以逗号分隔的字符串
            try {
                String ids = StringUtils.join(groupId.toArray(), ",");
                //先删用户-角色关联表的数据
                roleDao.delRoleUserById(ids);
                //再删角色-权限关联表的数据
                roleDao.delRoleMenuById(ids);
                //最后删角色
                roleDao.delRoleById(ids);
                result = Result.success();
            } catch (Exception e) {
                e.printStackTrace();
                result.setResultMsg("删除失败!");
            }
        }else {
            result.setResultMsg("请至少勾选一条记录删除!");
        }
        return result;
    }

    public Result modifyRole(Role role)throws Exception{
        Result result = Result.fail();
        try {
            int i = roleDao.modifyRole(role);
            if(i > 0){
                result = Result.success();
            }
        } catch (Exception e) {
            result.setResultMsg("更新失败!");
        }
        return result;
    }

    public Result menuConfigure(Role role)throws Exception{
        Result result = Result.fail();
        try {
            Set<Integer> Fmenu = new HashSet<Integer>();
            String modules = role.getModules();//权限菜单,仅仅只有二级菜单
            if(modules != null && modules.endsWith(";")){
                modules = modules.substring(0,modules.length()-1);
                role.setModules(modules);
                String menu[] = modules.split(";");
                for(int i=0;i<menu.length;i++){
                    int parentId = roleDao.qryParentMenuById(Long.valueOf(menu[i]));
                    Fmenu.add(parentId);
                }
                modules = StringUtils.join(Fmenu.toArray(),";") + ";" + modules ;
            }
            //更新角色表的modules(权限)字段
            roleDao.updateRoleMenu(role);

            role.setModules(modules);

            //清空之前这个角色的权限
            roleDao.clearMenusForRole(role);


            //插入角色-权限关系表数据(sys_role_menu)--一个菜单一条数据
            roleDao.addMenusForRole(role);

            result = Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultMsg("配置权限失败!");
        }
        return result;
    }

}
