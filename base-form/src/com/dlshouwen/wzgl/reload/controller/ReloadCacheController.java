/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.reload.controller;

import com.dlshouwen.core.base.http.HttpRequester;
import com.dlshouwen.core.base.http.HttpRespons;
import com.dlshouwen.core.base.model.AjaxResponse;
import com.dlshouwen.core.base.utils.AttributeUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author cuixubin
 */
@Controller
@RequestMapping("wzgl/reloadCash/reloadCash")
public class ReloadCacheController {
    private HttpRequester requester;
    
    @Resource(name = "resquester")
    public void setRequester(HttpRequester requester) {
        this.requester = requester;
    }
    
    /**
     * 更新网站页面
     *
     * @param request 请求对象
      */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse reload(HttpServletRequest request) {
        JSONObject jobj = null;
        AjaxResponse ajaxResp = new AjaxResponse();
        String urls = AttributeUtils.getAttributeContent(request.getServletContext(), "wzgl_reload_interface");
        boolean reloadFlag = true;
        String errorUrl = "";
        if(StringUtils.isNotEmpty(urls)) {
            try {
                String url[] = urls.split(",");
                for(int i=0; i<url.length; i++) {
                    HttpRespons resps = requester.sendPost(url[i]);
                    resps.getContent();
                    if(StringUtils.isNotEmpty(resps.getContent())) {
                        try {
                            jobj = JSONObject.fromObject(resps.getContent());
                            if(StringUtils.isEmpty(jobj.getString("reloadOK")) || !jobj.getString("reloadOK").equals("true")) {
                                errorUrl += "出错接口为接口"+ (i+1) +"：" + url[i] + "<br/>";
                                errorUrl += "出错信息：" + jobj.getString("reloadErrorMsg") + "<br/>"; 
                                reloadFlag = false;
                            }
                        }catch(Exception e) {
                            e.printStackTrace();
                            errorUrl += "出错接口为接口"+ (i+1) +"：" + url[i] + "<br/>";
                            reloadFlag = false;
                        }
                    }else {
                        errorUrl += "出错接口为接口"+ (i+1) +"：" + url[i] + "<br/>";
                        reloadFlag = false;
                    }
                }
                if(reloadFlag) {
                    ajaxResp.setSuccess(true);
                    ajaxResp.setSuccessMessage("更新成功！");
                }else {
                    ajaxResp.setError(true);
                    ajaxResp.setErrorMessage("更新完成！但有部分接口出错。<br/>" + errorUrl);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ajaxResp.setError(true);
                ajaxResp.setErrorMessage("更新失败！网站缓存接口配置错误。");
            }
        }else {
            ajaxResp.setError(true);
            ajaxResp.setErrorMessage("更新失败！网站缓存接口未配置。");
        }
        return ajaxResp;
    }
}
