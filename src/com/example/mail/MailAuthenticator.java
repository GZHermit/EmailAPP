package com.example.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
	String userName = null;
	String passWord = null;

	public MailAuthenticator() {

	}

	public MailAuthenticator(String username, String password) {
		this.userName = username;
		this.passWord = password;
	}

	// 得到用户名和密码验证
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, passWord);
	}
}
