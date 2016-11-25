package com.dlshouwen.core.announcement.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 公告
 * @author 大连首闻科技有限公司
 * @version 2013-08-17 12:13:00
 */
public class Announcement {

	private String announcement_id;

	@NotBlank(message="公告标题不能为空")
	@Length(min=0, max=200, message="公告标题长度必须在0-200之间")
	private String title;

	private String content;

	@NotBlank(message="公告状态不能为空")
	@Length(min=0, max=2, message="公告状态长度必须在0-2之间")
	private String status;

	@Length(min=0, max=80, message="创建人长度必须在0-80之间")
	private String creator;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;
	
	@Length(min=0, max=40, message="编辑人长度必须在0-40之间")
	private String editor;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date edit_time;

	public String getAnnouncement_id() {
		return announcement_id;
	}

	public void setAnnouncement_id(String announcement_id) {
		this.announcement_id = announcement_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}
	
//	===================================
//	附属字段
//	===================================
	
	private String creator_name;
	
	private String editor_name;

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public String getEditor_name() {
		return editor_name;
	}

	public void setEditor_name(String editor_name) {
		this.editor_name = editor_name;
	}

}