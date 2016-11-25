/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.template.model;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 模板实体
 * @author cuixub
 */
public class Template {
        private String template_id;
    
        /** 模板名 */
	@NotBlank(message="模板名不能为空")
	@Length(min=2, max=40, message="模板标题长度必须在2-40字符之间")
	private String name;
        
        /** 模板描述 */
        @Length(min=0, max=200, message="模板描述长度必须在0-200字符之间")
	private String description;
        
        /** 模板演示文件名 */
        @NotBlank(message="模板演示文件名不能为空")
	@Length(min=2, max=40, message="演示文件名长度必须在3-40字符之间")
	private String demo_name;
        
        /** 模板状态：启用-停用 */
	@NotBlank(message="模板状态不能为空")
	@Length(min=0, max=2, message="模板状态长度必须在0-2之间")
	private String status;
        
        /** 模板类型 */
        private String template_type;
        
        /** 模板内容 */
        private String content;
        
        /** 模板创建者 */
	@Length(min=0, max=80, message="创建人长度必须在0-80之间")
	private String creator;
        
        /** 模板创建时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;
	
        /** 模板修改者 */
	@Length(min=0, max=40, message="编辑人长度必须在0-40之间")
	private String editor;
        
        /** 模板修改时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date edit_time;

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDemo_name() {
            return demo_name;
        }

        public void setDemo_name(String demo_name) {
            this.demo_name = demo_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTemplate_type() {
            return template_type;
        }

        public void setTemplate_type(String template_type) {
            this.template_type = template_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
	/** 创建人姓名 */
	private String creator_name;
	/** 编辑人姓名 */
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
