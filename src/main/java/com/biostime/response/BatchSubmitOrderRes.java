package com.biostime.response;

import com.biostime.constant.Constant;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 13006 on 2017/6/19.
 */
public class BatchSubmitOrderRes {

    @ApiModelProperty(value = "交易号")
    private String tradeNo;
    @ApiModelProperty(value = "在线支付订单数")
    private String onlinePayOrdersCount = Constant.ONE;
    @ApiModelProperty(value = "在线支付总额")
    private String onlinePayAmount;
    @ApiModelProperty(value = "支付方式")
    private String payStyleName = "微信支付";

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOnlinePayOrdersCount() {
        return onlinePayOrdersCount;
    }

    public void setOnlinePayOrdersCount(String onlinePayOrdersCount) {
        this.onlinePayOrdersCount = onlinePayOrdersCount;
    }

    public String getOnlinePayAmount() {
        return onlinePayAmount;
    }

    public void setOnlinePayAmount(String onlinePayAmount) {
        this.onlinePayAmount = onlinePayAmount;
    }

    public String getPayStyleName() {
        return payStyleName;
    }

    public void setPayStyleName(String payStyleName) {
        this.payStyleName = payStyleName;
    }
}
