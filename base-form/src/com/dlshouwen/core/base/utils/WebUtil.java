package com.dlshouwen.core.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WebUtil工具类
 *
 * @author yangtong 2011/12/5
 */
public class WebUtil {
    
    static public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int expiry) throws Exception {
        if ((name != null) && (value != null)) {
            if (response != null) {
                value = URLEncoder.encode(value, "UTF-8");

                String contextPath = request.getContextPath();
                Cookie cookie = new Cookie(name, value);

                cookie.setPath(contextPath.length() > 0 ? contextPath : "/");

                cookie.setMaxAge(expiry);
                response.addCookie(cookie);
            }
        }
    }

    static public String getCookie(HttpServletRequest request, String name) throws UnsupportedEncodingException {
        if (name != null) {
            if (request != null) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ((cookie != null) && (name.equals(cookie.getName()))) {
                            String value = cookie.getValue();
                            value = URLDecoder.decode(value, "UTF-8");
                            return value;
                        }
                    }
                }
            }
        }
        return null;
    }

    static public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name)
            throws Exception {
        if (name != null) {
            addCookie(request, response, name, "", 0);
        }
    }
}
