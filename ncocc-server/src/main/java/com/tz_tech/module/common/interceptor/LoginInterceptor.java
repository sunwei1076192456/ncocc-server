package com.tz_tech.module.common.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.tz_tech.module.common.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
            return true;
        }
        Map<String, Claim> userInfo = null;
        if("".equals(request.getHeader("Authorization")) || null == request.getHeader("Authorization")){
            response.sendError(401);
            return false;
        }
        String[] Token = request.getHeader("Authorization").split("\\s+");//以空格分隔
        try {
            userInfo = TokenUtils.verifyToken(Token[1]);
        } catch (Exception e) {
            response.sendError(401);
            return false;
        }
        if(userInfo == null){
            response.sendError(401);
            return false;
        }else
            return true;
    }

    /**
     * 是否进行登陆过滤
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path,String basePath)
    {
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<String>();
        //设置不进行登录拦截的路径：登录注册和验证码
        notLoginPaths.add("/userManger/login.do");
        notLoginPaths.add("/BillService/saveBill.do");
        if(notLoginPaths.contains(path)) return false;
        return true;
    }

}
