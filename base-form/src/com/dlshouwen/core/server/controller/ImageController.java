package com.dlshouwen.core.server.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dlshouwen.core.base.config.CONFIG;
import com.dlshouwen.core.base.context.SessionContext;
import com.dlshouwen.core.base.model.OperationType;
import com.dlshouwen.core.base.model.SessionUser;
import com.dlshouwen.core.base.utils.LogUtils;

/**
 * 图片服务器
 * @author 大连首闻科技有限公司
 * @version 2013-11-20 14:24:09
 */
@Controller
@RequestMapping("/core/server/image")
public class ImageController {
	
	/** 功能根路径 */
	private String basePath = "core/server/image/";

	/**
	 * 跳转到图片主页面
	 * @param model 反馈对象
	 * @param request 请求对象
	 * @return basePath + 'imageMain'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String main(Model model, HttpServletRequest request) throws Exception {
//		根路径传递到前台
		model.addAttribute("previewPath", (CONFIG.HOST+request.getContextPath()+File.separator+CONFIG.IMAGE_PREVIEW_PATH).replaceAll("\\\\", "/"));
		model.addAttribute("rootPath", CONFIG.IMAGE_ROOT_PATH.replaceAll("\\\\", "/"));
//		获取参数：图片上传格式限制、图片上传大小限制
		model.addAttribute("upload_pic_pattern", CONFIG.UPLOAD_PIC_PATTERN);
		model.addAttribute("upload_pic_max_size", CONFIG.UPLOAD_PIC_MAX_SIZE);
//		传递是否选择
		model.addAttribute("isSelect", "0");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问图片管理页面");
		return basePath+"imageMain";
	}
	
	/**
	 * 跳转到选择图片主页面
	 * @param model 反馈对象
	 * @param request 请求对象
	 * @return basePath + 'imageMain'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/select", method=RequestMethod.GET)
	public String select(Model model, HttpServletRequest request) throws Exception {
//		根路径传递到前台
		model.addAttribute("previewPath", (CONFIG.HOST+request.getContextPath()+File.separator+CONFIG.IMAGE_PREVIEW_PATH).replaceAll("\\\\", "/"));
		model.addAttribute("rootPath", CONFIG.IMAGE_ROOT_PATH.replaceAll("\\\\", "/"));
//		获取参数：图片上传格式限制、图片上传大小限制
		model.addAttribute("upload_pic_pattern", CONFIG.UPLOAD_PIC_PATTERN);
		model.addAttribute("upload_pic_max_size", CONFIG.UPLOAD_PIC_MAX_SIZE);
//		传递是否选择
		model.addAttribute("isSelect", "1");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.VISIT, "访问选择图片页面");
		return basePath+"selectImage";
	}
	
	/**
	 * 获取图片列表
	 * @param filePath 文件路径
	 * @param request 请求对象
	 * @return 图片列表
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> list(String filePath, HttpServletRequest request) throws Exception {
//		定义变量存放文件列表
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
//		获取真实路径
//		String realPath = request.getSession().getServletContext().getRealPath("/") + File.separator + CONFIG.IMAGE_ROOT_PATH + filePath;
                String realPath = request.getSession().getServletContext().getRealPath("/");
                realPath = realPath.substring(0, realPath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_PIC_PATH;

//		读取文件夹列表
		File baseFile = new File(realPath);
		File[] files =  baseFile.listFiles();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(files != null) {
                    for(File file : files){
//                          获取文件名、扩展名、是否是路径、大小、创建时间放置在文件列表中
                            Map<String, Object> fileInfo = new HashMap<String, Object>();
                            fileInfo.put("name", this.getFileName(file.getName(), file.isDirectory()));
                            fileInfo.put("full_name", file.getName());
                            fileInfo.put("extension", this.getFileExtension(file.getName()));
                            fileInfo.put("is_dir", file.isDirectory()?"1":"0");
                            fileInfo.put("size", file.isDirectory()?0L:file.length());
                            fileInfo.put("create_time_long", file.lastModified());
                            fileInfo.put("create_time", sdf.format(file.lastModified()));
                            fileList.add(fileInfo);
                    }
                }
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.SEARCH, "获取图片列表");
		return fileList;
	}
	
	/**
	 * 新增文件夹
	 * @param nowPath 当前路径
	 * @param fileName 文件名称
	 * @param request 请求对象
	 * @return 反馈对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(String nowPath, String fileName, HttpServletRequest request) throws Exception {
//		定义返回信息
		Map<String, Object> resultInfo = new HashMap<String, Object>();
//		新增文件夹
		String realPath = request.getSession().getServletContext().getRealPath("/") + CONFIG.IMAGE_ROOT_PATH + nowPath + fileName;
		File newFile = new File(realPath);
		if(!newFile.mkdir()){
			resultInfo.put("success", "0");
			StringBuffer message = new StringBuffer();
			message.append("文件夹新增失败，可能的原因：<br /><br />");
			message.append("　　1. 文件名重复。<br />");
			message.append("　　2. 文件名为空。<br />");
			message.append("　　3. 文件名不符合正规文件命名规范。");
			resultInfo.put("message", message.toString());
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.INSERT, "新增文件夹（新增失败，文件夹名称："+fileName+"，错误原因："+message.toString()+"）");
			return resultInfo;
		}
//		处理返回信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		resultInfo.put("success", "1");
		resultInfo.put("message", "文件夹新增成功。");
		resultInfo.put("name", fileName);
		resultInfo.put("full_name", fileName);
		resultInfo.put("extension", "");
		resultInfo.put("is_dir", "1");
		resultInfo.put("size", 0L);
		resultInfo.put("create_time_long", newFile.lastModified());
		resultInfo.put("create_time", sdf.format(newFile.lastModified()));
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.INSERT, "新增文件夹，文件夹名称："+fileName);
		return resultInfo;
	}
	
	/**
	 * 编辑文件名
	 * @param nowPath 当前路径
	 * @param nowFile 当前文件
	 * @param fileName 文件名称
	 * @param request 请求对象
	 * @return 反馈对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/rename", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rename(String nowPath, String nowFile, String fileName, 
			HttpServletRequest request) throws Exception {
//		定义返回信息
		Map<String, Object> resultInfo = new HashMap<String, Object>();
//		获取需要编辑的文件
		String realPath = request.getSession().getServletContext().getRealPath("/") + CONFIG.IMAGE_ROOT_PATH + nowPath + nowFile;
		File file = new File(realPath);
		String renameRealPath = request.getSession().getServletContext().getRealPath("/") + CONFIG.IMAGE_ROOT_PATH + nowPath + fileName;
//		判断是否需要扩展名
		if(!file.isDirectory()){
			renameRealPath += "." + this.getFileExtension(file.getName());
		}
//		执行编辑
		File renameFile = new File(renameRealPath);
		if(!file.renameTo(renameFile)){
			resultInfo.put("success", "0");
			StringBuffer message = new StringBuffer();
			message.append("文件夹重命名失败，可能的原因：<br /><br />");
			message.append("　　1. 当前文件夹有其他文件文件名重复。<br />");
			message.append("　　2. 文件名为空。<br />");
			message.append("　　3. 文件名不符合正规文件命名规范。");
			resultInfo.put("message", message.toString());
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "重命名文件夹（重命名失败，文件夹名称："+fileName+"，错误原因："+message.toString()+"）");
			return resultInfo;
		}
//		返回信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		resultInfo.put("success", "1");
		resultInfo.put("message", "文件重命名成功。");
		resultInfo.put("name", this.getFileName(renameFile.getName(), renameFile.isDirectory()));
		resultInfo.put("full_name", renameFile.getName());
		resultInfo.put("extension", this.getFileExtension(renameFile.getName()));
		resultInfo.put("is_dir", renameFile.isDirectory()?"1":"0");
		resultInfo.put("size", renameFile.isDirectory()?0L:renameFile.length());
		resultInfo.put("create_time_long", renameFile.lastModified());
		resultInfo.put("create_time", sdf.format(renameFile.lastModified()));
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "重命名文件夹，文件名由【"+nowFile+"】修改为【"+fileName+"】");
		return resultInfo;
	}
	
	/**
	 * 删除文件
	 * @param nowPath 当前路径
	 * @param nowFile 当前文件
	 * @param request 请求对象
	 * @return 反馈对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editResource(String nowPath, String nowFile, HttpServletRequest request) throws Exception {
//		定义返回信息
		Map<String, Object> resultInfo = new HashMap<String, Object>();
//		执行删除文件
		String realPath = request.getSession().getServletContext().getRealPath("/") + CONFIG.IMAGE_ROOT_PATH + nowPath + nowFile;
		File file = new File(realPath);
		if(!file.delete()){
			resultInfo.put("success", "0");
			StringBuffer message = new StringBuffer();
			message.append("文件夹删除失败，可能的原因：<br /><br />");
			message.append("　　1. 当前文件目录同服务器目录不同步，请尝试刷新。<br />");
			message.append("　　2. 当前文件已经被删除，请尝试刷新。<br />");
			message.append("　　3. 当前文件夹中包含文件，请删除文件夹内所有文件后执行删除。");
			resultInfo.put("message", message.toString());
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.DELETE, "删除图片（删除失败，文件名称："+nowFile+"，错误原因："+message.toString()+"）");
			return resultInfo;
		}
		resultInfo.put("success", "1");
		resultInfo.put("message", "文件删除成功。");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.DELETE, "删除图片，文件名称："+nowFile);
		return resultInfo;
	}
	
	/**
	 * 上传图片
	 * @param nowPath 当前路径
	 * @param sessionId 会话编号
	 * @param userId 用户编号
	 * @param Filedata 文件对象
	 * @param request 请求对象
	 * @return ajax响应回执
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ResponseBody
	public String upload(String nowPath, String sessionId, String userId,
			MultipartFile Filedata, HttpServletRequest request) throws Exception {
//		定义返回参数
		Map<String, Object> resultInfo = new HashMap<String, Object>();
//		校验Session是否有效
		HttpSession projectSession = SessionContext.getInstance().getSession(sessionId);
		if(projectSession==null){
			resultInfo.put("success", "0");
			resultInfo.put("message", "您的会话已超时，请重新登录");
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "上传图片（上传错误，文件名："+Filedata.getOriginalFilename()+"，错误原因：会话超时");
			return JSONObject.fromObject(resultInfo).toString();
		}
//		检验用户是否匹配
		String projectUserId = ((SessionUser)projectSession.getAttribute(CONFIG.SESSION_USER)).getUser_id();
		if(!projectUserId.equals(userId)){
			resultInfo.put("success", "0");
			resultInfo.put("message", "校验授权信息失败，请检查");
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.UPDATE, "上传图片（上传错误，文件名："+Filedata.getOriginalFilename()+"，错误原因：校验授权信息失败");
			return JSONObject.fromObject(resultInfo).toString();
		}
//		执行文件写入
//              String imgFilePath = request.getSession().getServletContext().getRealPath("/") + CONFIG.IMAGE_ROOT_PATH;

                String imgFilePath = request.getSession().getServletContext().getRealPath("/");
                imgFilePath = imgFilePath.substring(0, imgFilePath.lastIndexOf(File.separator)) + CONFIG.UPLOAD_PIC_PATH;

		String imgFullPath = imgFilePath + nowPath + Filedata.getOriginalFilename();
		File dirFile = new File(imgFilePath);
                File imgFile = null;
                if(!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                imgFile = new File(imgFullPath);
                
                if(!imgFile.exists()){
                    imgFile.createNewFile();
                    OutputStream os = new FileOutputStream(imgFile);
                    try {
                        os.write(Filedata.getBytes());
                    } catch(Exception e) {
                        e.printStackTrace();
                    } finally {
                        os.close();
                    }
                }else{
                        resultInfo.put("success", "0");
                        resultInfo.put("message", "文件名称重复，请检查");
//			记录操作日志
                        LogUtils.updateOperationLog(request, OperationType.UPDATE, "上传图片（上传错误，文件名："+Filedata.getOriginalFilename()+"，错误原因：文件名称重复");
                        return JSONObject.fromObject(resultInfo).toString();
                }
		resultInfo.put("success", "1");
		resultInfo.put("message", "文件上传成功。");
//		记录操作日志
		LogUtils.updateOperationLog(request, OperationType.UPDATE, "上传图片，文件名："+Filedata.getOriginalFilename());
		return JSONObject.fromObject(resultInfo).toString();
	}
	
//	=====================================
//	文件处理方法
//	=====================================
	
	/**
	 * 获取文件名
	 */
	private String getFileName(String fullName, boolean isDirectory){
		String fileName = "";
		if(isDirectory){
			fileName = fullName.substring(fullName.lastIndexOf(File.separator)+1, fullName.length());
		}else{
			fileName = fullName.substring(fullName.lastIndexOf(File.separator)+1, fullName.lastIndexOf("."));
		}
		return fileName;
	}
	
	/**
	 * 获取文件扩展名
	 */
	private String getFileExtension(String fullName){
		return fullName.substring(fullName.lastIndexOf(".")+1, fullName.length());
	}
	
}