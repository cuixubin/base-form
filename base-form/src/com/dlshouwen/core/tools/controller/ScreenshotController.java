package com.dlshouwen.core.tools.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dlshouwen.core.tools.dao.ScreenshotDao;

/**
 * 截图
 * @author 大连首闻科技有限公司
 * @version 2015-10-19 09:18:36
 */
@Controller
@RequestMapping("/core/tools/screenshot")
@SuppressWarnings({ "unchecked", "deprecation" })
public class ScreenshotController {
	
	/** 功能根路径 */
	private String basePath = "core/tools/screenshot/";

	/** 数据操作对象 */
	@SuppressWarnings("unused")
	private ScreenshotDao dao;

	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="screenshotDao")
	public void setDao(ScreenshotDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 跳转到截图主页面
	 * @return basePath + 'screenshot'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String goScreenshotPage() throws Exception {
		return basePath + "screenshot";
	}
	
	/**
	 * 导出参数
	 * @param exportScreenAttrParams 参数信息
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/attr/export", method=RequestMethod.POST)
	public void exportScreenAttr(String exportScreenAttrParams, HttpServletResponse response) throws Exception {
		try {
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment;filename=screenshot.txt");
			response.getWriter().write(exportScreenAttrParams);
		} catch (IOException e) {
			response.reset();
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入参数
	 * @param importScreenAttrFile 导入的文件对象
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 * @return Ajax反馈对象
	 */
	@RequestMapping(value="/attr/import", method=RequestMethod.POST)
	public String importScreenAttr(@RequestParam(value="importScreenAttrFile", required=true) MultipartFile importScreenAttrFile, 
			Model model, HttpServletResponse response) throws Exception {
//		判断文件是否存在
		if(importScreenAttrFile==null){
			model.addAttribute("isSuccess", false);
			model.addAttribute("errorMessage", "您没有上传文件，请上传文件。");
			return goScreenshotPage();
		}
//		判断文件是否有内容
		if(importScreenAttrFile.getSize()<=0){
			model.addAttribute("isSuccess", false);
			model.addAttribute("errorMessage", "您上传的文件没有内容，请重新上传文件。");
			return goScreenshotPage();
		}
//		判断扩展名
		if(!importScreenAttrFile.getOriginalFilename().toLowerCase().endsWith(".txt")){
			model.addAttribute("isSuccess", false);
			model.addAttribute("errorMessage", "您上传的文件有误，请上传txt文件。");
			return goScreenshotPage();
		}
//		定义反馈信息
		List<Map<String, String>> screenAttrList = new ArrayList<Map<String,String>>();
//		执行遍历读取文件
		try{
			String content = new String(importScreenAttrFile.getBytes());
			for(String line : content.split("\r\n")){
				Map<String, String> screenAttr = new HashMap<String, String>();
				String[] screenArr = line.split("\t");
				screenAttr.put("sequence", screenArr[0]);
				screenAttr.put("name", screenArr[1]);
				screenAttr.put("url", screenArr[2]);
				screenAttr.put("width", screenArr[3]);
				screenAttr.put("height", screenArr[4]);
				screenAttrList.add(screenAttr);
			}
		}catch(Exception e){
			model.addAttribute("isSuccess", false);
			model.addAttribute("errorMessage", "您上传的文件内容有误，您可以在线编辑并导出，在导出的文件基础上进行修改。");
			return goScreenshotPage();
		}
//		将参数列表传递到前台
		model.addAttribute("isSuccess", true);
		model.addAttribute("errorMessage", "导入成功。");
		model.addAttribute("screenAttrList", JSONArray.fromObject(screenAttrList).toString());
		return goScreenshotPage();
	}
	
	/**
	 * 跳转到生成截图页面
	 * @return basePath + 'screenshot'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/generate", method=RequestMethod.GET)
	public String generateScreenPage() throws Exception {
		return basePath + "generateScreen";
	}
	
	/**
	 * 导出图片
	 * @param exportScreenAttrParams 参数信息
	 * @param response 响应对象
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/picture/generate", method=RequestMethod.POST)
	public void generatePicture(@RequestParam(value="pictureFile", required=true) MultipartFile pictureFile, 
			String pictureParams, HttpServletResponse response) throws Exception {
//		定义基础文件流
		FileInputStream fis = null;
		ImageInputStream iis = null;
		ZipOutputStream zos = null;
		try{
//			定义大图文件流
			iis = ImageIO.createImageInputStream(pictureFile.getInputStream());
			Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = iterator.next();
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
//			定义压缩包文件流
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=pictures.zip");
			zos = new ZipOutputStream(response.getOutputStream());
//			定义开始的位置
			int x = 0;
			int y = 0;
//			执行遍历裁图并写入到zip文件中
			List<Map<String, Object>> screenAttrList = (List<Map<String, Object>>)JSONArray.toList(JSONArray.fromObject(pictureParams), Map.class);
			for(Map<String, Object> screenAttr : screenAttrList){
				String name = MapUtils.getString(screenAttr, "name");
				int width = MapUtils.getInteger(screenAttr, "width");
				int height = MapUtils.getInteger(screenAttr, "height");
//				执行裁剪
				Rectangle rect = new Rectangle(x, y, width, height);
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				File file = new File(name+".png");
				ImageIO.write(bi, "png", file);
//				放置到压缩包中
				zos.putNextEntry(new ZipEntry(name+".png"));
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
				y += height;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			fis.close();
			iis.flush();
			iis.close();
			zos.flush();  
			zos.close();  
		}
	}
	
}