package com.biostime.request;

import com.biostime.request.base.AccessTokenReq;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 13006 on 2017/6/12.
 */
public class TestLoginReq extends AccessTokenReq {

    @ApiModelProperty(value = "用户id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
