package com.example.aftersale;

import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMail {

	final String emailPort = "587";// gmail's smtp port
	final String smtpAuth = "true";
	final String starttls = "true";
	final String emailHost = "smtp.gmail.com";
	// final String fromUser = "giftvincy@gmail.com";
	// final String fromUserEmailPassword = "jk2008gv";

	String fromEmail;
	String fromPassword;
	List<String> toEmailList;
	List<String> CCEmailList;
	String emailSubject;
	String emailBody;
	ArrayList<String> imagePaths;
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	public GMail() {

	}



	public GMail(String fromEmail, String fromPassword, List<String> toEmailList, String emailSubject, String emailBody, ArrayList<String> imagePaths) {
		this.fromEmail = fromEmail;
		this.fromPassword = fromPassword;
		this.toEmailList = toEmailList;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		this.imagePaths=imagePaths;
		CCEmailList=new ArrayList<>();
		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", smtpAuth);
		emailProperties.put("mail.smtp.starttls.enable", starttls);


		emailProperties.put("mail.smtp.proxy.host", "10.128.2.30");
		emailProperties.put("mail.smtp.proxy.port", "8080");
//		emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");



		Log.i("GMail", "Mail server properties set.");

	}
	public Properties addProxy(String host, String port) {
		emailProperties.setProperty("proxySet", "true");
		emailProperties.setProperty("socksProxyHost", host);
		emailProperties.setProperty("socksProxyPort", port);
		return emailProperties;
	}
	public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException {
//		CCEmailList.add("amany.ismaeel@hyperone.com.eg");
//		CCEmailList.add("ramy.kamal@hyperone.com.eg");
//		CCEmailList.add("said.farouk@hyperone.com.eg");
//		CCEmailList.add("ehab.hasanin@hyperone.com.eg");
//		CCEmailList.add("basem.yousry@hyperone.com.eg");
//		CCEmailList.add("walid.rabia@hyperone.com.eg");

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);
		Multipart multipart = new MimeMultipart();


		emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
		for (String toEmail : toEmailList) {
			Log.i("GMail","toEmail: "+toEmail);
			emailMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toEmail));
		}

		for (String CCEmail : CCEmailList) {
			Log.i("GMail","toEmail: "+CCEmail);
			emailMessage.addRecipient(Message.RecipientType.CC,
					new InternetAddress(CCEmail));
		}

		emailMessage.setSubject(emailSubject);
		//emailMessage.setContent(emailBody, "text/html");// for a html email
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(emailBody, "text/html");
		multipart.addBodyPart(messageBodyPart);

			for (int i = 0; i < imagePaths.size(); i++)
			 {

				 MimeBodyPart attachPart = new MimeBodyPart();
//				 DataSource source = new FileDataSource(imagePaths.get(i));
//				 attachPart.setDataHandler(new DataHandler(source));
//				 attachPart.setFileName(Environment.getExternalStorageDirectory().getPath()+imagePaths.get(i));
				 try {
					 attachPart.attachFile(Environment.getExternalStorageDirectory().getPath()+"/Pictures/"+imagePaths.get(i));
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
				 multipart.addBodyPart(attachPart);

		}

		emailMessage.setContent(multipart);
		// emailMessage.setText(emailBody);// for a text email
		Log.i("GMail", "Email Message created.");
		return emailMessage;
	}

	public void sendEmail() throws AddressException, MessagingException {

		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromEmail, fromPassword);
		Log.i("GMail","allrecipients: "+emailMessage.getAllRecipients());
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		Log.i("GMail", "Email sent successfully.");
	}


}
