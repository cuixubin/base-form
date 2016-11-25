package com.dlshouwen.core.base.config;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;

/**
 * Config对象
 *
 * @author 大连首闻科技有限公司
 * @version 2016-4-29 08:34:47
 */
public class CONFIG {

    /**
     * 单例模式对象
     */
    private static CONFIG config;

    /**
     * Servlet Context
     */
    public static ServletContext SERVLET_CONTEXT;

    /**
     * 字符编码
     */
    public static String ENCODING;

    /**
     * 项目Host名称
     */
    public static String HOST;

    /**
     * 首页地址
     */
    public static String FIRST_PAGE_PATH;

    /**
     * ProjectSession中存储的名称
     */
    public static String PROJECT_SESSION;
    /**
     * Session中存储登录用户的名称
     */
    public static String SESSION_USER;
    /**
     * Session出错的跳转页面
     */
    public static String SESSION_INVALID_PAGE;

    /**
     * Application域中存储的码表的名称
     */
    public static String CODE_TABLE;
    /**
     * 码表初始化的数据源
     */
    public static String CODE_TABLE_DATA_SOURCE;
    /**
     * 码表初始化的SQL
     */
    public static String CODE_TABLE_SQL;

    /**
     * Application域中存储的系统参数的名称
     */
    public static String SYSTEM_ATTRIBUTE;
    /**
     * 系统参数初始化的数据源
     */
    public static String SYSTEM_ATTRIBUTE_DATA_SOURCE;
    /**
     * 系统参数初始化的SQL
     */
    public static String SYSTEM_ATTRIBUTE_SQL;

    /**
     * 皮肤根路径
     */
    public static String SKIN_ROOT_PATH;

    /**
     * 初始化登录日志数据源
     */
    public static String LOGIN_LOG_DATA_SOURCE;

    /**
     * 强制下线页面
     */
    public static String FORCE_OUTLINE_PAGE;
    /**
     * 无权限页面
     */
    public static String NO_LIMIT_PAGE;
    /**
     * 时间限制页面
     */
    public static String LIMIT_TIME_FOURCE_PAGE;

    /**
     * 操作日志的SESSION缓存信息
     */
    public static String OPERATION_LOG;

    /**
     * Application域中存储的操作日志的名称
     */
    public static String OPERATION_LOG_APN;
    /**
     * Application域中存储的SQL日志的名称
     */
    public static String SQL_LOG_APN;

    /**
     * 设置用户信息Cookie存储天数
     */
    public static int SYSTEM_LOGIN_USER_COOKIE_AVAILABLE_DAY;
    /**
     * 设置用户皮肤信息Cookie存储天数
     */
    public static int SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY;
    /**
     * 设置是否背景浮动Cookie存储天数
     */
    public static int SYSTEM_IS_BACKGROUND_FLOAT_COOKIE_AVAILABLE_DAY;
    /**
     * 设置背景浮动速度Cookie存储天数
     */
    public static int SYSTEM_BACKGROUND_FLOAT_SPEED_COOKIE_AVAILABLE_DAY;

    /**
     * 文件服务器访问路径
     */
    public static String FILE_PREVIEW_PATH;
    /**
     * 文件服务器根目录
     */
    public static String FILE_ROOT_PATH;
    /**
     * 上传文件格式限制
     */
    public static String UPLOAD_FILE_PATTERN;
    /**
     * 上传文件大小限制
     */
    public static String UPLOAD_FILE_MAX_SIZE;

    /**
     * 图片服务器访问路径
     */
    public static String IMAGE_PREVIEW_PATH;
    /**
     * 图片服务器根目录
     */
    public static String IMAGE_ROOT_PATH;
    /**
     * 上传图片格式限制
     */
    public static String UPLOAD_PIC_PATTERN;
    /**
     * 上传图片大小限制
     */
    public static String UPLOAD_PIC_MAX_SIZE;

    /**
     * 上传文件路径
     */
    public static String UPLOAD_PIC_PATH;
    public static String UPLOAD_FILE_PATH;
    public static String UPLOAD_NEWS_PATH;

    /**
     * 模板文件路径
     */
    public static String UPLOAD_TEMPLATE_FILE_PATH;
    /**
     * 上传视频路径
     */
    public static String UPLOAD_VIDEO_PATH;

    /**
     * 构造方法私有化并调用初始化方法
     */
    private CONFIG() {
        init();
    }

