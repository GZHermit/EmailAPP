package com.example.likewind;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GetEmailDetailActivity extends Activity {
	private final String LOG_TAG = "GetEmailDetailActivity";
	private String protocol = "pop3"; // ����Э��
	String address;
	TextView textsender;
	TextView textsubject;
	TextView textdate;
	TextView textcontent;
	Button replybutton;

	Session session;
	Store store;
	Folder folder;
	Message message[];

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mia_maildetail);
		textsender = (TextView) findViewById(R.id.mia_maildetail_sender);
		textsubject = (TextView) findViewById(R.id.mia_maildetail_subject);
		textdate = (TextView) findViewById(R.id.mia_maildetail_date);
		textcontent = (TextView) findViewById(R.id.mia_maildetail_content);
		replybutton = (Button) findViewById(R.id.mia_maildetail_reply);
		replybutton.setOnClickListener(new replyButtonClickListener());
		try {
			receive();
		} catch (Exception e) {
			Log.e(LOG_TAG, "������Ϣʧ�ܣ�");
		}
	}

	public class replyButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(GetEmailDetailActivity.this,
					SendEmailActivity.class);
			intent.putExtra("address", address);
			startActivity(intent);
		}
	}

	public void receive() throws Exception {
		Log.e(LOG_TAG, "�Ѿ�����receive");
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		int id = bundle.getInt("id");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		String pophost = bundle.getString("pop3host");
		String subject = bundle.getString("subject");
		String sender = bundle.getString("sender");
		String date = bundle.getString("date");
		String content = bundle.getString("content");

		Log.e(LOG_TAG, "����2: " + subject + " " + sender + " " + content + " "
				+ date);

		for (int i = 0; i < sender.length(); i++) {
			if (sender.charAt(i) == '<') {
				address = sender.substring(i + 1, sender.length() - 1);
				break;
			}
		}
		// MailAuthenticator authenticator = null;
		// try {
		// // ���ӷ�����
		// session = Session.getDefaultInstance(System.getProperties(),
		// authenticator);
		// session.setDebug(true);
		// store = session.getStore(protocol);
		// if (store.isConnected()) {
		// Log.e(LOG_TAG, "�������Ѿ�������");
		// }
		// store.connect(pophost, username, password);
		// // �����ļ��ж���
		// folder = store.getFolder("INBOX");
		// // ���ý���
		// folder.open(Folder.READ_ONLY);
		//
		// Message message[] = folder.getMessages();
		// if (!message[id].getFolder().isOpen())
		// message[id].getFolder().open(Folder.READ_ONLY);
		// MailReceiver receivemail = new MailReceiver(
		// (MimeMessage) message[id]);
		// folder.close(false);
		// store.close();
		// } catch (MessagingException e) {
		// e.printStackTrace();
		// Log.e(LOG_TAG, "connectʧ��");
		// }
		//
		// textsubject.setText(receivemail.getSubject());//�õ��ʼ�������ı������ݲ����ڿؼ�����ʾ����
		// textsender.setText(receivemail.getFrom());// �õ��ʼ�������ķ�����
		// textdate.setText(receivemail.getSentDate());// �õ��ʼ�������ķ���ʱ��
		// textcontent.setText((CharSequence)
		// message[id].getContent().toString());// �õ��ʼ������������
		if (subject == null)
			subject = "";
		if (sender == null)
			sender = "";
		if (date == null)
			date = "";
		if (content == null)
			content = "";

		textsubject.setText(subject);// �õ��ʼ�������ı������ݲ����ڿؼ�����ʾ����
		textsender.setText(sender);// �õ��ʼ�������ķ�����
		textdate.setText(date);// �õ��ʼ�������ķ���ʱ��
		//textcontent.setText(content);// �õ��ʼ������������

	}
}
