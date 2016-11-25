/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlshouwen.core.base.utils;

import com.dlshouwen.core.base.Annotation.ExportDescrip;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author cuixubin
 */
public class ExcelUtils {

    /**
     * 根据实体(具有ExportDescrip注解)字段导出只含表头信息的exl模板
     *
     * @param request
     * @param response
     * @param obj 实体对象
     * @param fileName 导出的文件名称
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse response,
            Class obj, String fileName) throws Exception {
//          设置响应头
        response.setContentType("application/vnd.ms-excel");
//          执行文件写入
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
//          获取输出流
        OutputStream outputStream = response.getOutputStream();
//          定义Excel对象
        WritableWorkbook book = Workbook.createWorkbook(outputStream);
//          创建Sheet页
        WritableSheet sheet = book.createSheet("导入数据", 0);
//          冻结表头
        SheetSettings settings = sheet.getSettings();
        settings.setVerticalFreeze(1);
//          定义表头字体样式、表格字体样式
        WritableFont headerFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
        WritableFont bodyFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.NO_BOLD);
        WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
        WritableCellFormat bodyCellFormat = new WritableCellFormat(bodyFont);
//          设置表头样式：加边框、背景颜色为淡灰、居中样式
        headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        headerCellFormat.setBackground(Colour.PALE_BLUE);
        headerCellFormat.setAlignment(Alignment.CENTRE);
        headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        headerCellFormat.setWrap(true);
//          设置表格体样式：加边框、居中、自动换行
        bodyCellFormat.setAlignment(Alignment.CENTRE);
        bodyCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        bodyCellFormat.setWrap(true);
//          默认单元格宽度
        sheet.getSettings().setDefaultColumnWidth(20);
//          允许录入表记录数
        String recordNumberStr = AttributeUtils.getAttributeContent(request.getServletContext(), "exl_import_record_number");
        int recordNumber = 500;
        try {
            recordNumber = Integer.parseInt(recordNumberStr);
        } catch (Exception e) {
            recordNumber = 500;
        }
        for(int i=0; i<recordNumber; i++) {
            sheet.setRowView(i, 290, false);
        }
//          判断一下表头数组是否有数据
        if (null != obj) {
//          列标记
            int seq = 0;
            //获取实体的所有属性字段
            Field[] field = obj.getDeclaredFields();
            
            //下拉列表可选数据存放位置起始纵坐标和横坐标
            int selDataYIndex = 1;
            int selDataXIndex = field.length + 5;
            
            for (int i = 0; i < field.length; i++) {
                //根据注解，判断属性字段是否可作为表头
                boolean flag = field[i].isAnnotationPresent(ExportDescrip.class);
                if (flag) {
                    //字段名称
                    String name = field[i].getAnnotation(ExportDescrip.class).fname();
                    //字段关联属性标识，""不关联；"0"关联其他实体；"codename"关联码表
                    String connecType = field[i].getAnnotation(ExportDescrip.class).connecType();
                    //所关联实体的dao路径
                    String daoPath = field[i].getAnnotation(ExportDescrip.class).daoPath();
                    //所关联dao的方法名称
                    String methodInDao = field[i].getAnnotation(ExportDescrip.class).methodInDao();

                    if ("".equals(connecType)) {//普通字段
                        Label label = new Label(seq, 0, name, headerCellFormat);
                        sheet.addCell(label);
                        for(int m=1; m<=recordNumber; m++) {
                            Label lb = new Label(seq, m, null, bodyCellFormat);
                            sheet.addCell(lb);
                        }
                        seq++;
                    } else if ("0".equals(connecType)) {//关联实体字段
                        //获取关联实体数据
                        Class c = Class.forName(daoPath);
                        Method m = c.getDeclaredMethod(methodInDao);
                        List<String> choseDataList = (List<String>) m.invoke(SpringUtils.getBean(c));
                        if (choseDataList != null && choseDataList.size() > 0) {
                            Label label = new Label(seq, 0, name, headerCellFormat);
                            sheet.addCell(label);
                            
                            int selY = selDataYIndex;
                            //将可选数据写入excel
                            for(int ii=0; ii< choseDataList.size(); ii++) {
                                String temStr[] = choseDataList.get(ii).split("____");
                                
                                if(null != temStr && temStr.length > 1) {
                                    String selName = temStr[0], selId = temStr[1];
                                    Label selLabel = new Label(selDataXIndex, selY, selId, headerCellFormat);
                                    sheet.addCell(selLabel);
                                    selLabel = new Label((selDataXIndex+1), selY, 
                                            selName+":"+selDataXIndex+","+(selY++), headerCellFormat);
                                    sheet.addCell(selLabel);
                                }
                            }
                            //添加选择输入单元格
                            addColumnList(sheet, ++selDataXIndex, selDataYIndex, --selY, seq, recordNumber, bodyCellFormat);
                            selDataXIndex++;
                            seq++;
                        }
                    } else {
                        //获取关联码表的所有可选数据
                        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
                        dataMap = CodeTableUtils.getValues(request.getServletContext(), connecType);
                        if (null != dataMap && dataMap.size() > 0) {
                            int selY = selDataYIndex;
                            Label label = new Label(seq, 0, name, headerCellFormat);
                            sheet.addCell(label);
                            
                            for (String key : dataMap.keySet()) {
                                Label selLabel = new Label(selDataXIndex, selY++, 
                                            dataMap.get(key).toString()+":"+key, headerCellFormat);
                                sheet.addCell(selLabel);
                            }
                            //添加选择输入单元格
                            addColumnList(sheet, selDataXIndex, selDataYIndex, --selY, seq, recordNumber, bodyCellFormat);
                            selDataXIndex++;
                            seq++;
                        }
                    }
                }
            }
//              写入Excel工作表
            book.write();
//              关闭Excel工作薄对象
            book.close();
//              关闭流
            outputStream.flush();
            outputStream.close();
            outputStream = null;
        }
    }

    /**
     * 生成选择输入单元格
     *
     * @param sheet 表格
     * @param contentList 单元格可选值字符数组
     * @param rowIndex 列号
     * @param recordNumber 指定生成多少行单元格
     * @return
     * @throws Exception
     */
    private static void addColumnList(WritableSheet sheet,
        List<String> contentList, int rowIndex, int recordNumber, WritableCellFormat wc) throws Exception {
        for (int j = 0; j < recordNumber;) {
            WritableCellFeatures wcf = new WritableCellFeatures();
            wcf.setDataValidationList(contentList);
            Blank b = new Blank(rowIndex, ++j, wc);
            b.setCellFeatures(wcf);
            sheet.addCell(b);
        }
    }

