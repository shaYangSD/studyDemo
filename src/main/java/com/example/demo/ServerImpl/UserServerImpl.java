package com.example.demo.ServerImpl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.server.UserServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * 实现类是将controller与service结合
 * **/

@Service
public class UserServerImpl implements UserServer {
    @Resource
    UserMapper mapper;

    @Override
    public List<User> getAll() {
        return mapper.getAll();
    }

    @Override
    public User getUserById(int id) {
        return mapper.getUserById( id);
    }

    @Override
    public void updeteUser(User user) {
        mapper.updateUser(user);
    }

    @Override
    public void insertUser(User user) {
        mapper.insertUser(user);
    }

    @Override
    public void deleteUserById(int id) {
        mapper.deleteUserById(id);
    }




}
