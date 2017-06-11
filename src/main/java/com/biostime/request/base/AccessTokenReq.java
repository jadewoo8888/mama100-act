package com.biostime.request.base;

import io.swagger.annotations.ApiModelProperty;

public class AccessTokenReq extends BaseReq {
    @ApiModelProperty(value = "设备ID")
    private String devid = null;
    @ApiModelProperty(value = "用于访问用户网络资源的token")
    private String accessToken = null;

    @ApiModelProperty(value = "authentication data，由客户端生成，每次访问用户的网络资源时，url需要带上此字段，字段值为：device id + seq no 后再des work key加密。 此字段用于服务端检验访问用户资源的http请求的合法性")
    private String authData = null;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthData() {
        return authData;
    }

    public void setAuthData(String authData) {
        this.authData = authData;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

}
