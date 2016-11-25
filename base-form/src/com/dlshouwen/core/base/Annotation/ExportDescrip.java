/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体导出excel字段描述注解
 * @author cuixubin
 */
@Target({ElementType.FIELD})   
@Retention(RetentionPolicy.RUNTIME)   
public @interface ExportDescrip {
    /**
     * 属性名称(表头字段)
     * @return 
     */
    String fname() default "";  
    /**
     * 关联属性的关联类型（默认为""表示输入普通字段，不关联任何表；
     *      值为"0"表示关联实体；值为非数字字符串表示关联码表数据，比如"jszz"表示教师资质码表数据）
     * @return 
     */
    String connecType() default "";
    /**
     * 关联属性所关联实体的dao路径
     * @return 
     */
    String daoPath() default "";
    /**
     * 关联dao中的list方法名
     * @return 
     */
    String methodInDao() default "";
    
}
