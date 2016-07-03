package com.example.likewind;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String LOG_TAG = MainActivity.class.getSimpleName();
	private static final String FILENAME = "data.txt";
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		ImageButton joinButton = (ImageButton) findViewById(R.id.main_button_join);
		joinButton.setOnClickListener(new LoginButtonListener());

		// ������actionbar������titlebar

		try {
			createFilePath();
		} catch (IOException e) {
			Log.e(LOG_TAG, "����data�ļ�ʧ�ܣ�");
		}
		actionBar = getActionBar();
		actionBar.hide();
	}

	public class LoginButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (isNetworkAvailable(MainActivity.this)) {
				Toast.makeText(getApplicationContext(), "��ӭʹ��LikeWind�ʼ�ϵͳ�����ȵ�¼��",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "��ǰû�п������磡",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void createFilePath() throws IOException {
		File sdCard = Environment.getExternalStorageDirectory();
		// ��ȡ�ⲿ�洢�豸��SD������·��
		Log.e(LOG_TAG, sdCard.getAbsolutePath());
		sdCard = new File(sdCard, "/MyFiles");
		if (sdCard.mkdir()) {
			Log.e(LOG_TAG, "�ɹ������ļ���");
		}
		// sdCard.mkdirs();// ����MyFilesĿ¼(�ɴ����༶Ŀ¼)
		sdCard = new File(sdCard, FILENAME);
		FileOutputStream out = new FileOutputStream(sdCard);
		Writer writer = new OutputStreamWriter(out);
		try {
			String username = "gzhaozhuce@126.com";
			String password = "wangyi123";
			writer.write(username + '\n');
			writer.write(password + '\n');
			Log.e(LOG_TAG, "�ɹ�д���ĵ�");
		} finally {
			writer.close();
		}
	}

	public boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			// ��ȡNetworkInfo����
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					// �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
