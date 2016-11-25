package com.dlshouwen.core.system.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户参数
 * @author 大连首闻科技有限公司
 * @since 2016-05-06 09:26:49
 */
public class UserAttr {

	private String user_id;

	@NotBlank(message="皮肤信息不能为空")
	@Length(min=0, max=40, message="皮肤信息长度必须在0-40之间")
	private String skin_info;

	@NotBlank(message="是否显示快捷菜单不能为空")
	@Length(min=0, max=2, message="是否显示快捷菜单长度必须在0-2之间")
	private String is_show_shortcut;
	
	@NotBlank(message="是否背景漂浮不能为空")
	@Length(min=0, max=2, message="是否背景漂浮长度必须在0-2之间")
	private String is_background_float;
	
	@NotNull(message="背景漂浮速度不能为空")
	private int background_float_speed;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSkin_info() {
		return skin_info;
	}

	public void setSkin_info(String skin_info) {
		this.skin_info = skin_info;
	}

	public String getIs_show_shortcut() {
		return is_show_shortcut;
	}

	public void setIs_show_shortcut(String is_show_shortcut) {
		this.is_show_shortcut = is_show_shortcut;
	}

	public String getIs_background_float() {
		return is_background_float;
	}

	public void setIs_background_float(String is_background_float) {
		this.is_background_float = is_background_float;
	}

	public int getBackground_float_speed() {
		return background_float_speed;
	}

	public void setBackground_float_speed(int background_float_speed) {
		this.background_float_speed = background_float_speed;
	}

}