package com.biostime.response;

import com.biostime.constant.Constant;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 13006 on 2017/6/15.
 */
public class TermSkuInfoRes {

    @ApiModelProperty(value = "门店编码")
    private String terminalCode = Constant.EMPTY;

    @ApiModelProperty(value = "商品spu")
    private String spu = Constant.EMPTY;

    @ApiModelProperty(value = "商品sku")
    private String sku = Constant.EMPTY;

    @ApiModelProperty(value = "商品名称")
    private String productName = Constant.EMPTY;

    @ApiModelProperty(value = "优惠分值")
    private long exchangePoint = 0;

    @ApiModelProperty(value = "商品图片URL")
    private String imgUrl = Constant.EMPTY;
    //剩余库存=库存-冻结库存。假如当前库存10，冻结库存0， 下单买一个商品，冻结库存+1,发货冻结库存-1，库存-1，最后库存9，冻结库存0
    @ApiModelProperty(value = "商品库存")
    private double stock = 0;

    @ApiModelProperty(value = "商品广告词")
    private String adDesc = Constant.EMPTY;

    @ApiModelProperty(value = "优惠原分值")
    private long oldExchangePoint = 0;

    @ApiModelProperty(value = "优惠价")
    private double price = 0.00;

    @ApiModelProperty(value = "建议零售价（原价）")
    private double suggestPrice = 0.00;

    @ApiModelProperty(value = "排序，仅后端使用")
    private int sortNum = 0;

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(long exchangePoint) {
        this.exchangePoint = exchangePoint;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public String getAdDesc() {
        return adDesc;
    }

    public void setAdDesc(String adDesc) {
        this.adDesc = adDesc;
    }

    public long getOldExchangePoint() {
        return oldExchangePoint;
    }

    public void setOldExchangePoint(long oldExchangePoint) {
        this.oldExchangePoint = oldExchangePoint;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(double suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}
