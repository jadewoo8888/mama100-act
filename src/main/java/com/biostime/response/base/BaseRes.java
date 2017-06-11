package com.biostime.response.base;

import com.biostime.constant.Constant;
import io.swagger.annotations.ApiModelProperty;

public class BaseRes {
	//序列号
	@ApiModelProperty(value = "请求序列号")
    private String seqNo;
    //返回编码
	@ApiModelProperty(value = "100代表成功，其它为错误")
    private String code = Constant.SUCCESS;
    //返回描述
	@ApiModelProperty(value = "返回描述")
    private String desc = "成功";
    
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
