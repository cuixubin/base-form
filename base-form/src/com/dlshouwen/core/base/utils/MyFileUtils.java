/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author sunxingwu
 */
public class MyFileUtils {

    public static BufferedReader bufread;
    private static String readStr = "";

    public static boolean readTxtFile(String fileName, String checkStr) {
        InputStream ris = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/"+fileName);
        try {
            bufread = new BufferedReader(new InputStreamReader(ris, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            String read;
            while ((read = bufread.readLine()) != null) {
                readStr = readStr + read + "\n";
                if (checkStr.indexOf(read) >= 0) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void deleteFile(Object file_obj) {
        File file = null;
        try {
            file = getFile(file_obj);
            if ((file != null) && (file.isFile())) {
                file.delete();
            }
        } catch (Exception e) {
            String filePath = file == null ? null : file.getAbsolutePath();
            MyLogUtils.logError("删除文件异常,文件路径=" + filePath, e);
        }
    }

    public static long getFileSize(Object file_obj) {
        String filePath = null;
        if (file_obj != null) {
            FileInputStream fis = null;
            try {
                if ((file_obj instanceof File)) {
                    File file = (File) file_obj;
                    filePath = file == null ? null : file.getAbsolutePath();
                    fis = new FileInputStream(file);
                } else if ((file_obj instanceof String)) {
                    fis = new FileInputStream(filePath = (String) file_obj);
                }
                if (fis != null) {
                    return fis.available();
                }
            } catch (Exception e) {
                MyLogUtils.logError("获得文件字节大小异常,文件路径=" + filePath, e);
                return 0L;
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (Exception e) {
                    MyLogUtils.logError("获得文件字节大小-关闭文件流异常,文件路径=" + filePath, e);
                }
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                MyLogUtils.logError("获得文件字节大小-关闭文件流异常,文件路径=" + filePath, e);
            }
        }
        return 0L;
    }

    public static byte[] getBytesFromFile(Object file_obj) {
        int size_0 = 8192;
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        String filePath = null;
        try {
            File file = getFile(file_obj);
            if ((file == null) || (!file.isFile())) {
                return null;
            }
            filePath = file.getAbsolutePath();
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream(size_0);
            byte[] b = new byte[size_0];
            int readed = -1;
            while ((readed = in.read(b)) != -1) {
                if (readed < size_0) {
                    byte[] b2 = new byte[readed];
                    System.arraycopy(b, 0, b2, 0, readed);
                    out.write(b2);
                } else {
                    out.write(b);
                }
                out.flush();
            }
            return out.toByteArray();
        } catch (IOException e) {
            MyLogUtils.logError("得到文件的字节数组异常,文件路径=" + filePath, e);
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (Exception e2) {
                MyLogUtils.logError("得到文件的字节数组-关闭文件流异常,文件路径=" + filePath, e2);
            }
        }
    }

    public static File getFile(Object file_obj) {
        if (file_obj == null) {
            return null;
        }
        File file = null;
        if ((file_obj instanceof File)) {
            file = (File) file_obj;
        } else if ((file_obj instanceof String)) {
            file = new File((String) file_obj);
        }
        return file;
    }

    public static File createFile(Object file_obj) {
        File file = getFile(file_obj);
        if (file != null) {
            File parentFile = file.getParentFile();
            if ((parentFile != null) && (!parentFile.exists())) {
                parentFile.mkdirs();

                return file;
            }
        }
        return file;
    }

    private static void clearDownloadHeader(HttpServletResponse response) {
        if (response != null) {
            response.setHeader("Content-Disposition", null);
            response.setHeader("Content-Length", null);
            response.setContentType("text/html;charset=UTF-8");
        }
    }

    public static List<File> getFileName(List<File> lists, String filePath) {
        File file = new File(filePath);
        System.out.println(file.isFile() + "/" + file.isDirectory() + "/"
                + file.getPath());
        File[] fileList = file.listFiles();
        System.out.println(fileList);
        if (fileList == null) {
            return null;
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                lists.add(fileList[i]);
            } else {
                lists = getFileName(lists, fileList[i].getPath());
            }
        }
        return lists;
    }

    public static File getLastFile(String filePath) {
        List<File> list = new ArrayList();
        getFileName(list, filePath);
        File file = null;
        long max = 0L;
        for (int i = 0; i < list.size(); i++) {
            long lastupdatetime = ((File) list.get(i)).lastModified();
            if (max < lastupdatetime) {
                max = lastupdatetime;
                file = (File) list.get(i);
            }
        }
        return file;
    }

    public static String encodeDownloadFilename(String filename)
            throws IOException {
        filename = new BASE64Encoder().encodeBuffer(filename.getBytes("utf-8"));
        filename = filename.replaceAll("\r\n", "");
        filename = filename.replaceAll("\\+", "_");
        return filename;
    }

    public static String decodeDownloadFilename(String filename)
            throws IOException {
        filename = filename.replace("_", "+");
        filename = new String(new BASE64Decoder().decodeBuffer(filename), "utf-8");
        return filename;
    }

    public static String encodeNum(String proCodeId) {
        return Long.valueOf(proCodeId).longValue() * 99L + 123L + "";
    }

    public static String decodeNum(String base64ProCodeId) {
        return (Long.valueOf(base64ProCodeId).longValue() - 123L) / 99L + "";
    }

    public static void copyFile(String a, String b) {
        File file = new File(a);
        if (!file.exists()) {
            System.out.println(a + "  Not Exists. ");
            return;
        }
        File fileb = new File(b);
        if (file.isFile()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(file);
                fos = new FileOutputStream(fileb);
                byte[] bb = new byte[(int) file.length()];
                fis.read(bb);
                fos.write(bb);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    fis.close();
                    fos.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            } finally {
                try {
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.isDirectory()) {
            if (!fileb.exists()) {
                fileb.mkdir();
            }
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                copyFile(a + "\\" + fileList[i], b + "\\" + fileList[i]);
            }
        }
    }
    
    /**
     * 上传文件到指定目录，返回上传成功后的文件全路径
     * @param request
     * @param fname 前端jsp页面input的name
     * @param savePath 指定要保存的文件地址
     * @return 
     */
    public static String uploadFile(HttpServletRequest request, String fname, String savePath) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile(fname);
        String fileName = multipartFile.getOriginalFilename();
        String filePath = "";
        if (fileName.trim().length() > 0) {
            int pos = fileName.lastIndexOf(".");
            fileName = fileName.substring(pos);
            String path = SysConfigLoader.getSystemConfig().getProperty("fileTempPath");
            if(null != savePath) {
                path = savePath;
            }
            Date date = new Date();
            fileName = String.valueOf(date.getTime()) + fileName;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + "/" + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
            path = path + "/" + fileName;
            FileOutputStream fos = null;
            InputStream s = null;
            try {
                fos = new FileOutputStream(file);
                s = multipartFile.getInputStream();
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = s.read(buffer)) != -1) {
                    fos.write(buffer, 0, read);
                }
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (s != null) {
                        s.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            path = path.replaceAll("\\\\", "/");
            filePath = path;
        }
        return filePath;
    }
    
    public static void main(String[] args)
            throws IOException {
        readTxtFile("sendContextBlackDictionary.txt", "123");
    }
}
