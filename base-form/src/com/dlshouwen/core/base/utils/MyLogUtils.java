/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.utils;
import org.slf4j.LoggerFactory;
/**
 *
 * @author sunxingwu
 */
public class MyLogUtils {
    public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyLogUtils.class);
    public static org.slf4j.Logger logger_logback = LoggerFactory.getLogger(MyLogUtils.class);

    public static void logInfo(Object message)
    {
      logger.info(message == null ? "" : message);
    }

    public static void logInfo(Object message, Throwable e)
    {
      logger.info(message == null ? "" : message, e);
    }

    public static void logWarn(Object message)
    {
      logger.warn(message == null ? "" : message);
    }

    public static void logWarn(Object message, Throwable e)
    {
      logger.warn(message == null ? "" : message, e);
    }

    public static void logError(Object message)
    {
      logger.error(message == null ? "" : message);
    }

    public static void logError(Object message, Throwable e)
    {
      logger.error(message == null ? "" : message, e);
    }
}
