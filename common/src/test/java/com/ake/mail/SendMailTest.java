package com.ake.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class SendMailTest {
	
	private String userName = "15159816517@163.com";
	private String password = "MeiLan1126";
	private String sendHost = "smtp.163.com";

	Session session ;
	
	private void init(){
		Properties properties = new Properties();
		
		//设置发送
		properties.put("mail.transport.protocal", "smtp");
		//smtp认证
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.transport.class", "com.sun.mail.smtp.SMTPTransport");
		//设置发送邮件服务器
		properties.put("mail.smtp.host", sendHost);
		//获取邮件通信会话
		Authenticator authenticator = new MailAuthenticator();
		session = Session.getDefaultInstance(properties, authenticator);
		session.setDebug(true);
	}
	
	/** 
     * 登陆认证 
     * @author u1 
     * 
     */  
    private class MailAuthenticator extends Authenticator{  
  
        protected PasswordAuthentication getPasswordAuthentication() {  
            // TODO Auto-generated method stub  
            return new PasswordAuthentication(userName, password);  
        }  
          
    } 
    
    public Message createSimpleMsg(String fromAddr, String toAddr) throws MessagingException{
    	MimeMessage message = new MimeMessage(session);
    	message.setFrom(fromAddr);
    	message.setRecipient(RecipientType.TO, new InternetAddress(toAddr));
    	message.setSubject("testMail");
    	message.setSentDate(new Date());
    	message.setText("test from here....");
    	System.out.println("create mail.."+message);
    	return message;
    }
    
    public void sentMail(Message msg) throws MessagingException{
    	Transport.send(msg);
    	System.out.println("after send mail");
    }
    
    public static void main(String[] args) throws MessagingException {
		SendMailTest test = new SendMailTest();
		test.init();
		Message msg = test.createSimpleMsg("15159816517@163.com", "695774301@qq.com");
		test.sentMail(msg);
	}
	
}
