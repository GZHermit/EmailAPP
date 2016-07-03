package com.example.mail;

import java.util.Properties;

public class MailSenderInfo {
	// 发送邮件的服务器的IP
	private String mailServerHost;
	// 发送邮件的服务器的端口号,默认为25
	private String mailServerPort = "25";
	// 邮件的发送地址
	private String sendAddress;
	// 邮件的接受地址
	private String receiveAddress;
	// 登录邮箱用户名
	private String userName;
	// 登录邮箱密码
	private String passWord;
	// 是否需要身份验证，默认为否
	private boolean isvalidated = false;
	// 邮件主题
	private String mailSubject;
	// 邮件内容
	private String mailContent;
	// 邮件的附件文件名，用String数组存储
	private String[] mailAttachFileNames;

	// 获取邮箱服务器IP
	public String getMailServerHost() {
		return mailServerHost;
	}

	// 设置邮箱服务器IP
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	// 获取邮箱服务器端口号
	public String getMailServerPort() {
		return mailServerPort;
	}

	// 设置邮箱服务器端口号
	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	// 获取登录邮箱用户名
	public String getUserName() {
		return userName;
	}

	// 设置登录邮箱用户名
	public void setUserName(String userName) {
		this.userName = userName;
	}

	// 获取登录邮箱密码
	public String getPassWord() {
		return passWord;
	}

	// 设置登录邮箱密码
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	// 获取发送邮件地址
	public String getSendAddress() {
		return sendAddress;
	}

	// 设置发送邮件地址
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	// 获取结束邮件地址
	public String getReceiveAddress() {
		return receiveAddress;
	}

	// 设置接收邮件地址
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	// 获取邮件主题
	public String getMailSubject() {
		return mailSubject;
	}

	// 设置邮件主题
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	// 获取邮件内容
	public String getMailContent() {
		return mailContent;
	}

	// 设置邮件内容
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	// 获取邮件附件文件名
	public String[] getAttachFileNames() {
		return mailAttachFileNames;
	}

	// 设置邮件附件文件名
	public void setAttachFileNames(String[] mailAttachFileNames) {
		this.mailAttachFileNames = mailAttachFileNames;
	}

	// 获取是否进行身份验证的结果
	public boolean isValidated() {
		return isvalidated;
	}

	// 设置是否进行身份验证
	public void setValidate(boolean validate) {
		this.isvalidated = validate;
	}

	// 获取邮件会话属性
	public Properties getProperties() {
		Properties p = new Properties();
		p.setProperty("mail.smtp.protocol.", "smtp");
		p.setProperty("mail.smtp.host", this.mailServerHost);
		p.setProperty("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", isvalidated);
		return p;
	}
}
