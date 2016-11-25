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
package com.dlshouwen.wzgl.video.controller;


import com.dlshouwen.core.base.utils.ConvertVideo;
import com.dlshouwen.core.base.utils.SpringUtils;
import com.dlshouwen.wzgl.video.dao.VideoDao;
import com.dlshouwen.wzgl.video.model.Video;
import org.apache.log4j.Logger;

/**
 * SimpleThread 单线程处理转码视频
 *
 * @author yangtong
 */
public class SimpleThread implements Runnable {

    private final Logger logger = Logger.getLogger(SimpleThread.class);

    private String localFilePath = null;

    private String toFilePath = null;

    private String videoId = null;
    
    public SimpleThread(String localFilePath, String toFilePath, String id) {
        this.localFilePath = localFilePath;
        this.toFilePath = toFilePath;
        this.videoId = id;
    }

    public void run() {
        synchronized (this) {
            try {
                String msg = ConvertVideo.process(localFilePath, toFilePath);
                if (msg.equals("error")) {
                    logger.info(localFilePath + ":" + "have error");
                } else {
                    VideoDao dao = (VideoDao) SpringUtils.getBean("videoDao");
                    Video video = new Video();
                    video.setVd_id(videoId);
                    video.setVd_status("2");
                    video.setVd_savepath(msg);
                    String imagePath = msg.substring(0, msg.indexOf(".")) + ".jpg";
                    video.setVd_thumbnailspath(imagePath);
                    dao.updatePublishVideo(video);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
