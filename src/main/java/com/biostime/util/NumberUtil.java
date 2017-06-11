package com.biostime.util;

import com.biostime.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
	private static DecimalFormat df = new DecimalFormat("#########0.00");
	
	/**
	 * 获取指定位数的随机数
	 * @param size int 位数
	 * @return 指定位数的随机数 long
	 */
	public static long getRandom(int size) {
		Double value = (Math.random() * Math.pow(10, size));
		return value.longValue();
	}
	
	public static String genSeqNo(){
		String seqNo = format.format(new Date()) + getRandom(4);
		return seqNo;
	}
	
	public static String genSeqNo(String code){
		return (StringUtils.isNotBlank(code) ? code : Constant.EMPTY) + format.format(new Date()) + getRandom(4);
	}
	
	public static String formatFloat(float value){
		return df.format(value);
	}
	
	public static String formatDouble(double value){
		return df.format(value);
	}
}
