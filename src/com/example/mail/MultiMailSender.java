package com.example.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.content.Context;
import android.util.Log;

public class MultiMailSender {
	private final String LOG_TAG = "MultiMailSender";

	public boolean sendTextMail(MultiMailSenderInfo mailInfo, Context context) {
		Log.e(LOG_TAG, "已经进入sendTextMail");
		MailAuthenticator authenticator = null;
		final Context mcontext = context;
		Properties mailPro = mailInfo.getProperties();
		mailPro.put("mail.smtp.auth", "true"); 
		// 判断是否需要身份认证
		if (mailInfo.isValidated()) {
			// 如果需要身份认证，创建一个密码验证器
			authenticator = new MailAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassWord());
		}
		// 通过邮件会话属性和密码验证器构造一个发送邮件的Session（configure）
		Session sendMailSession = Session.getInstance(mailPro, authenticator);//getDefaultInstance()是默认共享一个session
		try {
			// 通过Session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address sendAddress = new InternetAddress(mailInfo.getSendAddress());
			// 设置邮件的发送者
			mailMessage.setFrom(sendAddress);
			Log.e(LOG_TAG, "发送者:" + mailInfo.getSendAddress());
			// 设置邮件的接受者，并且设置到邮件消息中
			Address[] tos = null;
			String[] receivers = mailInfo.getReceives();
			for (int i = 0; i < receivers.length; i++)
				Log.e(LOG_TAG, "rece:" + receivers[i]);
			if (receivers != null) {
				tos = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					tos[i] = new InternetAddress(receivers[i]);
				}
			} else {
				tos = new InternetAddress[1];
				tos[0] = new InternetAddress(receivers[0]);
			}
			// 获取抄送者信息
			String[] ccs = mailInfo.getCcs();
			if (ccs != null) {
				Address[] ccAdresses = new InternetAddress[ccs.length];
				for (int i = 0; i < ccs.length; i++) {
					ccAdresses[i] = new InternetAddress(ccs[i]);
				}
				// 将抄送者信息设置到邮件信息中
				mailMessage.setRecipients(Message.RecipientType.CC, ccAdresses);
			}
			// 设置消息的接受者属性为TO，即直接接受者，CC为抄送，BCC为密抄送
			mailMessage.setRecipients(Message.RecipientType.TO, tos);
			mailMessage.setSubject(mailInfo.getMailSubject());
			Log.e(LOG_TAG, "主题:" + mailInfo.getMailSubject());
			mailMessage.setSentDate(new Date());
			mailMessage.setText(mailInfo.getMailContent());
			Log.e(LOG_TAG, "正文:" + mailInfo.getMailContent());
			mailMessage.saveChanges();
			try {
				Transport transport = sendMailSession.getTransport("smtp");
				transport.connect("smtp.126.com", "gzhaozhuce@126.com",
						"kaodao750ah");
				transport.sendMessage(mailMessage,
						mailMessage.getAllRecipients());
				transport.close();
				Log.e(LOG_TAG, "发送Email成功");

			} catch (MessagingException e) {
				Log.e(LOG_TAG, "发送Email失败");
			}
			return true;
		} catch (MessagingException e) {
			e.getStackTrace();
		}
		return false;
	}

	public static class MultiMailSenderInfo extends MailSenderInfo {
		// 抄送人名单
		private String[] ccs;
		// 接收人名单
		private String[] receives;

		public String[] getCcs() {
			return ccs;
		}

		public void setCcs(String[] ccs) {
			this.ccs = ccs;
		}

		public String[] getReceives() {
			return receives;
		}

		public void setReceives(String[] receives) {
			this.receives = receives;
		}
	}
}
