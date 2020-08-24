package com.example.demo.config;

import com.example.demo.filter.JWTFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Filter;

public class ShiroConfig {
    /**
     * 先经过token过滤器，如果检测到请求头存在 token，则用 token 去 login，接着走 Realm 去验证
     * */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        //添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap=new LinkedHashMap<>();//??????
        //设置自定义的jwt过滤器
        filterMap.put("jwt",new JWTFilter() );


    }

}
