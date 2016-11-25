package com.dlshouwen.core.system.model;

import com.dlshouwen.core.base.Annotation.Attached;
import com.dlshouwen.core.base.Annotation.ExportDescrip;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户
 * @author 大连首闻科技有限公司
 * @version 2015-07-28 14:00:39
 */
public class User implements Serializable{

	private String user_id;
        @NotBlank(message="用户编号不能为空")
        @ExportDescrip(fname="编号")
	private String user_code;
        @NotBlank(message="用户姓名不能为空")
        @ExportDescrip(fname="姓名")
	private String user_name;
        
        @ExportDescrip(fname="所属部门", connecType="0", daoPath="com.dlshouwen.core.system.dao.DeptDao", methodInDao="getDeptNameList")
	private String dept_id;
        
        @ExportDescrip(fname="所属团队", connecType="0", daoPath="com.dlshouwen.core.team.dao.TeamDao", methodInDao="getTeamNameList")
	private String team_id;
        
	private String password;
        
        //有效性
	private String valid_type;
        //身份信息，依从码表
        private String identity;
        
        @ExportDescrip(fname="性别", connecType="sex")
	private String sex;
        
        @ExportDescrip(fname="证件类型", connecType="card_type")
	private String card_type;
        
        @ExportDescrip(fname="证件号")
	private String card_id;
        
        @ExportDescrip(fname="出生日期")
	private Date birthday;
        
        @ExportDescrip(fname="工作日期")
	private Date work_date;
        
        private String imgpath;
        
        @ExportDescrip(fname="民族", connecType="folk")
	private String folk;
        
        @ExportDescrip(fname="学历", connecType="degree")
	private String degree;
        
        @ExportDescrip(fname="教师资质", connecType="jszz")
	private String qualified;
        
        @ExportDescrip(fname="毕业院校")
	private String graduateSchool;
        
        
        @ExportDescrip(fname="电话")
	private String phone;

        @ExportDescrip(fname="邮箱")
	private String email;
        
        @ExportDescrip(fname="住址")
	private String address;
        
        @ExportDescrip(fname="备注")
	private String remark;

	private String creator;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;

	private String editor;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date edit_time;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValid_type() {
		return valid_type;
	}

	public void setValid_type(String valid_type) {
		this.valid_type = valid_type;
	}

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getWork_date() {
		return work_date;
	}

	public void setWork_date(Date work_date) {
		this.work_date = work_date;
	}

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }
        
	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

        public String getQualified() {
            return qualified;
        }

        public void setQualified(String qualified) {
            this.qualified = qualified;
        }

        public String getGraduateSchool() {
            return graduateSchool;
        }

        public void setGraduateSchool(String graduateSchool) {
            this.graduateSchool = graduateSchool;
        }
        
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }
        
//	===================================
//	附属字段
//	===================================
	@Attached
	private String dept_name;
        @Attached
        private String team_name;
	@Attached
	private String creator_name;
	@Attached
	private String editor_name;

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

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

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }
        
}