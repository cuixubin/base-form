package com.dlshouwen.core.base.model;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.dlshouwen.core.base.utils.ValidateUtils;

/**
 * 框架反馈信息对象
 * @author 大连首闻科技有限公司
 * @version 2013-7-19 10:25:56
 */
public class AjaxResponse {
	
	/** 是否成功 */
	private boolean success = false;
	
	/** 成功提示信息 */
	private String successMessage;
	
	/** 是否警告 */
	private boolean warning = false;
	
	/** 警告提示信息 */
	private String warningMessage;
	
	/** 是否错误 */
	private boolean error = false;
	/** 错误提示信息 */
	private String errorMessage;
	
	/** 需要传递的数据 */
	private Object data;
        
        /**
         * 附加信息
         */
        private Object extParam;
        
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

        public Object getExtParam() {
            return extParam;
        }

        public void setExtParam(Object extParam) {
            this.extParam = extParam;
        }
	/**
	 * 绑定spring mvc提供的验证结果
	 * @param bindingResult 绑定的结果
	 * @param warningCode 警告代码
	 */
	public void bindingResultHandler(BindingResult bindingResult){
		this.setWarning(true);
		StringBuffer errors = new StringBuffer();
		errors.append("验证出现错误：<br /><br />");
		int sequence = 0;
		for(FieldError fieldError : bindingResult.getFieldErrors()){
			sequence++;
			errors.append("　　").append(sequence).append(". ").append(fieldError.getDefaultMessage()).append("<br />");
		}
		this.setWarningMessage(errors.toString());
	}
	

	/**
	 * 获取spring mvc提供的验证结果
	 * @param bindingResult 绑定的结果
	 * @return 错误提示信息
	 */
	public static String getBindingResultMessage(BindingResult bindingResult){
		StringBuffer errors = new StringBuffer();
		int sequence = 0;
		for(FieldError fieldError : bindingResult.getFieldErrors()){
			sequence++;
			errors.append("").append(sequence).append(". ").append(fieldError.getDefaultMessage()).append("；");
		}
		return errors.toString();
	}
	
	/**
	 * 手动验证类的方法
	 * @param t 需要验证的对象
	 * @return 对象验证结果
	 */
	public <T> boolean validatorClass(T t) throws Exception {
		Set<ConstraintViolation<T>> constraintViolations = ValidateUtils.validate(t);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				this.setError(true);
				this.setErrorMessage(constraintViolation.getMessage());
				return false;
			}
		}
		return true;
	}
	
}
