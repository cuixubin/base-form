package com.dlshouwen.core.base.extra.spring.mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

/**
 * 对象映射
 * @author 大连首闻科技有限公司
 * @version 2013-7-29 17:16:57
 */
public class ClassRowMapper<T> implements RowMapper<T> {

	/**
	 * 内部类对象用于实例化
	 */
	private Class<T> mappedClass;
	
	/**
	 * 构造方法，包含类泛型对象
	 * @param mappedClass 类泛型对象
	 */
	public ClassRowMapper(Class<T> mappedClass){
		this.mappedClass = mappedClass;
	}
	
	public Class<T> getClazz() {
		return this.mappedClass;
	}
	
	/**
	 * 执行映射
	 */
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
//		定义泛型的对象
		T instance = null;
		try {
//			实例化泛型对象
			instance = mappedClass.newInstance();
//			获取对象参数列表
			PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
//			遍历对象参数列表
			for (PropertyDescriptor pd : pds) {
//				如果此参数可写则写入此内容
				if (pd.getWriteMethod() != null) {
//					获取参数属性
					String fieldName = pd.getName();
//					获取参数内容，如果数据查询中不存在此列则忽略
					Object value = null;
					try{
						value = rs.getObject(fieldName);
//						执行写入
						Method writeMethod = pd.getWriteMethod();
						if(value instanceof Double){
							writeMethod.invoke(instance, (Double)value);
						}else if(value instanceof Integer){
							writeMethod.invoke(instance, value);
						}else if(value instanceof BigDecimal){
							writeMethod.invoke(instance, ((BigDecimal) value).doubleValue());
						}else if(value instanceof Timestamp){
							writeMethod.invoke(instance, (Timestamp)value);
						}else{
							writeMethod.invoke(instance, value);
						}
					}catch(SQLException e){
//						e.printStackTrace();
					}catch(IllegalArgumentException e){
//						e.printStackTrace();
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
}
