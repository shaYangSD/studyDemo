package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 映射sql文件
 * */

@Mapper
public interface UserMapper {
    //查询所有
    List<User> getAll();

    User getUserById(int id);

    void deleteUserById(int id);

    void updateUser(User user);

    void insertUser(User user);

}
