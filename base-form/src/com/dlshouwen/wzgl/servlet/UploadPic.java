/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.wzgl.servlet;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.http.FileUploadClient;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.AttributeUtils;
import com.dlshouwen.core.base.utils.SpringUtils;
import com.dlshouwen.core.base.utils.SysConfigLoader;
import com.dlshouwen.wzgl.picture.dao.PictureDao;
import com.dlshouwen.wzgl.picture.model.Picture;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author haohao
 */
@WebServlet(name = "uploadPic", urlPatterns = {"/uploadPic"})
public class UploadPic extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String albumId = request.getParameter("albumId");
//      String articleId = request.getParameter("articleId");
        String type = request.getParameter("albumFlag");
//      String isFile = request.getParameter("isFile");
//      String isVideo = request.getParameter("isVideo");
        PictureDao pictureDao = null;
        try {
            pictureDao = (PictureDao) SpringUtils.getBean("pictureDao");
        } catch (Exception ex) {
            Logger.getLogger(UploadPic.class.getName()).log(Level.SEVERE, null, ex);
        }

        // 临时文件目录   
        String tempPath = SysConfigLoader.getSystemConfig().getProperty("imageTemp", "C:\\files\\temp");
        //创建临时文件夹  
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()) {
            dirTempFile.mkdirs();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。     
        factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。     
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                String fileName = item.getName();
                if (!item.isFormField()) {
                    InputStream is = null;
                    synchronized (this) {
                        try {
                            is = item.getInputStream();
                            JSONObject jobj = FileUploadClient.upFile(request, fileName, is);
                            String path = null;
                            if(null != jobj && jobj.getString("responseMessage").equals("OK")) {
                                if(StringUtils.isNotEmpty(jobj.getString("fpath"))) {
                                    String sourceURL = AttributeUtils.getAttributeContent(request.getServletContext(), "source_webapp_file_postion");
                                    path = sourceURL + jobj.getString("fpath");
//                                  filename = path.substring(path.lastIndexOf(File.separator) + 1);
                                }
                            }
                            
                            if (albumId != null && albumId.trim().length() > 0) {
                                Picture pic = new Picture();
                                if (type != null) {
                                    pic.setFlag(type);
                                } 
                                pic.setPicture_name(fileName);
                                pic.setPath(path);
                                pic.setAlbum_id(albumId);
                                pic.setCreate_time(new Date());
                                SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
                                String userName = sessionUser.getUser_name();
                                pic.setUser_name(userName);
                                pictureDao.insertPicture(pic);
                            }
                            
                            String json = "{ \"state\": \"SUCCESS\",\"url\": \"" + path + "\",\"title\": \""
                                        + fileName + "\",\"original\": \"" + fileName + "\"}";
                            
                            response.setContentType("text/html;charset=utf-8");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().print(json);
                        } catch (Exception ex) {
                            java.util.logging.Logger.getLogger(UploadPic.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                }
            }

        } catch (FileUploadException e) {
        }
    }
/*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String albumId = request.getParameter("albumId");
        String articleId = request.getParameter("articleId");
        String type = request.getParameter("albumFlag");
        String isFile = request.getParameter("isFile");
        String isVideo = request.getParameter("isVideo");
        PictureDao pictureDao = null;
        try {
            pictureDao = (PictureDao) SpringUtils.getBean("pictureDao");
        } catch (Exception ex) {
            Logger.getLogger(UploadPic.class.getName()).log(Level.SEVERE, null, ex);
        }

        // 临时文件目录   
        String tempPath = SysConfigLoader.getSystemConfig().getProperty("imageTemp", "C:\\files\\temp");
        //创建临时文件夹  
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()) {
            dirTempFile.mkdirs();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。     
        factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。     
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                String fileName = item.getName();
                if (!item.isFormField()) {
                    InputStream is = null;
                    FileOutputStream fos = null;
                    synchronized (this) {
                        try {
                            is = item.getInputStream();
                            String fileDirPath = request.getSession().getServletContext().getRealPath("/");
                            String path = fileDirPath.substring(0, fileDirPath.lastIndexOf(File.separator));
                            if(StringUtils.isNotEmpty(articleId)) {
                                path += CONFIG.UPLOAD_NEWS_PATH + "/" + articleId + "/";
                                if(null != isFile) {
                                    path += "file";
                                }else if(null != isVideo){
                                    path += "video";
                                }else {
                                    path += "image";
                                }
                            }else {
                                if(null != isFile) {
                                    path += CONFIG.UPLOAD_FILE_PATH;
                                }else if(null != isVideo){
                                    path += CONFIG.UPLOAD_VIDEO_PATH;
                                }else {
                                    path += CONFIG.UPLOAD_PIC_PATH;
                                }
                            }
                            String onlyPath = path;
                            Date date = new Date();
                            fileName = String.valueOf(date.getTime()) + fileName;
                            File file = new File(path);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            file = new File(path + "/" + fileName);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            path = path + "/" + fileName;
                            byte[] buffer = new byte[1024];
                            int read = 0;
                            fos = new FileOutputStream(file);
                            while ((read = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, read);
                            }
                            fos.flush();
                            if (albumId != null && albumId.trim().length() > 0) {
                                Picture pic = new Picture();
                                if (type != null) {
                                    pic.setFlag(type);
                                } 
                                pic.setPicture_name(fileName);
                                pic.setPath(path);
                                pic.setAlbum_id(albumId);
                                pic.setCreate_time(new Date());
                                SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CONFIG.SESSION_USER);
                                String userName = sessionUser.getUser_name();
                                pic.setUser_name(userName);
                                pictureDao.insertPicture(pic);
                            }
                            String json = "";
                            if (isFile != null && isFile.trim().length() > 0 && isFile.equals("file")) {
                                json = "{ \"state\": \"SUCCESS\",\"url\": \"http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/downloadImage.jsp?path=" + path.replaceAll("\\\\", "/") + "\",\"title\": \""
                                        + fileName + "\",\"original\": \"" + fileName + "\"}";
                            } else if(isVideo != null && isVideo.trim().length() > 0 && isVideo.equals("video")){
                                Thread thread = new Thread(new ConvertVideoThread(path, onlyPath));
                                thread.start();
                                path = path.substring(0, path.indexOf(".")) + ".flv";
                                
                                json = "{ \"state\": \"SUCCESS\",\"url\": \"http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/downloadVideo.jsp?path=" + path.replaceAll("\\\\", "/") + "\",\"title\": \""
                                        + fileName + "\",\"original\": \"" + fileName + "\"}";
                            }else {
                                json = "{ \"state\": \"SUCCESS\",\"url\": \"http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/downloadImage.jsp?path=" + path.replaceAll("\\\\", "/") + "\",\"title\": \""
                                        + fileName + "\",\"original\": \"" + fileName + "\"}";
                            }
                            response.setContentType("application/json;charset=UTF-8");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().print(json);
                        } catch (Exception ex) {
                            java.util.logging.Logger.getLogger(UploadPic.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                }
            }

        } catch (FileUploadException e) {
        }
    }
*/
}
