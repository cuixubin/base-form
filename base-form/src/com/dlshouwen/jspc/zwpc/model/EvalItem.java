/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.jspc.zwpc.model;

import com.dlshouwen.core.base.Annotation.Attached;
import java.util.Date;

/**
 * 评测项实体
 *
 * @author cuixubin
 */
public class EvalItem {
    private String evalItem_id;
    
    /** 获得荣誉或称谓 */
    private  String name;
    /** 评测项所属分类 */
    private String type;
    /** 详情描述 */
    private String description;
    /** 所属级别（国家、省级、市级...） */
    private String level;
    /** 所属等级（一等、二等、三等） */
    private String grade;
    /** 关联评审 */
    private String evalResult_id;
    /** 附件信息 */
    private String attach;
    /** 开始时间 */
    private Date beginDate;
    /** 结束时间 */
    private Date endDate;
    /** 创建时间 */
    private Date createDate;
    /** 修改时间 */
    private Date editDate;
    /** 创建者 */
    private String creator;
    /** 修改者 */
    private String editor;

    public String getEvalItem_id() {
        return evalItem_id;
    }

    public void setEvalItem_id(String evalItem_id) {
        this.evalItem_id = evalItem_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEvalResult_id() {
        return evalResult_id;
    }

    public void setEvalResult_id(String evalResult_id) {
        this.evalResult_id = evalResult_id;
    }
    
    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
    
//    附加信息      
    /** 创建人姓名 */
    @Attached
    private String creator_name;
    /** 编辑人姓名 */
    @Attached
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
