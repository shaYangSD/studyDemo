package com.example.demo.filter;

import com.example.demo.entity.JwtUser;
import com.example.demo.exception.TokenIsExpiredException;
import com.example.demo.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * 鉴权功能
 * */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    //在doFilter方法中，doFilterInternal方法由子类实现，主要作用是规定过滤的具体方法。
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JWTUtil.TOKEN_HEADER);

        //检查如果请求头中没有Authorization就放行
        if(tokenHeader==null|| !tokenHeader.startsWith(JWTUtil.TOKEN_PREFIX)){//拿到header从bearer后开始
            chain.doFilter(request,response);
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息setAuthentication
        //SecurityContextHolder 存储当前的认证信息
        try {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String reason = "统一处理，原因：" + e.getMessage();
            response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
            response.getWriter().flush();
            return;
        }
        super.doFilterInternal(request, response, chain);// 放弃权限给下一个
    }

    private Authentication getAuthentication(String tokenHeader) throws TokenIsExpiredException {
            String token=tokenHeader.replace(JWTUtil.TOKEN_PREFIX,"");//通过把bearer替换成空，提出token
            boolean expiration = JWTUtil.isExpiration(token);//判断是否过时
            if(expiration){
                throw new TokenIsExpiredException("token超时了");
            }else {
                String username = JWTUtil.getUsername(token);//拿出用户名
                String role = JWTUtil.getRole(token);
                if(username!=null){
                    return new UsernamePasswordAuthenticationToken(username,null, Collections.singleton(new SimpleGrantedAuthority(role)));
                }
            }
    return null;
    }
}
