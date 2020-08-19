package com.example.demo.server;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServer {
    /**
        Service层介于controller和dao之间作为服务层进行一些逻辑处理，
       逻辑简单,单纯调用Mapper所以不做注释
     */

     List<User> getAll();

     User getUserById(int id);


     void updeteUser(User user);


     void insertUser(User user);


     void deleteUserById(int id);


}
