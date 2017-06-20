package com.biostime.helper;

import java.math.BigDecimal;


/**
 * 
 * @author kafua.zhou
 */
public class ProductHelper {

	/** 常量  */
	private final static double constant_value = 0.7d;
	
	/**
	 * 计算跨境商品的税费，不包含原价格，不判断是否免税
	 * @param price 商品价格
	 * @param customsTaxRate 关税税率
	 * @param consumptionTax 消费税税率
	 * @param valueAddedTax 增值税税率
	 * @return
	 */
	public static double taxCost(double price,double customsTaxRate,double consumptionTax,double valueAddedTax ){
		if(customsTaxRate < 0 || consumptionTax <0 || valueAddedTax<0){
			return 0D;
		}
		//关税=（单价）*关税税率
		double customsTax = price * customsTaxRate;//
		//消费税  = (单价/(1-消费税税率))*消费税税率
		double costTax = ( price/(1-consumptionTax) ) * consumptionTax;
		//增值税 = （单价 + 消费税）*增值税税率
		double valueTax = (price + costTax) * valueAddedTax;
		//最后综合税 = （关税+消费税+增值税）* 0.7 
		double taxCost = (customsTax + costTax + valueTax ) * constant_value;
		BigDecimal b = new BigDecimal(taxCost);  
		double p= b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		return p;
	} 
	
}
