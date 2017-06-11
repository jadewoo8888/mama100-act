package com.biostime.request.base;

import com.biostime.util.NumberUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

public class BaseReq {
	@ApiModelProperty(value = "请求序列号")
	private String seqNo;
    //来源系统
	@ApiModelProperty(value = "请求来源系统，比如妈妈100app、营销通")
    private String fromSystem;
    //来源业务
	@ApiModelProperty(value = "请求来源业务，比如妈妈100app下面的合家商城，营销通下面的推商品，因为某个业务可能同时在多个系统上存在，因此系统和业务区分开来")
    private String fromBiz;
	public String getSeqNo() {
		return StringUtils.isNotBlank(seqNo) ? seqNo : NumberUtil.genSeqNo();
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getFromSystem() {
		return fromSystem;
	}
	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem;
	}
	public String getFromBiz() {
		return fromBiz;
	}
	public void setFromBiz(String fromBiz) {
		this.fromBiz = fromBiz;
	}
}
