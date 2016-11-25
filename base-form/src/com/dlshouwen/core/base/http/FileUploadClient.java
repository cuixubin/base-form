/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.http;

import com.dlshouwen.core.base.utils.AttributeUtils;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 *
 * @author xcyg
 */
public class FileUploadClient {

    /**
     * 向服文件服务器上传文件
     * @param request
     * @param fname 文件名(包含后缀)
     * @param inputStream 输入流
     * @return 
     */
    public static JSONObject upFile(HttpServletRequest request, String fname, InputStream inputStream) {
        HttpURLConnection conn = null;
        OutputStream out = null;
        DataInputStream dis = null;
        InputStream is = null;
        JSONObject obj  = new JSONObject();
        try {
            String sourceWebAppUrl = AttributeUtils.getAttributeContent(request.getServletContext(), "file_upload_webapp_interface");
            if(StringUtils.isEmpty(fname) || StringUtils.isEmpty(sourceWebAppUrl) || null == inputStream) {
                return obj;
            }
            fname = "filename" + fname.substring(fname.lastIndexOf("."));
            URL url = new URL(sourceWebAppUrl + "?filename=" + fname);
            conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行  

            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/html");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("charset", "UTF-8");
            conn.connect();
            conn.setConnectTimeout(10000);
            out = conn.getOutputStream();

            dis = new DataInputStream(inputStream);

            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = dis.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
            dis.close();
            out.flush();
            out.close();
            try {
                is = conn.getInputStream();   
            }catch(Exception e) {
                obj.put("responseMessage", "ERROR");
                obj.put("errorMessage", "无法访问文件服务器。");
                return obj;
            }
            
            BufferedReader bufferedReader = new BufferedReader(      
                    new InputStreamReader(is,"utf-8"));     
            Vector<String> contents = new Vector<String>();     
            StringBuffer temp = new StringBuffer();     
            String line = bufferedReader.readLine();     
            while (line != null) {     
                contents.add(line);     
                temp.append(line).append("\r\n");     
                line = bufferedReader.readLine();     
            }
            bufferedReader.close(); 
            if(contents.size() > 0) {
                obj = JSONObject.fromObject(contents.get(0));
            }
            obj.put("responseMessage", conn.getResponseMessage());
            return obj;
        } catch (Exception e) {
            System.out.println("发送文件出现异常！" + e);
            e.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileUploadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(dis != null) {
                try {
                    dis.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileUploadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileUploadClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