    /**
     * 实例获取方法
     */
    public static CONFIG getInstance() {
        return config == null ? new CONFIG() : config;
    }

    /**
     * 初始化参数获取方法
     */
    private void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("core");

        ENCODING = bundle.getString("ENCODING");

        HOST = bundle.getString("HOST");

        FIRST_PAGE_PATH = bundle.getString("FIRST_PAGE_PATH");

        PROJECT_SESSION = bundle.getString("PROJECT_SESSION");
        SESSION_USER = bundle.getString("SESSION_USER");
        SESSION_INVALID_PAGE = bundle.getString("SESSION_INVALID_PAGE");

        CODE_TABLE = bundle.getString("CODE_TABLE");
        CODE_TABLE_DATA_SOURCE = bundle.getString("CODE_TABLE_DATA_SOURCE");
        CODE_TABLE_SQL = bundle.getString("CODE_TABLE_SQL");

        SYSTEM_ATTRIBUTE = bundle.getString("SYSTEM_ATTRIBUTE");
        SYSTEM_ATTRIBUTE_DATA_SOURCE = bundle.getString("SYSTEM_ATTRIBUTE_DATA_SOURCE");
        SYSTEM_ATTRIBUTE_SQL = bundle.getString("SYSTEM_ATTRIBUTE_SQL");

        SKIN_ROOT_PATH = bundle.getString("SKIN_ROOT_PATH");

        LOGIN_LOG_DATA_SOURCE = bundle.getString("LOGIN_LOG_DATA_SOURCE");

        OPERATION_LOG_APN = bundle.getString("OPERATION_LOG_APN");
        SQL_LOG_APN = bundle.getString("SQL_LOG_APN");

        FORCE_OUTLINE_PAGE = bundle.getString("FORCE_OUTLINE_PAGE");
        NO_LIMIT_PAGE = bundle.getString("NO_LIMIT_PAGE");
        LIMIT_TIME_FOURCE_PAGE = bundle.getString("LIMIT_TIME_FOURCE_PAGE");

        OPERATION_LOG = bundle.getString("OPERATION_LOG");

        SYSTEM_LOGIN_USER_COOKIE_AVAILABLE_DAY = Integer.parseInt(bundle.getString("SYSTEM_LOGIN_USER_COOKIE_AVAILABLE_DAY"));
        SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY = Integer.parseInt(bundle.getString("SYSTEM_LOGIN_USER_SKIN_INFO_COOKIE_AVAILABLE_DAY"));
        SYSTEM_IS_BACKGROUND_FLOAT_COOKIE_AVAILABLE_DAY = Integer.parseInt(bundle.getString("SYSTEM_IS_BACKGROUND_FLOAT_COOKIE_AVAILABLE_DAY"));
        SYSTEM_BACKGROUND_FLOAT_SPEED_COOKIE_AVAILABLE_DAY = Integer.parseInt(bundle.getString("SYSTEM_BACKGROUND_FLOAT_SPEED_COOKIE_AVAILABLE_DAY"));

        FILE_PREVIEW_PATH = bundle.getString("FILE_PREVIEW_PATH");
        FILE_ROOT_PATH = bundle.getString("FILE_ROOT_PATH");
        UPLOAD_FILE_PATTERN = bundle.getString("UPLOAD_FILE_PATTERN");
        UPLOAD_FILE_MAX_SIZE = bundle.getString("UPLOAD_FILE_MAX_SIZE");

        IMAGE_PREVIEW_PATH = bundle.getString("IMAGE_PREVIEW_PATH");
        IMAGE_ROOT_PATH = bundle.getString("IMAGE_ROOT_PATH");
        UPLOAD_PIC_PATTERN = bundle.getString("UPLOAD_PIC_PATTERN");
        UPLOAD_PIC_MAX_SIZE = bundle.getString("UPLOAD_PIC_MAX_SIZE");

        UPLOAD_PIC_PATH = bundle.getString("UPLOAD_PIC_PATH");
        UPLOAD_FILE_PATH = bundle.getString("UPLOAD_FILE_PATH");
        UPLOAD_NEWS_PATH = bundle.getString("UPLOAD_NEWS_PATH");

        UPLOAD_TEMPLATE_FILE_PATH = bundle.getString("UPLOAD_TEMPLATE_FILE_PATH");
        UPLOAD_VIDEO_PATH = bundle.getString("UPLOAD_VIDEO_PATH");
    }

}
