package com.example.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import android.util.Log;

public class MailLister {
	private final String LOG_TAG = MailLister.class.getSimpleName();
	private String host; // pop3服务器
	private String user; // 邮箱
	private String password; // 密码
	private String protocol = "pop3"; // 邮箱协议
	private int port = 110; // 服务器端口号
	private String dataformat = "yy-MM-dd hh:mm";// 默认的时间格式

	Session session;
	Store store;
	Folder folder;

	public MailLister(String popHost, String userAcount, String password) {
		this.host = popHost;
		this.user = userAcount;
		this.password = password;
	}

	@SuppressWarnings("finally")
	public List<MailReceiver> getAllMail(String foldername)
			throws MessagingException {
		List<MailReceiver> maillist = new ArrayList<MailReceiver>();
		MailAuthenticator authenticator = null;
		try {
			// 连接服务器
			session = Session.getDefaultInstance(System.getProperties(),
					authenticator);
			store = session.getStore(protocol);
			Log.e(LOG_TAG, this.host + " " + this.user + " " + this.password);
			store.connect(this.host, this.user, this.password);
			Log.e(LOG_TAG, "connect完成");
		} catch (Exception e) {
			Log.e(LOG_TAG, "connect失败");
		}
		// 打开文件夹
		try {
			folder = store.getFolder(foldername);
			folder.open(Folder.READ_ONLY);
			Log.e(LOG_TAG, "尝试open文件夹");
			// 总邮件数
			int mailcount = folder.getMessageCount();
			Log.e(LOG_TAG, "邮件数：" + Integer.toString(mailcount));
			if (mailcount == 0) {
				maillist = null;
			} else {
				// 取出所有的邮件
				Message[] messages = folder.getMessages();
				for (int i = 0; i < messages.length; i++) {
					if (!messages[i].getFolder().isOpen())
						messages[i].getFolder().open(Folder.READ_ONLY);
					Log.e(LOG_TAG, "无话可说：" + messages[i].getSubject());
					MailReceiver mailreceiver = new MailReceiver(
							(MimeMessage) messages[i]);
					Log.e(LOG_TAG, "无话可说2：" + mailreceiver.getFrom());
					mailreceiver.setDataFormat(dataformat);
					maillist.add(mailreceiver);
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "文件");
		} finally {
			// 关闭文件夹
			folder.close(false); // boolean参数表示是否在删除操作邮件后更新folder
			store.close();
			if (maillist == null)
				Log.e(LOG_TAG, "maillist为null");
			return maillist;// 返回获取到的邮件列表
		}
	}

	public void delete(int position) throws MessagingException {
		Session session = Session.getDefaultInstance(System.getProperties(),
				null);
		Store store = session.getStore(protocol);
		store.connect(this.host, this.user, this.password);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message messages[] = folder.getMessages();
		messages[position].setFlag(Flags.Flag.DELETED, true);
		Log.e(LOG_TAG, "邮件删除成功！");
		folder.close(true);
		store.close();
	}
}
