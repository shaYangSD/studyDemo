package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.server.UserServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "api")
public class UserController {

    @Resource
    private UserServer userServer;


    @RequestMapping(value = "findall",method = RequestMethod.GET)
    public Map<String,Object> findAll(){
        Map<String,Object> map = new HashMap<>();
        try {
            map.put("all users",userServer.getAll());
            map.put("msg","查询成功");
        } catch (Exception e) {
            map.put("msg","查询失败");
        }
        return map;
    }

    @ApiOperation("根据ID查找")
    @RequestMapping(value = "finduserbyid",method = RequestMethod.GET)
    public Map<String ,Object> findUserById(@RequestParam(value ="id",required = true) int id){
        Map<String ,Object> map = new HashMap<>();
        try {
            map.put("msg","查询成功");
            map.put("user",userServer.getUserById(id));
        } catch (Exception e) {
            map.put("msg","查询失败");
        }
        return map;
    }

    @ApiOperation("根据ID删除")
    @RequestMapping(value = "deleteuser",method = RequestMethod.DELETE)
    public  Map<String ,Object> deleteUser(@RequestParam(value = "id",required = true) int id){
        Map<String,Object> map = new HashMap<>();
        try {
            userServer.deleteUserById(id);
            map.put("msg","删除成功");
        } catch (Exception e) {
            map.put("msg","删除失败");
        }
        return map;
    }

    @ApiOperation("添加新用户")
    @RequestMapping(value = "adduser",method = RequestMethod.POST)//当一个方法想要返回不止一个参数时，可以定义Map类型做返回类型
    public Map<String,Object> insertUser(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
            try {
                userServer.insertUser(user);
                map.put("user",user);
                map.put("msg","新增成功");
            }catch (Exception e){
                map.put("msg","新增失败");
            }
            return map;
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "updateuser",method = RequestMethod.PUT)
    public Map<String,Object> updateuser(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
        try {
            userServer.updeteUser(user);
            map.put("user",user);
            map.put("msg","更新成功");
        } catch (Exception e) {
            map.put("msg","更新失败");
        }
        return map;
    }


}
