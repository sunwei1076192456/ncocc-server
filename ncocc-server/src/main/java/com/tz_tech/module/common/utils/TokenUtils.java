package com.tz_tech.module.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tz_tech.module.common.model.User;

public class TokenUtils {
    //key
    public static String SECRET = "SDFEEdfdeFDRE";

    public static String createToken(User user) throws Exception{
        //当前签发时间
        Date signTime = new Date();

        //设置过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 1);
        Date expiresDate = nowTime.getTime();

        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");

        //生产Token
        String token = JWT.create()
                .withHeader(headerMap)
                .withClaim("login_name", user.getLoginName())
                .withClaim("name",user.getName())
                .withExpiresAt(expiresDate)
                .withIssuedAt(signTime)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    public static Map<String, Claim> verifyToken(String token) throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("凭证过期！");
        }

        return jwt.getClaims();
    }
}
