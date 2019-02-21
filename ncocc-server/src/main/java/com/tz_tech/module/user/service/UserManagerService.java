package com.tz_tech.module.user.service;

import com.auth0.jwt.interfaces.Claim;
import com.tz_tech.module.common.model.Result;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.common.utils.MD5Utils;
import com.tz_tech.module.common.utils.TokenUtils;
import com.tz_tech.module.user.dao.UserDao;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManagerService {

    @Autowired
    private UserDao userDao;

    /**
     * 登录校验
     * @param user
     * @param request
     * @return
     * @throws Exception
     */
    public Result login(User user, HttpServletRequest request) throws Exception{
        Result loginResult = Result.fail();
        HttpSession session = request.getSession();
        String password = user.getPassword();
        //验证用户名+密码
        Map<String,Object> userMap = userDao.qryUserByUserName(user);
        if(null == userMap)
        {
            loginResult.setResultMsg("用户名不存在!");
            loginResult.setResultCode(1000);
            return loginResult;
        }
        if(MapUtils.getInteger(userMap, "active") != 0)
        {
            loginResult.setResultMsg("用户已失效!");
            loginResult.setResultCode(1001);
            return loginResult;
        }
        /*if(!MD5Utils.md5(password).equals(
                MapUtils.getString(userMap, "password")))
        {
            loginResult.setResultMsg("密码错误!");
            loginResult.setResultCode(1002);
            return loginResult;
        }*/
        if(!password.equals(MapUtils.getString(userMap, "password"))){
            loginResult.setResultMsg("密码错误!");
            loginResult.setResultCode(1002);
            return loginResult;
        }
        session.setAttribute("loginInfo", userMap);
        session.setAttribute("loginName", MapUtils.getString(userMap, "login_name"));
        //生产token给到前台
        user.setName(MapUtils.getString(userMap, "name"));
        String accessToken = TokenUtils.createToken(user);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("access_token",accessToken);
        loginResult = Result.success(data);
        return loginResult;
    }

    /**
     * 通过Token获取当前登录用户的中文名
     * @param request
     * @return
     * @throws Exception
     */
    public Result getUserByToken(HttpServletRequest request) throws Exception{
        Result loginResult = Result.fail();
        Map<String,Object> data = new HashMap<String, Object>();
        String[] Token = request.getHeader("Authorization").split("\\s+");//以空格分隔
        try {
            Map<String, Claim> userInfo = TokenUtils.verifyToken(Token[1]);
            String name = userInfo.get("name").asString();
            data.put("name",name);
            loginResult = Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginResult;
    }

    /**
     *通过自身权限查找这个组下的所有用户
     * (现在只用于管理员)
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Result queryAllUserByAuth(Map<String,Object> paramMap) throws Exception{
        Result result = Result.fail();
        //判断当前用户是否是管理员
       /* boolean isManager = false;
        List<Map<String, Object>> roleList = userDao.
                qryUserRoleByUserName(MapUtils.getString(paramMap,"userName"));*/

        //已用户名分页模糊查询
        List<Map<String, Object>> userList = userDao.qryAllUserByLoginName(paramMap);

        //查询出总量
        Long totalCount = userDao.qryAllUserCount(paramMap);
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("user",userList);
        data.put("totalCount",totalCount);
        result = Result.success(data);
        return result;
    }

    public Result saveUser(User user) throws Exception{
        Result result = Result.fail();
        try {
            if(user != null){
                //校验用户名是否存在
                if(!checkLoginNameExist(user.getLoginName())){
                    int i = userDao.addUser(user);
                    if(i > 0){
                        //成功
                        result = Result.success();
                    }
                }else {
                    result.setResultCode(201);
                    result.setResultMsg("用户名已存在，请重新输入!");
                }
            }
        } catch (Exception e) {
            result.setResultMsg("添加失败!");
        }
        return result;
    }

    public Result deleteUser(List<Long> groupId) throws Exception{
        Result result = Result.fail();
        if(groupId != null && groupId.size() > 0){
            //list转为以逗号分隔的字符串
            try {
                String ids = StringUtils.join(groupId.toArray(), ",");
                userDao.delUserRolesById(ids);
                userDao.delUserById(ids);
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

    public Result modifyUser(User user) throws Exception{
        Result result = Result.fail();
        //校验用户名是否存在
        try {
//            if(!checkLoginNameExist(user.getLoginName())){
                int i = userDao.modifyUser(user);
                if(i > 0){
                    result = Result.success();
                }
//            }else{
//                result.setResultMsg("用户名已存在，请重新输入!");
//            }
        } catch (Exception e) {
            result.setResultMsg("更新失败!");
        }
        return result;
    }

    private boolean checkLoginNameExist(String loginName) throws Exception{
        return userDao.checkUserExist(loginName);
    }
}
