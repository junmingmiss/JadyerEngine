package com.jadyer.engine.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;

/**
 * JSR303验证工具
 * @see -------------------------------------------------------------------------------------------
 * @see 本工具需要借助以下三个jar
 * @see hibernate-validator-5.1.3.Final.jar
 * @see validation-api-1.1.0.Final.jar
 * @see jboss-logging-3.1.3.GA.jar
 * @see 其实也可以像我博客写的这样http://blog.csdn.net/jadyer/article/details/7574668
 * @see 采用org.springframework.validation.BindingResult来验证,只是个人觉得单独一个工具类更好些
 * @see -------------------------------------------------------------------------------------------
 * @see 以下为Bean Validation规范内嵌的约束注解定义
 * @see @Null        限制只能为null
 * @see @NotNull     限制必须不为null
 * @see @AssertTrue  限制必须为true
 * @see @AssertFalse 限制必须为false
 * @see @Min         限制必须为一个不小于指定值的数字
 * @see @Max         限制必须为一个不大于指定值的数字
 * @see @DecimalMin  限制必须为一个不小于指定值的数字
 * @see @DecimalMax  限制必须为一个不大于指定值的数字
 * @see @Size        限制字符长度必须在min到max之间
 * @see @Digits      限制必须为一个小数,且整数部分的位数不能超过integer,小数部分的位数不能超过fraction
 * @see @Past        限制必须是一个过去的日期
 * @see @Future      限制必须是一个将来的日期
 * @see @Pattern     限制必须符合指定的正则表达式
 * @see -------------------------------------------------------------------------------------------
 * @see 除了以上列出的JSR-303原生支持的限制类型之外,还可以定义自己的限制类型
 * @see 本工具类最下方的注释部分是一个例子
 * @see 另外也可参考文章http://haohaoxuexi.iteye.com/blog/1812584
 * @see -------------------------------------------------------------------------------------------
 * @version v1.0
 * @history v1.0-->新建
 * @create 2015-6-9 下午11:25:18
 * @create 2015-6-9 下午11:25:18
 * @author 玄玉<http://blog.csdn.net/jadyer>
 */
public final class ValidatorUtil {
	private static Validator validator = null;

	static{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private ValidatorUtil() {}


	/**
	 * 验证对象中的属性的值是否符合注解定义
	 * @param obj 需要验证的对象
	 * @return 返回空字符串""表示验证通过,否则返回错误信息,多个字段的错误信息用英文分号[;]分隔
	 */
	public static String validate(Object obj){
		return validate(obj, new String[]{});
	}


	/**
	 * 验证对象中的属性的值是否符合注解定义
	 * @param obj             需要验证的对象
	 * @param exceptFieldName 不需要验证的属性
	 * @return 返回空字符串""表示验证通过,否则返回错误信息,多个字段的错误信息用英文分号[;]分隔
	 */
	public static String validate(Object obj, String... exceptFieldName){
		String validateMsg = "";
		if(null == obj){
			return "被验证对象不能为null";
		}
		Set<ConstraintViolation<Object>> validateSet = validator.validate(obj);
		for(ConstraintViolation<Object> constraintViolation : validateSet){
			String field = constraintViolation.getPropertyPath().toString();
			String message = constraintViolation.getMessage();
			if(!isExcept(field, exceptFieldName)){
				//id:最小不能小于1;name:不能为空;
				validateMsg += field + ":" + message + ";";
			}
		}
		return validateMsg;
	}


	/**
	 * 判断属性是否属于例外属性列表
	 * @return true--是例外属性,false--不是例外属性
	 */
	private static boolean isExcept(String field, String... exceptFieldName){
		for(String obj : exceptFieldName){
			if(StringUtils.isNotBlank(obj) && field.indexOf(obj)>=0){
				return true;
			}
		}
		return false;
	}
}
/*
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy=ByteLengthValidate.class)  
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})  
public @interface ByteLength {
	int min() default 0;

	int max() default Integer.MAX_VALUE;

	String message() default "{org.hibernate.validator.constraints.Length.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
*/
/*
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ByteLengthValidate implements ConstraintValidator<ByteLength, String> {
	private int min;
	private int max;

	@Override
	public void initialize(ByteLength byteLength) {
		this.min = byteLength.min();
		this.max = byteLength.max();
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext arg1) {
		String standard = input.replaceAll("[^\\x00-\\xff]", "**");
		int length = standard.length();
		if(min>0 && length<min){
			return false;
		}
		if(max>0 && length>max){
			return false;
		}
		return true;
	}
}
*/