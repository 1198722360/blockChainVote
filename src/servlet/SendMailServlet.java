package servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet("/sendMailServlet")
public class SendMailServlet extends HttpServlet//授权码tighqxsmmhbkgiic
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String checkCode=this.sendCheckCode(req.getParameter("username"));
        new BaseServlet().writeValue(checkCode,resp);
    }

    public String sendCheckCode(String mail)
    {
        String checkCode="";
        for (int i=0;i<6;i++)
        {
            checkCode+=new Random().nextInt(9);
        }
        String from="1198722360@qq.com";
        String to=mail;
        String host = "smtp.qq.com";  //QQ 邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("1198722360@qq.com",
                        "tighqxsmmhbkgiic"); //发件人邮件用户名、授权码
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("区块链投票系统");
            message.setText("您的验证码是："+checkCode+"\n有效期：3分钟");
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return checkCode;
    }
    public void sendKeys(String mail,String publicKey,String privateKey)
    {
        String from="1198722360@qq.com";
        String to=mail;
        String host = "smtp.qq.com";  //QQ 邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("1198722360@qq.com",
                        "tighqxsmmhbkgiic"); //发件人邮件用户名、授权码
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("Your keys!");
            message.setText("Your public key is\n"+publicKey+"\n\nYour private key is\n"+privateKey);
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
