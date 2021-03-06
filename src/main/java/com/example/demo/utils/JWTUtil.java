package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;

public class JWTUtil {
    public static final String TOKEN_HEADER     = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "rwj";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    //添加角色key（重要）
    private static final String ROLE_CLAIMS = "password";

    //token生成
    public static String createToken(String username,String password,boolean isRemamberMe){
        long expiration = isRemamberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,password);
        return Jwts.builder()//是token
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration*1000))
                .compact();
    }

    //获得token主体
    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        }


        //检查token
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    //从token中获取密码

    public static String getRole(String token){
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    //是否过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }



}