    /**
     * 生成选择输入单元格
     *
     * @param sheet 表格
     * @return
     * @throws Exception
     */
    private static void addColumnList(WritableSheet sheet, int colNumber, 
            int rowStart, int rowEnd, int rowIndex, int recordNumber, WritableCellFormat wc) throws Exception {
        for (int j = 0; j < recordNumber;) {
            WritableCellFeatures wcf = new WritableCellFeatures();
            wcf.setDataValidationRange(colNumber,rowStart,colNumber,rowEnd);
            Blank b = new Blank(rowIndex, ++j, wc);
            b.setCellFeatures(wcf);
            sheet.addCell(b);
        }
    }
    
    /**
     * 读取Exl文件返回数据的List结果集，集合一个元素为标题集合
     *
     * @param filePath 文件路径
     * @param titleRow 标题行（默认为第0行）
     * @param cls 数据表对应的实体类
     * @param args 约束字段
     * @return 由表数据转换出的实体集合
     */
    public static List<Object> readExlData(String filePath, Integer titleRow, Class cls, String... args) throws Exception {
        List<Object> rlist = new ArrayList<Object>();
        Workbook book = null;
        try {
            //打开文件  
            book = Workbook.getWorkbook(new File(filePath));
            //取得第一个sheet  
            Sheet sheet = book.getSheet(0);
            //取得标题
            if (null == titleRow) {
                titleRow = 0;
            }
            Map<String, String> titleMap = new HashMap<String, String>();
            Cell[] tcell = sheet.getRow(titleRow);
            for (int i = 0; i < tcell.length; i++) {
                titleMap.put(sheet.getCell(i, titleRow).getContents(), i + "");
            }

            //有标题才读取数据
            if (titleMap.size() > 0) {

                //存放每一行数据，从标题行的下一行开始取数据
                for (int rowNumber = titleRow + 1; rowNumber < sheet.getRows(); rowNumber++) {
                    if (validPass(args, titleMap, rowNumber, sheet)) {
                        Iterator<Map.Entry<String, String>> entries = titleMap.entrySet().iterator();
                        Object obj = cls.newInstance();
                        Field[] fields = cls.getDeclaredFields();
                        while (entries.hasNext()) {
                            Entry<String, String> it = entries.next();
                            //列号
                            int colNumber = Integer.parseInt(it.getValue());
                            //标题
                            String title = it.getKey();
                            //单元格数据
                            String cellData = sheet.getCell(colNumber, rowNumber).getContents();
                            if(cellData.indexOf("____") != -1) {
                                cellData = cellData.split("____")[1];
                            }else if(cellData.indexOf(":") != -1) {
                                cellData = cellData.split(":")[1];
                                //判断是否是坐标数据
                                if(cellData.indexOf(",") != -1) {
                                    int x = Integer.parseInt(cellData.split(",")[0]);
                                    int y = Integer.parseInt(cellData.split(",")[1]);
                                    cellData = sheet.getCell(x, y).getContents();
                                }
                            }
                            
                            for(Field f: fields){
                                if(f.isAnnotationPresent(ExportDescrip.class)){
                                    String annoName = f.getAnnotation(ExportDescrip.class).fname();
                                    if(annoName!= null &&annoName.trim().length()>0&&annoName.equals(title)){
                                        f.setAccessible(true);
                                        String type = f.getType().toString();
                                        if(type.indexOf("String")!=-1){
                                            f.set(obj, cellData);
                                        }else if(type.indexOf("Date")!=-1) {
                                            Cell cell = sheet.getCell(colNumber, rowNumber);
                                            Date date = new Date();
                                            if (cell.getType() == CellType.DATE) { 
                                                DateCell dc = (DateCell) cell;
                                                date = dc.getDate(); 
                                            }
                                            f.set(obj, date);
                                        }else if(type.indexOf("int")!=-1 || type.indexOf("Integer") != -1) {
                                            Integer d = null;
                                            try{
                                                d = Integer.parseInt(cellData);
                                            }catch(Exception e) {}
                                            f.set(obj, d);
                                        }else if(type.indexOf("float")!=-1 || type.indexOf("Float") != -1) {
                                            Float d = null;
                                            try{
                                                d = Float.parseFloat(cellData);
                                            }catch(Exception e) {}
                                            f.set(obj, d);
                                        }else if(type.indexOf("double")!=-1 || type.indexOf("Double") != -1) {
                                            Double d = null;
                                            try{
                                                d = Double.parseDouble(cellData);
                                            }catch(Exception e) {}
                                            f.set(obj, d);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        rlist.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭文件  
            if (null != book) {
                book.close();
            }
        }

        return rlist;
    }
    
    /**
     * 判断表格某一行的必填列是否有只值
     * @param args 必填列标题
     * @param titleMap
     * @param rowNumber
     * @param sheet
     * @return 某一行所有必填列都有值时返回true
     */
    private static boolean validPass(String[] args, Map<String, String> titleMap, int rowNumber, Sheet sheet) {
        boolean flag = true;
        if (args.length > 0) {
            for (String s : args) {
                if(titleMap.containsKey(s)){
                    Integer cols = Integer.valueOf(titleMap.get(s));
                    String content = sheet.getCell(cols,rowNumber).getContents();
                    if("".equals(content)){
                        flag = false;
                        break;
                    }
                }else{
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
}
