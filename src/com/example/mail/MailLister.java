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
	private String host; // pop3������
	private String user; // ����
	private String password; // ����
	private String protocol = "pop3"; // ����Э��
	private int port = 110; // �������˿ں�
	private String dataformat = "yy-MM-dd hh:mm";// Ĭ�ϵ�ʱ���ʽ

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
			// ���ӷ�����
			session = Session.getDefaultInstance(System.getProperties(),
					authenticator);
			store = session.getStore(protocol);
			Log.e(LOG_TAG, this.host + " " + this.user + " " + this.password);
			store.connect(this.host, this.user, this.password);
			Log.e(LOG_TAG, "connect���");
		} catch (Exception e) {
			Log.e(LOG_TAG, "connectʧ��");
		}
		// ���ļ���
		try {
			folder = store.getFolder(foldername);
			folder.open(Folder.READ_ONLY);
			Log.e(LOG_TAG, "����open�ļ���");
			// ���ʼ���
			int mailcount = folder.getMessageCount();
			Log.e(LOG_TAG, "�ʼ�����" + Integer.toString(mailcount));
			if (mailcount == 0) {
				maillist = null;
			} else {
				// ȡ�����е��ʼ�
				Message[] messages = folder.getMessages();
				for (int i = 0; i < messages.length; i++) {
					if (!messages[i].getFolder().isOpen())
						messages[i].getFolder().open(Folder.READ_ONLY);
					Log.e(LOG_TAG, "�޻���˵��" + messages[i].getSubject());
					MailReceiver mailreceiver = new MailReceiver(
							(MimeMessage) messages[i]);
					Log.e(LOG_TAG, "�޻���˵2��" + mailreceiver.getFrom());
					mailreceiver.setDataFormat(dataformat);
					maillist.add(mailreceiver);
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "�ļ�");
		} finally {
			// �ر��ļ���
			folder.close(false); // boolean������ʾ�Ƿ���ɾ�������ʼ������folder
			store.close();
			if (maillist == null)
				Log.e(LOG_TAG, "maillistΪnull");
			return maillist;// ���ػ�ȡ�����ʼ��б�
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
		Log.e(LOG_TAG, "�ʼ�ɾ���ɹ���");
		folder.close(true);
		store.close();
	}
}
