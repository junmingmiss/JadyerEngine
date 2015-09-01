package com.jadyer.engine.common.util.tmp.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTypeCurrency {
	/**
	 * 数值格式的小数位数
	 * @see 默认值为2
	 */
	public int decimalDigits() default 2;

	/**
	 * 货币符号
	 * @see 默认为[￥]
	 */
	public String currencySymbol() default "￥";
}