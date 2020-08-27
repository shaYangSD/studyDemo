package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api",method = RequestMethod.POST)
public class ResgisterController {
    @Autowired
    private UserRepository userRepository;//为crud操作用户名

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;//对密码进行加密

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));//得到用户名并设置
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));//得到密码加密并设置
        user.setRole("ROLE_USER");
        user.setAge("18");
        User save = userRepository.save(user);
        return save.toString();
    }
}
