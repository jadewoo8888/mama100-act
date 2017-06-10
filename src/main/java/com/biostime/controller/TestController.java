package com.biostime.controller;

import com.biostime.domain.test1.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by 13006 on 2017/6/10.
 */
@RestController
@RequestMapping("act")
//@Api(value = "测试类",tags = "测试接口")
public class TestController {

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(dataType = "java.lang.Long", name = "id", value = "id", required = true, paramType = "path")
    @RequestMapping(value = "/test/str/{id}",method = RequestMethod.POST)
    public String getStr(@PathVariable Long id){


        return "aa";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "java.lang.Long")
    @RequestMapping(value="/test/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable Long id) {
        return "bb";
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.Long", name = "id", value = "id", required = true, paramType = "path"),
            @ApiImplicitParam(dataType = "User", name = "user", value = "用户信息", required = true)
    })
    @RequestMapping(value = "/test/{id}",method = RequestMethod.POST)
    public User insert(@PathVariable Long id, @RequestBody User user){

        System.out.println("id:"+id+", user:"+user);
        user.setId(id);

        return user;
    }



    @ApiIgnore
    @ApiOperation(value="删除指定id用户", notes="根据id来删除用户信息")
    @ApiImplicitParam(name = "id",value = "用户id", dataType = "java.lang.Long", paramType = "path")
    @RequestMapping(value = "/user/del/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id){
        System.out.println("delete user:"+id);
        return "OK";
    }
}
