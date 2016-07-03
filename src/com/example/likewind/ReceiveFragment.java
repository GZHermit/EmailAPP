package com.example.likewind;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.MessagingException;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.likewind.SendEmailActivity.LooperThread;
import com.example.mail.MailLister;
import com.example.mail.MailReceiver;

public class ReceiveFragment extends Fragment {
	private final String LOG_TAG = ReceiveFragment.class.getSimpleName();
	private View rView;
	private ListView rCategories;
	private ListAdapter rAdapter;
	private SimpleAdapter sAdapter;
	List<MailReceiver> mails;

	LooperThread mLooperThread;

	private String username;
	private String password;
	private String pop3host;
	private String smtp;

	ArrayList<HashMap<String, String>> emails = new ArrayList<HashMap<String, String>>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rView == null) {
			initView(inflater, container);
		}
		return rView;
	}

	public void initView(LayoutInflater inflater, ViewGroup container) {
		// ((TextView)getActivity().findViewById(R.id.mia_text_title)).setText("收件箱");
		rView = inflater.inflate(R.layout.receive, container, false);
		rCategories = (ListView) rView
				.findViewById(R.id.recevie_listview_rcategories);
		// rAdapter = new ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_1, remails);
		// rCategories.setAdapter(rAdapter);
		getCollectMailInfo();
		getEmails();
		// 短按是查看邮件详细信息
		rCategories.setOnItemClickListener(new ReceMailClickListener());
		// 长按是删除邮件
		rCategories.setOnItemLongClickListener(new ReceMailLongClickListener());
	}

	private void getCollectMailInfo() {
		username = "gzhaozhuce@126.com";
		password = "kaodao750ah";
		pop3host = "pop.126.com";
		smtp = "";
	}

	private void getEmails() {
		new UpdateViewTask().execute();
	}

	private class UpdateViewTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// 执行耗时操作
			Log.e(LOG_TAG, "准备开始获得maillist");
			MailLister maillister = new MailLister(pop3host, username, password);
			Log.e(LOG_TAG, "已经获得maillist");
			try {
				mails = maillister.getAllMail("INBOX");
				Log.e(LOG_TAG, "准备进入脑残模式");
				if (mails == null) {
					Log.e(LOG_TAG, "已经进入脑残模式");
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("sender", "guoshen");
					map.put("subject", "guoshen");
					map.put("date", "guoshen");
					emails.add(map);
					Log.e(LOG_TAG,
							"脑残模式里emails的size:"
									+ Integer.toString(emails.size()));
				} else {
					Log.e(LOG_TAG, "没有进入脑残模式");
					for (int i = 0; i < mails.size(); i++) {
						MailReceiver tempmr = mails.get(i);
						String sender = tempmr.getFrom();
						String subject = tempmr.getSubject();
						String date = tempmr.getSentDate();
						Log.e(LOG_TAG, "sender:" + sender + " subject:"
								+ subject + " date:" + date);
						HashMap<String, String> map = new HashMap<String, String>();// 定义一个Map.将获取的内容以键值的方式将内容展现
						if (sender == null) {
							map.put("sender", "guoshen");
						} else {
							map.put("sender", sender);
						}
						if (subject == null) {
							map.put("subject", "guoshen");
						} else {
							map.put("subject", subject);
						}
						if (date == null) {
							map.put("date", "guoshen");
						} else {
							map.put("date", date);
						}
						emails.add(map);
					}
				}
			} catch (MessagingException me) {
				Log.e(LOG_TAG, "me exception");
				me.printStackTrace();
			} catch (UnsupportedEncodingException ue) {
				Log.e(LOG_TAG, "ue exception");
				ue.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			// 对UI组件的更新操作
			Log.e(LOG_TAG, "即将new一个sAdapter");
			sAdapter = new SimpleAdapter(getActivity(), emails,
					R.layout.mia_center_emailsimpleshow, new String[] {
							"sender", "subject", "date" }, new int[] {
							R.id.mia_center_sender, R.id.mia_center_subject,
							R.id.mia_center_date });
			Log.e(LOG_TAG, "即将setAdapter");
			rCategories.setAdapter(sAdapter);
		}
	}

	private class ReceMailClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			ComponentName comp = new ComponentName("com.example.likewind",
					"com.example.likewind.GetEmailDetailActivity");
			try {
				String subject = mails.get(position).getSubject();
				String sender = mails.get(position).getFrom();
				String content = mails.get(position).getMailContent();
				String date = mails.get(position).getSentDate();
				String address = mails.get(position).getMailAddress("TO");
				intent.putExtra("id", position);
				intent.putExtra("username", username);
				intent.putExtra("password", password);
				intent.putExtra("pop3host", pop3host);
				intent.putExtra("sender", sender);
				intent.putExtra("subject", subject);
				intent.putExtra("content", content);
				intent.putExtra("date", date);
				intent.putExtra("address", address);
				Log.e(LOG_TAG, "蚊子: " + subject + " " + sender + " " + content
						+ " " + date);
			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			intent.setComponent(comp);
			startActivity(intent);
		}

	}

	private class ReceMailLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			new AlertDialog.Builder(getActivity())
					.setTitle("系统提示")
					// 设置对话框标题
					.setMessage("确定要删除这封邮件吗？")
					// 设置显示的内容
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DeleteEmailTask task = new DeleteEmailTask();
									task.execute(position);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {// 添加返回按钮
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();// 在按键响应事件中显示此对话框
			return true;
		}

	}

	private class DeleteEmailTask extends AsyncTask<Integer, Integer, String> {
		ProgressDialog pdiglog = null;

		@Override
		protected String doInBackground(Integer... params) {
			try {
				new MailLister(pop3host, username, password).delete(params[0]);
				getEmails();
				return "1";
			} catch (Exception e) {
				Log.e(LOG_TAG, "删除失败");
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				pdiglog.hide();
			} catch (Exception e) {
				Log.e(LOG_TAG, "hide出了问题");
				// TODO: handle exception
			}

			if (result != null && result.equals("1")) {
				Toast.makeText(getActivity(), "邮件删除成功", 2000).show();
			} else {
				Toast.makeText(getActivity(), "邮件删除失败", 2000).show();
			}
		}
	}

}
