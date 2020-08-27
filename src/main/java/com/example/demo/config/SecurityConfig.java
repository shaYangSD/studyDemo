package com.example.demo.config;

import com.example.demo.exception.JWTAccessDeniedHandler;
import com.example.demo.exception.JWTAuthenticationEntryPoint;
import com.example.demo.filter.JWTAuthenticationFilter;
import com.example.demo.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier( "userDetailsServiceImpl")    //通过这个标示，表明了哪个实现类才是我们所需要的
    private UserDetailsService userDetailsService;//用userDetailsService的loaduserbyusername方法来获取用户信息

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}//密码加密

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {//PasswordEncoder接口使用BCrypt强哈希方法来加密密码。
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    //默认配置 是拦截所有的,不采用默认配置则重写
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()

                //通过authorizeRequests()方法来开始请求权限配置。针对应用程序中的每个URL进行了身份验证。通过向http.authorizeRequests（）方法添加多个子项来指定URL的自定义要求
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")//只有ROLE_ADMIN才能删除
                .anyRequest().permitAll()
                .and()

                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//用JWT托管安全信息，所以禁掉session
                .and()

                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(new JWTAccessDeniedHandler());      //添加无权限时的处理
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
