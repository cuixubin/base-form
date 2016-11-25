package com.dlshouwen.core.base.extra.unique.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dlshouwen.core.base.utils.SpringUtils;

/**
 * 验证唯一
 * @author 大连首闻科技有限公司
 * @version 2013-8-6 20:16:53
 */
@Controller
@RequestMapping("/core/base/extra/unique")
@SuppressWarnings({"deprecation", "unchecked"})
public class UniqueController {
	
	/**
	 * 执行唯一校验
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 验证结果，成功为true 失败为false
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public void unique(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		获取参数
		String validParams = request.getParameter("validParams");
		JSONObject validParamsObj = JSONObject.fromObject(validParams);
		String sqlCode = validParamsObj.getString("sqlCode");
		String key = validParamsObj.getString("key");
		List<Object> attrs = (List<Object>)JSONArray.toCollection(validParamsObj.getJSONArray("attrs"), List.class);
//		获取sql
		ResourceBundle bundle = ResourceBundle.getBundle("uniqueSqls");
		String sql = bundle.getString(sqlCode);
//		执行查询结果集，结果集应该是一个数值
		JdbcTemplate template = new JdbcTemplate((DataSource)SpringUtils.getBean("defaultDataSource"));
//		获取参数列表
		int count;
		if(attrs==null||attrs.size()==0){
			count = template.queryForInt(sql, key);
		}else{
			attrs.add(0, key);
			count = template.queryForObject(sql, attrs.toArray(), Integer.class);
		}
//		回写数据
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(String.valueOf(count==0?true:false));
	}

}
