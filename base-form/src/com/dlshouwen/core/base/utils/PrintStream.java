/*
 *XCYG TECHNOLOGY CO., LTD. PROPRIETARY INFORMATION
 * 
 * This software is supplied under the terms of a license agreement or
 * nondisclosure agreement with Mylab Healthcare Technology Co., Ltd. and may not 
 * be copied or disclosed except in accordance with the terms of that agreement.
 * 
 * Copyright (c) 2015-present XCYG Technology Co., Ltd. All Rights Reserved.
 * 
 * 版权所有 (c) 新晨阳光科技（北京）有限公司
 * 
 */
package com.dlshouwen.core.base.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * PrintStream输入流进程处理
 *
 * @author yangtong
 */
public class PrintStream extends Thread {
    private Logger logger = Logger.getLogger(PrintStream.class);
    private InputStream is = null;
    
    public PrintStream(InputStream is) {
        this.is = is;
    }
    
    @Override
    public void run(){
        try{
            while(this != null){
                int ch = is.read();
                if(ch != -1){
                    logger.info("system out"+(char)ch);
                }else{
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(PrintStream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
