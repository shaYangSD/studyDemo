package com.example.demo.ServerImpl;

import com.example.demo.entity.JwtUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//使用springSecurity需要实现UserDetailsService接口供权限框架使用
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    //主要工作就是重写这个方法
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user=userRepository.findUserName(s);
        return new JwtUser(user);
    }
}
