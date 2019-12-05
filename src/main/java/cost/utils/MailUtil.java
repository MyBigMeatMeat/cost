package cost.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2019/4/23.
 */
public class MailUtil {

    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    /*private static String host = "smtp.exmail.qq.com";
    private static String account = "matrix@sofund.com";
    private static String password = "Matrix2345";
    private static String output = "D:\\ImageMail.eml";*/
    private static String host = "smtp.msfgroup.com";
    private static String account = "re@msfgroup.com";
    private static String password = "Hexin135";
    private static String output = "D:\\ImageMail.eml";

    /**
     * @param args
     * @throws Exception
     */
    /*public void main(String[] args) throws Exception {

        Properties prop = new Properties();
        prop.setProperty("mail.host", host);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(host, account, password);
        //4、创建邮件
        Message message = createSimpleMail(session);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }*/

    /**
     * 创建一封只包含文本的邮件
     * @param email     收件人地址
     * @param subject   邮件标题
     * @param content   邮件内容
     * @return
     * @throws Exception
     */
    public static Boolean createSimpleMail(String email, String subject, String content){

        logger.info("只包含文本的邮件发送中......");

        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.port", "465");
            prop.setProperty("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.ssl.enable", true);
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(host, account, password);

            //4、创建邮件
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(account));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //邮件的标题
            message.setSubject(subject);
            //邮件的文本内容
            message.setContent(content, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e){
            logger.info("邮件发送出现异常");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 生成一封邮件正文带图片的邮件
     * @param email     收件人邮箱
     * @param subject   邮件标题
     * @param content   邮件内容
     * @param input     图片地址
     * @param fileName  图片名称
     * @throws Exception
     */
    public static void createImageMail(String email, String subject, String content,
                                       String input, String fileName){

        logger.info("正文带图片的邮件发送中......");
        try{
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(host, account, password);

            //4、创建邮件
            MimeMessage message = new MimeMessage(session);
            // 设置邮件的基本信息
            //发件人
            message.setFrom(new InternetAddress(account));
            //收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //邮件标题
            message.setSubject(subject);

            // 准备邮件数据
            // 准备邮件正文数据
            MimeBodyPart text = new MimeBodyPart();
            //text.setContent("这是一封邮件正文带图片<img src='cid:xxx.jpg'>的邮件", "text/html;charset=UTF-8");
            text.setContent(content+"<br><img src='cid:" + fileName + "'>", "text/html;charset=UTF-8");
            // 准备图片数据
            MimeBodyPart image = new MimeBodyPart();
            //DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
            DataHandler dh = new DataHandler(new FileDataSource(input));
            image.setDataHandler(dh);
            //image.setContentID("xxx.jpg");
            image.setContentID(fileName);
            // 描述数据关系
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text);
            mm.addBodyPart(image);
            mm.setSubType("related");

            message.setContent(mm);
            message.saveChanges();
            //将创建好的邮件写入到E盘以文件的形式进行保存
            //message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
            message.writeTo(new FileOutputStream(output));
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e){
            logger.info("邮件发送出现异常");
            e.printStackTrace();
        }
    }

    /**
     * 创建一封带附件的邮件
     * @param email     收件人地址
     * @param subject   邮件标题
     * @param content   邮件内容
     * @param input     附件地址
     * @throws Exception
     */
    public static void createAttachMail(String email, String subject, String content, String input){

        logger.info("带附件的邮件发送中......");

        try{
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(host, account, password);

            //4、创建邮件
            MimeMessage message = new MimeMessage(session);

            //设置邮件的基本信息
            //发件人
            message.setFrom(new InternetAddress(account));
            //收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //邮件标题
            message.setSubject(subject);

            //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(content, "text/html;charset=UTF-8");

            //创建邮件附件
            MimeBodyPart attach = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(input));
            attach.setDataHandler(dh);
            attach.setFileName(dh.getName());  //

            //创建容器描述数据关系
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(text);
            mp.addBodyPart(attach);
            mp.setSubType("mixed");

            message.setContent(mp);
            message.saveChanges();
            //将创建的Email写入到E盘存储
            message.writeTo(new FileOutputStream(output));
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e){
            logger.info("邮件发送出现异常");
            e.printStackTrace();
        }
    }

    /**
     * 生成一封带附件和带图片的邮件
     * @param email     收件人邮箱
     * @param subject   邮件标题
     * @param content   邮件内容
     * @param input     图片地址
     * @param input1    附件1地址
     * @param input2    附件2地址  没有传null
     * @throws Exception
     */
    public static void createMixedMail(String email, String subject, String content, String input, String fileName, String input1, String input2){

        logger.info("带附件和带图片的邮件发送中......");

        try{

            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(host, account, password);

            //4、创建邮件
            MimeMessage message = new MimeMessage(session);

            //设置邮件的基本信息
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);

            //正文
            MimeBodyPart text = new MimeBodyPart();
            //text.setContent("xxx这是女的xxxx<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
            text.setContent(content+"<br><img src='cid:" + fileName + "'>", "text/html;charset=UTF-8");

            //图片
            MimeBodyPart image = new MimeBodyPart();
            image.setDataHandler(new DataHandler(new FileDataSource(input)));
            image.setContentID(fileName);

            //附件1
            MimeBodyPart attach = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(input1));
            attach.setDataHandler(dh);
            attach.setFileName(dh.getName());

            //附件2
            MimeBodyPart attach2 = new MimeBodyPart();
            if(!StringUtils.isEmpty(input2)){
                DataHandler dh2 = new DataHandler(new FileDataSource(input2));
                attach2.setDataHandler(dh2);
                attach2.setFileName(MimeUtility.encodeText(dh2.getName()));
            }


            //描述关系:正文和图片
            MimeMultipart mp1 = new MimeMultipart();
            mp1.addBodyPart(text);
            mp1.addBodyPart(image);
            mp1.setSubType("related");

            //描述关系:正文和附件
            MimeMultipart mp2 = new MimeMultipart();
            mp2.addBodyPart(attach);
            if(!StringUtils.isEmpty(input2)){
                mp2.addBodyPart(attach2);
            }

            //代表正文的bodypart
            MimeBodyPart msg = new MimeBodyPart();
            msg.setContent(mp1);
            mp2.addBodyPart(msg);
            mp2.setSubType("mixed");

            message.setContent(mp2);
            message.saveChanges();

            message.writeTo(new FileOutputStream(output));
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();

        }catch (Exception e){
            logger.info("邮件发送出现异常");
            e.printStackTrace();
        }
    }
}