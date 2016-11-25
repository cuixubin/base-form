/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author sunxingwu
 */
public class SendSMS {

    static String corpID = null;
    static String batchCorpID = null;
    static String password = null;
    static String blackDictionary = null;
    static String isOpen = null;

    static {
        try {
            InputStream is = SendSMS.class.getResourceAsStream("/conf/sysConfig.properties");
            Properties p = new Properties();
            p.load(is);
            corpID = SysConfigLoader.getSystemConfig().getProperty("corpID");
            batchCorpID = SysConfigLoader.getSystemConfig().getProperty("batchCorpID");
            password = SysConfigLoader.getSystemConfig().getProperty("password");
            blackDictionary = SysConfigLoader.getSystemConfig().getProperty("blackDictionary");
            isOpen = SysConfigLoader.getSystemConfig().getProperty("openSMS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    }
    
    public static int sendSMS(String mobile, String smsContent, String sendTime) {
        if (!MyFileUtils.readTxtFile(blackDictionary, smsContent) && isOpen.equals("1")) {
            URL url = null;
            try {
                String sendContent = URLEncoder.encode(smsContent.replaceAll("<br/>", " "),
                        "GBK");
                url = new URL("http://www.ht3g.com/htws/Send.aspx?CorpID=" + corpID
                        + "&Pwd=" + password + "&Mobile=" + mobile + "&Content="
                        + sendContent + "&Cell=&SendTime=" + sendTime);
            } catch (UnsupportedEncodingException eu) {
                eu.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            int inputLine = 0;
            try {
                MyLogUtils.logInfo("发送短信手机号码为 ：" + mobile);
                in = new BufferedReader(new InputStreamReader(url.openStream()));
                inputLine = new Integer(in.readLine()).intValue();
            } catch (Exception e) {
                MyLogUtils.logInfo("网络异常,发送短信失败！");
                inputLine = -9;
            }
            MyLogUtils.logInfo("发送短信结束返回值：  " + inputLine);
            return inputLine;
        }
        return 33;
    }
    
    public static int batchSend(String mobile, String smsContent, String sendTime) {
        if (!MyFileUtils.readTxtFile(blackDictionary, smsContent)) {
            URL url = null;
            try {
                String sendContent = URLEncoder.encode(smsContent.replaceAll("<br/>", " "),
                        "GBK");
                url = new URL("http://www.ht3g.com/htWS/BatchSend.aspx?CorpID="
                        + batchCorpID + "&Pwd=" + password + "&Mobile=" + mobile
                        + "&Content=" + sendContent + "&Cell=&SendTime=" + sendTime);
            } catch (UnsupportedEncodingException eu) {
                eu.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            int inputLine = 0;
            try {
                MyLogUtils.logInfo("发送短信手机号码为 ：" + mobile);
                in = new BufferedReader(new InputStreamReader(url.openStream()));
                inputLine = new Integer(in.readLine()).intValue();
            } catch (Exception e) {
                MyLogUtils.logInfo("网络异常,发送短信失败！");
                inputLine = -9;
            }
            return inputLine;
        }
        return 33;
    }
    
    public static List receiveSMS() {
        URL url = null;
        try {
            url = new URL("http://www.ht3g.com/htWS/Get.aspx?CorpID=" + corpID + "&Pwd="
                    + password);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String retunVal = in.readLine();
            List<Map> list = new ArrayList();
            if (retunVal == null) {
                return null;
            }
            String[] outVals = retunVal.split("\\|\\|");
            for (int i = 1; i < outVals.length; i++) {
                String[] vals = outVals[i].split("\\#");
                Map maps = new HashMap();
                for (int j = 0; j < vals.length; j++) {
                    if (j == 0) {
                        maps.put("mobile", vals[j]);
                    }
                    if (j == 1) {
                        maps.put("receiveContent", vals[j]);
                    }
                    if (j == 2) {
                        maps.put("receiveTime", vals[j]);
                    }
                }
                list.add(maps);
            }
            MyLogUtils.logInfo(list);
            return list;
        } catch (Exception e) {
            MyLogUtils.logInfo("网络异常,接收短信失败！");
        }
        return null;
    }
}
