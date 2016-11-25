package com.dlshouwen.core.base.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;

public class SendEmail {

    private static final Logger LOGGER = Logger.getLogger(SendEmail.class);

    public static void sendEmailXHD(String username, String email, String actionUrl) {
        SimpleMail sm = new SimpleMail();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String time = sdf.format(c.getTime());

        sm.setSubject("新晨阳光统一身份认证");

        String prourl = SysConfigLoader.getSystemConfig().getProperty("producturl") + actionUrl;
        String str = "<p>亲爱的用户"
                + username
                + "：您好！</p>"
                + "<p>&nbsp;&nbsp;&nbsp;&nbsp;您于"
                + time
                + "获取此邮件，你可以继续享受新晨阳光平台所提供给您的服务！</p>"
                + "<p>&nbsp;&nbsp;&nbsp;&nbsp;点此进行重置密码<a style='color:blue;' href='"
                + prourl
                + "' >"
                + prourl
                + "</a></p>"
                + " <p> &nbsp;&nbsp;&nbsp;&nbsp;(如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可。该链接使用后将立即失效。)</p>"
                + "<p>&nbsp;&nbsp;&nbsp;&nbsp;此信息为系统消息，请勿回复</p>"
                + "<p>&nbsp;&nbsp;&nbsp;&nbsp;如果不是本人操作请及时修改密码！</p>";
        sm.setContent(str);
        String sendemailName = SysConfigLoader.getSystemConfig().getProperty("sendemailName");
        String sendemailPass = SysConfigLoader.getSystemConfig().getProperty("sendemailPass");
        SimpleMailSender sms = new SimpleMailSender(sendemailName, sendemailPass);
        try {
            sms.send(email, sm);
            LOGGER.info("执行完成！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
