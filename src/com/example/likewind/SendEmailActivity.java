package com.example.likewind;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mail.MultiMailSender;
import com.example.mail.MultiMailSender.MultiMailSenderInfo;

public class SendEmailActivity extends Activity {
	private final String LOG_TAG = "SendEmailActivity";
	EditText receEmail;
	EditText ccEmail;
	EditText subject;
	EditText text;
	LooperThread mLooperThread;

	String address;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendemail);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null)
			address = bundle.getString("address");

		receEmail = (EditText) findViewById(R.id.sendemail_edit_recemail);
		ccEmail = (EditText) findViewById(R.id.sendemail_edit_ccemail);
		subject = (EditText) findViewById(R.id.sendemail_edit_title);
		text = (EditText) findViewById(R.id.sendemail_edit_text);

		if (address != null)
			receEmail.setText(address);

		Button sureSend = (Button) findViewById(R.id.sendemail__button_send);
		sureSend.setOnClickListener(new SureSendButtonListener());
		Button cancelSend = (Button) findViewById(R.id.sendemail__button_cancel);
		cancelSend.setOnClickListener(new CancelSendButtonListener());
		Button fileSend = (Button) findViewById(R.id.sendemail_button_file);
		fileSend.setOnClickListener(new FileSendButtonListener());

		mLooperThread = new LooperThread();
		mLooperThread.start();
	}
	
	public class FileSendButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	}

	public class SureSendButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			final String[] receemailstr = divideAddress(receEmail.getText()
					.toString());
			for (int i = 0; i < receemailstr.length; i++)
				Log.e(LOG_TAG, "rece:" + receemailstr[i]);
			final String[] ccemailstr = divideAddress(ccEmail.getText()
					.toString());
			for (int i = 0; i < ccemailstr.length; i++)
				Log.e(LOG_TAG, "cc:" + ccemailstr[i]);
			final String subjectstr = subject.getText().toString();
			Log.e(LOG_TAG, "subject:" + subjectstr);
			final String textstr = text.getText().toString();
			Log.e(LOG_TAG, "text:" + textstr);
			new Thread(new Runnable() {

				@Override
				public void run() {
					MultiMailSenderInfo mainInfo = new MultiMailSenderInfo();
					boolean flag = false;
					try {
						mainInfo.setMailServerHost("smtp.126.com");
						mainInfo.setMailServerPort("25");
						mainInfo.setValidate(true);
						mainInfo.setUserName("gzhaozhuce@126.com");
						mainInfo.setPassWord("kaodao750ah");
						mainInfo.setSendAddress(mainInfo.getUserName());
						mainInfo.setReceives(receemailstr);
						mainInfo.setCcs(ccemailstr);
						mainInfo.setMailSubject(subjectstr);
						mainInfo.setMailContent(textstr);
						//mainInfo.setAttachFileNames(mailAttachFileNames);
						MultiMailSender mailsend = new MultiMailSender();
						mailsend.sendTextMail(mainInfo, getApplicationContext());
						flag = true;
						printInfo(flag);
					} catch (Exception e) {
						printInfo(flag);
						e.printStackTrace();
					} finally {
						finish();
					}
				}
			}).start();
		}
	}

	public class CancelSendButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			finish();
		}
	}
	

	// 用于分隔多个邮件地址
	public String[] divideAddress(String str) {
		ArrayList<String> dividedaddress = new ArrayList<String>();
		int len = str.length();
		int last = 0;// 前一个;处的编号
		for (int i = 0; i < len; i++) {
			if (str.charAt(i) == ';') {
				dividedaddress.add(str.substring(last, i));
				last = i + 1;
			}
		}
		String[] address = new String[dividedaddress.size()];
		address = dividedaddress.toArray(address);
		return address;
	}

	// 在发送邮件后给出结果信息
	public void printInfo(boolean flag) {
		Message msg = new Message();
		if (flag)
			msg.what = 1;
		else
			msg.what = 0;
		mLooperThread.mHandler.sendMessage(msg);
	}

	public class LooperThread extends Thread {
		public Handler mHandler;

		public void run() {
			Looper.prepare();
			mHandler = new Handler() {
				public void handleMessage(Message msg) {
					if (msg.what == 1)
						Toast.makeText(SendEmailActivity.this, "邮件发送成功", 2000)
								.show();
					else {
						Toast.makeText(SendEmailActivity.this, "邮件发送失败", 2000)
								.show();
					}
				}
			};
			Looper.loop();
		}
	}
}
