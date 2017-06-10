package com.biostime.controller;

import com.biostime.domain.test1.User;
import com.biostime.service.test1.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 13006 on 2017/6/9.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    //http://127.0.0.1:8080/user?id=1
    @RequestMapping("/user")
    public User query(Long id) {
        return userService.findOne(id);
    }

    @RequestMapping("/total")
    public Integer totalRecord() {
        return userService.totalCount();
    }

    @RequestMapping(name = "/all")
    public List<User> getList() {
        return userService.findAll();
    }
}
