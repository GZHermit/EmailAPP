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

		// 这里是actionbar，不是titlebar

		try {
			createFilePath();
		} catch (IOException e) {
			Log.e(LOG_TAG, "创建data文件失败！");
		}
		actionBar = getActionBar();
		actionBar.hide();
	}

	public class LoginButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (isNetworkAvailable(MainActivity.this)) {
				Toast.makeText(getApplicationContext(), "欢迎使用LikeWind邮件系统！请先登录！",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "当前没有可用网络！",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void createFilePath() throws IOException {
		File sdCard = Environment.getExternalStorageDirectory();
		// 获取外部存储设备（SD卡）的路径
		Log.e(LOG_TAG, sdCard.getAbsolutePath());
		sdCard = new File(sdCard, "/MyFiles");
		if (sdCard.mkdir()) {
			Log.e(LOG_TAG, "成功创建文件夹");
		}
		// sdCard.mkdirs();// 创建MyFiles目录(可创建多级目录)
		sdCard = new File(sdCard, FILENAME);
		FileOutputStream out = new FileOutputStream(sdCard);
		Writer writer = new OutputStreamWriter(out);
		try {
			String username = "gzhaozhuce@126.com";
			String password = "wangyi123";
			writer.write(username + '\n');
			writer.write(password + '\n');
			Log.e(LOG_TAG, "成功写入文档");
		} finally {
			writer.close();
		}
	}

	public boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
