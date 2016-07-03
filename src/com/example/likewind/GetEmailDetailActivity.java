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
	private String protocol = "pop3"; // 邮箱协议
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
			Log.e(LOG_TAG, "接受信息失败！");
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
		Log.e(LOG_TAG, "已经进入receive");
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

		Log.e(LOG_TAG, "蚊子2: " + subject + " " + sender + " " + content + " "
				+ date);

		for (int i = 0; i < sender.length(); i++) {
			if (sender.charAt(i) == '<') {
				address = sender.substring(i + 1, sender.length() - 1);
				break;
			}
		}
		// MailAuthenticator authenticator = null;
		// try {
		// // 连接服务器
		// session = Session.getDefaultInstance(System.getProperties(),
		// authenticator);
		// session.setDebug(true);
		// store = session.getStore(protocol);
		// if (store.isConnected()) {
		// Log.e(LOG_TAG, "天啦，已经连接了");
		// }
		// store.connect(pophost, username, password);
		// // 返回文件夹对象
		// folder = store.getFolder("INBOX");
		// // 设置仅读
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
		// Log.e(LOG_TAG, "connect失败");
		// }
		//
		// textsubject.setText(receivemail.getSubject());//得到邮件解析后的标题内容并且在控件中显示出来
		// textsender.setText(receivemail.getFrom());// 得到邮件解析后的发送者
		// textdate.setText(receivemail.getSentDate());// 得到邮件解析后的发送时间
		// textcontent.setText((CharSequence)
		// message[id].getContent().toString());// 得到邮件解析后的内容
		if (subject == null)
			subject = "";
		if (sender == null)
			sender = "";
		if (date == null)
			date = "";
		if (content == null)
			content = "";

		textsubject.setText(subject);// 得到邮件解析后的标题内容并且在控件中显示出来
		textsender.setText(sender);// 得到邮件解析后的发送者
		textdate.setText(date);// 得到邮件解析后的发送时间
		//textcontent.setText(content);// 得到邮件解析后的内容

	}
}
