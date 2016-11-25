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
 * 附加属性注解，表明属性只为实体附属属性不持久化到数据表中
 * @author cuixubin
 */
@Target({ElementType.FIELD})   
@Retention(RetentionPolicy.RUNTIME)   
public @interface Attached {
    String desc() default "无描述信息";  
}
