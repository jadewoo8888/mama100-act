package com.biostime.controller;

import com.biostime.constant.Constant;
import com.biostime.controller.base.BaseController;
import com.biostime.domain.mysql.User;
import com.biostime.request.TestLoginReq;
import com.biostime.request.TestUserReq;
import com.biostime.response.TestUserRes;
import com.biostime.response.base.BaseActRes;
import com.biostime.response.base.UserInfoRes;
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
public class TestController extends BaseController {

    @ApiOperation(value="创建用户1", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path")
    @RequestMapping(value = "/test/str/{id}",method = RequestMethod.POST)
    public BaseActRes getStr(@PathVariable Long id){
        BaseActRes<String> res = new BaseActRes<>();

        res.setData(id.toString());
        return res;
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

    @ApiOperation(value="创建用户TestUserReq", notes="根据User对象创建用户")
    @ApiImplicitParam(dataType = "java.lang.Long", name = "id", value = "id", required = true, paramType = "path")
    @RequestMapping(value = "/test/req/{id}",method = RequestMethod.POST)
    public BaseActRes<TestUserRes> save(@PathVariable Long id, @RequestBody TestUserReq userReq){

        BaseActRes<TestUserRes> res = new BaseActRes<>();

        System.out.println("id:"+id+", user:"+userReq);
        //user.setId(id);
        TestUserRes userRes = new TestUserRes();
        userRes.setId(userReq.getId());
        userRes.setAge(userReq.getAge());
        userRes.setName(userReq.getName());
        res.setData(userRes);
        return res;
    }

    @ApiIgnore
    @ApiOperation(value="删除指定id用户", notes="根据id来删除用户信息")
    @ApiImplicitParam(name = "id",value = "用户id", dataType = "java.lang.Long", paramType = "path")
    @RequestMapping(value = "/user/del/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id){
        System.out.println("delete user:"+id);
        return "OK";
    }

    @ApiOperation(value="测试登录", notes="")
    @RequestMapping(value = "/test/login", method = RequestMethod.POST)
    public BaseActRes<String> testLogin(@RequestBody TestLoginReq loginReq) {
        BaseActRes<String> res = new BaseActRes<>();
        UserInfoRes u = getUserInfo(loginReq);
        if(null == u || !Constant.SUCCESS.equals(u.getResponse().getCode())){
            res.setCode(u.getResponse().getCode());
            res.setDesc(u.getResponse().getDesc());
            return res;
        }
        Long customerId = Long.parseLong(u.getResponse().getCustomerId());
        res.setData(customerId+"");
        res.setCode(Constant.SUCCESS);
        res.setSeqNo(loginReq.getSeqNo());
        res.setDesc("测试登录");
        return res;
    }
}
