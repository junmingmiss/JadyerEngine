package com.jadyer.engine.common.util.tmp.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTypeNumber {
	/**
	 * 数值格式的小数位数
	 * @see 默认值为2
	 */
	public int decimalDigits() default 2;
	
	/**
	 * 是否使用千分位分隔符
	 * @see 默认为false,即不使用
	 */
	public boolean thousandSeparator() default false;
}