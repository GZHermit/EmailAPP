package com.example.mail;

import java.util.Properties;

public class MailSenderInfo {
	// �����ʼ��ķ�������IP
	private String mailServerHost;
	// �����ʼ��ķ������Ķ˿ں�,Ĭ��Ϊ25
	private String mailServerPort = "25";
	// �ʼ��ķ��͵�ַ
	private String sendAddress;
	// �ʼ��Ľ��ܵ�ַ
	private String receiveAddress;
	// ��¼�����û���
	private String userName;
	// ��¼��������
	private String passWord;
	// �Ƿ���Ҫ�����֤��Ĭ��Ϊ��
	private boolean isvalidated = false;
	// �ʼ�����
	private String mailSubject;
	// �ʼ�����
	private String mailContent;
	// �ʼ��ĸ����ļ�������String����洢
	private String[] mailAttachFileNames;

	// ��ȡ���������IP
	public String getMailServerHost() {
		return mailServerHost;
	}

	// �������������IP
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	// ��ȡ����������˿ں�
	public String getMailServerPort() {
		return mailServerPort;
	}

	// ��������������˿ں�
	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	// ��ȡ��¼�����û���
	public String getUserName() {
		return userName;
	}

	// ���õ�¼�����û���
	public void setUserName(String userName) {
		this.userName = userName;
	}

	// ��ȡ��¼��������
	public String getPassWord() {
		return passWord;
	}

	// ���õ�¼��������
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	// ��ȡ�����ʼ���ַ
	public String getSendAddress() {
		return sendAddress;
	}

	// ���÷����ʼ���ַ
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	// ��ȡ�����ʼ���ַ
	public String getReceiveAddress() {
		return receiveAddress;
	}

	// ���ý����ʼ���ַ
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	// ��ȡ�ʼ�����
	public String getMailSubject() {
		return mailSubject;
	}

	// �����ʼ�����
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	// ��ȡ�ʼ�����
	public String getMailContent() {
		return mailContent;
	}

	// �����ʼ�����
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	// ��ȡ�ʼ������ļ���
	public String[] getAttachFileNames() {
		return mailAttachFileNames;
	}

	// �����ʼ������ļ���
	public void setAttachFileNames(String[] mailAttachFileNames) {
		this.mailAttachFileNames = mailAttachFileNames;
	}

	// ��ȡ�Ƿ���������֤�Ľ��
	public boolean isValidated() {
		return isvalidated;
	}

	// �����Ƿ���������֤
	public void setValidate(boolean validate) {
		this.isvalidated = validate;
	}

	// ��ȡ�ʼ��Ự����
	public Properties getProperties() {
		Properties p = new Properties();
		p.setProperty("mail.smtp.protocol.", "smtp");
		p.setProperty("mail.smtp.host", this.mailServerHost);
		p.setProperty("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", isvalidated);
		return p;
	}
}
