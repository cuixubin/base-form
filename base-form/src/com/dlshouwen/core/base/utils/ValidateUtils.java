package com.dlshouwen.core.base.utils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 验证工具类
 * @author 大连首闻科技有限公司
 * @version 2015-12-2 14:00:20
 */
public class ValidateUtils {

	/** 单例验证对象 */
	private static Validator validator;

	/**
	 * 验证对象
	 * <p>该方法可以进行对象做服务器验证，采用Hibernate Validate验证方案
	 * @param t 需要验证的泛型对象
	 * @return 验证结果
	 * @throws Exception 抛出全部异常
	 */
	public static <T> Set<ConstraintViolation<T>> validate(T t) throws Exception {
		LocalValidatorFactoryBean validatorFactory = 
				(LocalValidatorFactoryBean) SpringUtils.getBean("validatorFactory");
		if (validator == null)
			validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		return constraintViolations;
	}
	
}