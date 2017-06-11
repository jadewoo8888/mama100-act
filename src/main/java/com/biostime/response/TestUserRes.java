package com.biostime.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 13006 on 2017/6/11.
 */
public class TestUserRes {
    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "用户年龄")
    private Long age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
