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
		Log.e(LOG_TAG, "�Ѿ�����sendTextMail");
		MailAuthenticator authenticator = null;
		final Context mcontext = context;
		Properties mailPro = mailInfo.getProperties();
		mailPro.put("mail.smtp.auth", "true"); 
		// �ж��Ƿ���Ҫ�����֤
		if (mailInfo.isValidated()) {
			// �����Ҫ�����֤������һ��������֤��
			authenticator = new MailAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassWord());
		}
		// ͨ���ʼ��Ự���Ժ�������֤������һ�������ʼ���Session��configure��
		Session sendMailSession = Session.getInstance(mailPro, authenticator);//getDefaultInstance()��Ĭ�Ϲ���һ��session
		try {
			// ͨ��Session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵ�ַ
			Address sendAddress = new InternetAddress(mailInfo.getSendAddress());
			// �����ʼ��ķ�����
			mailMessage.setFrom(sendAddress);
			Log.e(LOG_TAG, "������:" + mailInfo.getSendAddress());
			// �����ʼ��Ľ����ߣ��������õ��ʼ���Ϣ��
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
			// ��ȡ��������Ϣ
			String[] ccs = mailInfo.getCcs();
			if (ccs != null) {
				Address[] ccAdresses = new InternetAddress[ccs.length];
				for (int i = 0; i < ccs.length; i++) {
					ccAdresses[i] = new InternetAddress(ccs[i]);
				}
				// ����������Ϣ���õ��ʼ���Ϣ��
				mailMessage.setRecipients(Message.RecipientType.CC, ccAdresses);
			}
			// ������Ϣ�Ľ���������ΪTO����ֱ�ӽ����ߣ�CCΪ���ͣ�BCCΪ�ܳ���
			mailMessage.setRecipients(Message.RecipientType.TO, tos);
			mailMessage.setSubject(mailInfo.getMailSubject());
			Log.e(LOG_TAG, "����:" + mailInfo.getMailSubject());
			mailMessage.setSentDate(new Date());
			mailMessage.setText(mailInfo.getMailContent());
			Log.e(LOG_TAG, "����:" + mailInfo.getMailContent());
			mailMessage.saveChanges();
			try {
				Transport transport = sendMailSession.getTransport("smtp");
				transport.connect("smtp.126.com", "gzhaozhuce@126.com",
						"kaodao750ah");
				transport.sendMessage(mailMessage,
						mailMessage.getAllRecipients());
				transport.close();
				Log.e(LOG_TAG, "����Email�ɹ�");

			} catch (MessagingException e) {
				Log.e(LOG_TAG, "����Emailʧ��");
			}
			return true;
		} catch (MessagingException e) {
			e.getStackTrace();
		}
		return false;
	}

	public static class MultiMailSenderInfo extends MailSenderInfo {
		// ����������
		private String[] ccs;
		// ����������
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
