package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

//根据用户获取用户名
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserName(String username);
}
