/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Vector;
import org.springframework.stereotype.Component;

/**
 *
 * @author cuixubin
 */
@Component("resquester")
public class HttpRequester {
    private String defaultContentEncoding;     
      
    public HttpRequester() {     
        this.defaultContentEncoding = "utf-8";    
    }     
      
    /**   
     * 发送GET请求   
     *    
     * @param urlString   
     * URL地址   
     * @return 响应对象   
     * @throws IOException   
     */    
    public HttpRespons sendGet(String urlString) throws IOException {     
        return this.send(urlString, "GET", null, null);     
    }     
      
    /**   
     * 发送GET请求   
     *    
     * @param urlString   
     *            URL地址   
     * @param params   
     *            参数集合   
     * @return 响应对象   
     * @throws IOException   
     */    
    public HttpRespons sendGet(String urlString, Map<String, String> params)     
            throws IOException {     
        return this.send(urlString, "GET", params, null);     
    }     
      
    /**   
     * 发送GET请求   
     *    
     * @param urlString   
     *            URL地址   
     * @param params   
     *            参数集合   
     * @param propertys   
     *            请求属性   
     * @return 响应对象   
     * @throws IOException   
     */    
    public HttpRespons sendGet(String urlString, Map<String, String> params,     
            Map<String, String> propertys) throws IOException {     
        return this.send(urlString, "GET", params, propertys);     
    }     
      
    /**   
     * 发送POST请求   
     *    
     * @param urlString   
     *            URL地址   
     * @return 响应对象   
     * @throws IOException   
     */    
    public HttpRespons sendPost(String urlString) throws IOException {     
        return this.send(urlString, "POST", null, null);     
    }     
      
    /**   
     * 发送POST请求   
     *    
     * @param urlString   
     *            URL地址   
     * @param params   
     *            参数集合   
     * @return 响应对象   
     * @throws IOException   
     */    
    public HttpRespons sendPost(String urlString, Map<String, String> params)     
            throws IOException {     
        return this.send(urlString, "POST", params, null);     
    }     
      
    /**   
     * 发送POST请求   
     *    
     * @param urlString   
     *            URL地址   
     * @param params   
     *            参数集合   
     * @param propertys   
     *            请求属性   
     * @return 响应对象   
     * @throws IOException   
     */    
    public HttpRespons sendPost(String urlString, Map<String, String> params,     
            Map<String, String> propertys) throws IOException {     
        return this.send(urlString, "POST", params, propertys);     
    }     
      
    /**   
     * 发送HTTP请求   
     *    
     * @param urlString   
     * @return 响映对象   
     * @throws IOException   
     */    
    private HttpRespons send(String urlString, String method,     
            Map<String, String> parameters, Map<String, String> propertys)     
            throws IOException {     
        HttpURLConnection urlConnection = null;     
      
        if (method.equalsIgnoreCase("GET") && parameters != null) {     
            StringBuffer param = new StringBuffer();     
            int i = 0;     
            for (String key : parameters.keySet()) {     
                if (i == 0)     
                    param.append("?");     
                else    
                    param.append("&");     
                param.append(key).append("=").append(parameters.get(key));     
                i++;     
            }     
            urlString += param;     
        }     
        URL url = new URL(urlString);   
        urlConnection = (HttpURLConnection) url.openConnection();     
        urlConnection.setConnectTimeout(5000);
        urlConnection.setRequestMethod(method);     
        urlConnection.setDoOutput(true);     
        urlConnection.setDoInput(true);     
        urlConnection.setUseCaches(false);     
      
        if (propertys != null)     
            for (String key : propertys.keySet()) {     
                urlConnection.addRequestProperty(key, propertys.get(key));     
            }     
      
        if (method.equalsIgnoreCase("POST") && parameters != null) {     
            StringBuffer param = new StringBuffer();     
            for (String key : parameters.keySet()) {     
                param.append("&");     
                param.append(key).append("=").append(parameters.get(key));     
            }     
            urlConnection.getOutputStream().write(param.toString().getBytes());     
            urlConnection.getOutputStream().flush();     
            urlConnection.getOutputStream().close();     
        }     
      
        return this.makeContent(urlString, urlConnection);     
    }     
      
    /**   
     * 得到响应对象   
     *    
     * @param urlConnection   
     * @return 响应对象   
     * @throws IOException   
     */    
    private HttpRespons makeContent(String urlString,     
            HttpURLConnection urlConnection) throws IOException {     
        HttpRespons httpResponser = new HttpRespons();     
        try {     
            InputStream in = urlConnection.getInputStream();   
            
            BufferedReader bufferedReader = new BufferedReader(      
                    new InputStreamReader(in,"utf-8"));     
            Vector<String> contents = new Vector<String>();     
            StringBuffer temp = new StringBuffer();     
            String line = bufferedReader.readLine();     
            while (line != null) {     
                contents.add(line);     
                temp.append(line).append("\r\n");     
                line = bufferedReader.readLine();     
            }
            bufferedReader.close();  
            
            
            
            httpResponser.setContentCollection(contents);
            
            String ecod = urlConnection.getContentEncoding();     
            if (ecod == null)     
                ecod = this.defaultContentEncoding;     
      
            httpResponser.setUrlString(urlString);
            httpResponser.setDefaultPort(urlConnection.getURL().getDefaultPort());     
            httpResponser.setFile(urlConnection.getURL().getFile());     
            httpResponser.setHost(urlConnection.getURL().getHost());     
            httpResponser.setPath(urlConnection.getURL().getPath());     
            httpResponser.setPort(urlConnection.getURL().getPort());     
            httpResponser.setProtocol(urlConnection.getURL().getProtocol());     
            httpResponser.setQuery(urlConnection.getURL().getQuery());     
            httpResponser.setRef(urlConnection.getURL().getRef());     
            httpResponser.setUserInfo(urlConnection.getURL().getUserInfo());     
      
            httpResponser.setContent(new String(temp.toString().getBytes("utf-8"), ecod));     
            httpResponser.setContentEncoding(ecod);     
            httpResponser.setCode(urlConnection.getResponseCode());     
            httpResponser.setMessage(urlConnection.getResponseMessage());     
            httpResponser.setContentType(urlConnection.getContentType());     
            httpResponser.setMethod(urlConnection.getRequestMethod());     
            httpResponser.setConnectTimeout(urlConnection.getConnectTimeout());     
            httpResponser.setReadTimeout(urlConnection.getReadTimeout());     
      
            return httpResponser;     
        } catch (IOException e) { 
            e.printStackTrace();
            throw e;     
        } finally {     
            if (urlConnection != null)     
                urlConnection.disconnect();     
        }     
    }     
      
    /**   
     * 默认的响应字符集   
     */    
    public String getDefaultContentEncoding() {     
        return this.defaultContentEncoding;     
    }     
      
    /**   
     * 设置默认的响应字符集   
     */    
    public void setDefaultContentEncoding(String defaultContentEncoding) {     
        this.defaultContentEncoding = defaultContentEncoding;     
    }     
}
