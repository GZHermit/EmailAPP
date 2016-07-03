package com.example.likewind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.mail.Flags.Flag;

import android.R.bool;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private final String LOG_TAG = LoginActivity.class.getSimpleName();
	private static final String FILENAME = "data.txt";
	ActionBar actionBar;
	EditText username;
	EditText password;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button button_login = (Button) findViewById(R.id.login_button_login);
		button_login.setOnClickListener(new LoginButtonListener());

		Button button_cancel = (Button) findViewById(R.id.login_button_cancel);
		button_cancel.setOnClickListener(new CancelButtonListener());

		username = (EditText) findViewById(R.id.text_username);
		password = (EditText) findViewById(R.id.text_password);

		actionBar = getActionBar();
	}

	public class LoginButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			boolean flag = false;
			try {
				flag = loadData();
			} catch (FileNotFoundException fe) {
				// TODO: handle exception
			} catch (IOException ie) {
				// TODO: handle exception
			}
			if (flag == true) {
				Toast.makeText(getApplicationContext(), "欢迎回来，正在为您加载邮件~",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MIActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "用户名或密码错误！",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public class CancelButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			shows(v);
		}
	}

	public boolean loadData() throws FileNotFoundException, IOException {
		BufferedReader reader = null;
		StringBuilder data = new StringBuilder();
		boolean flag = true;
		try {
			File sdCard = Environment.getExternalStorageDirectory();
			sdCard = new File(sdCard, "/MyFiles/" + FILENAME);
			FileInputStream in = new FileInputStream(sdCard);
			reader = new BufferedReader(new InputStreamReader(in));
			String line = new String();
			if ((line = reader.readLine()) != null) {
				Log.e(LOG_TAG, line);
				if (!line.equals(username.getText().toString()))
					flag = false;
			}
			if (flag && (line = reader.readLine()) != null) {
				Log.e(LOG_TAG, line);
				if (!line.equals(password.getText().toString()))
					return false;
			}

		} catch (FileNotFoundException e) {
		} finally {
			reader.close();
			return flag;
		}
	}

	public void shows(View v) {
		actionBar.hide();
	}
}
