package com.dlshouwen.core.base.extra.unique.utils;

import java.util.ResourceBundle;

import com.dlshouwen.core.base.dao.BaseDao;
import com.dlshouwen.core.base.model.AjaxResponse;

/**
 * 唯一验证工具类
 * @author 大连首闻科技有限公司
 * @version 2013-8-8 10:27:03
 */
public class UniqueUtils {
	
	/**
	 * 验证唯一
	 * <p>用于进行表单唯一验证
	 * @param dao 数据库操作基类
	 * @param ajaxResponse Ajax响应回执
	 * @param warningMessage 验证出错时的错误信息
	 * @param sqlCode SQL的对应键值
	 * @param args 参数列表，可变参数
	 * @return 验证唯一是否成功
	 * @throws Exception 抛出全部异常
	 */
	public static boolean unique(BaseDao dao, AjaxResponse ajaxResponse, String warningMessage, 
			String sqlCode, Object... args) throws Exception {
//		获取sql
		ResourceBundle bundle = ResourceBundle.getBundle("uniqueSqls");
		String sql = bundle.getString(sqlCode);
//		获取参数列表
		int count = dao.queryForInt(sql, args);
//		处理结果级
		boolean validResult = count==0?true:false;
//		处理Ajax响应回执
		if(!validResult){
			ajaxResponse.setWarning(true);
			ajaxResponse.setWarningMessage(warningMessage);
		}
		return validResult;
	}

}
