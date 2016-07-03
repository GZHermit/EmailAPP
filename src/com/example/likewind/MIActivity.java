package com.example.likewind;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MIActivity extends SlidingFragmentActivity {
	private final String LOG_TAG = MIActivity.class.getSimpleName();
	private final String Action = "com.example.likewind.fragment";
	private Activity miaActivity = this;
	private DeleteFragment dFragment;
	private SendFragment sFragment;
	private ReceiveFragment rFragment;
	private MIBroadcastReceiver miBroadcastReceiver;
	private ImageButton sendEmailButton;
	private ImageButton updateButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mia);

		sendEmailButton = (ImageButton) findViewById(R.id.id_iv_right);
		sendEmailButton.setOnClickListener(new SendMailButtonListener());

		updateButton = (ImageButton) findViewById(R.id.mia_image_update);
		updateButton.setOnClickListener(new UpdateMailButtonListener());
		// ����Ĭ�ϵ�Framgent
		initFragment();
		// ��ʼ��SlideMenu
		initSlideMenu();
		// ��̬ע��㲥
		initReceiveBroadcast();
	}

	private void initReceiveBroadcast() {
		// ���ܴ�MILeftFragment�����Ĺ㲥��Ϣ
		miBroadcastReceiver = new MIBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Action);
		registerReceiver(miBroadcastReceiver, filter);
	}

	private void initSlideMenu() {

		Fragment leftMenuFragment = new MILeftFragment();
		setBehindContentView(R.layout.mia_left_fragment);
		SlidingMenu menu = getSlidingMenu();
		// ���û����˵���λ��
		menu.setMode(SlidingMenu.LEFT);
		// ���ô�����Ļ��ģʽ
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// ������Ӱ���
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// ������ӰЧ��
		menu.setShadowDrawable(R.drawable.shadow);
		// ���û����˵���ͼ�Ŀ��
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ý��뽥��Ч����ֵ
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		getFragmentManager().beginTransaction()
				.replace(R.id.mialeft_framelayout_fragment, leftMenuFragment)
				.commit();
	}

	// �������ʾ��໬��Menu����
	public void showLeftMenu(View view) {
		getSlidingMenu().showMenu();
	}

	private void initFragment() {
		// ����Fragment����,���ص�ǰ�����˺�����ʼ�
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		rFragment = new ReceiveFragment();
		transaction.replace(R.id.mia_framelayout_content, rFragment);
		transaction.commit();
	}

	public class MIBroadcastReceiver extends BroadcastReceiver {
		private final String LOG_TAG = "MIBroadcastReceiver";
		private Context mcontext;
		private Intent mintent;

		@Override
		public void onReceive(Context context, Intent intent) {
			// �õ��㲥���mia��Framelayout�������¼���
			Log.e(LOG_TAG, "�ѽ���onReceive");
			mcontext = context;
			mintent = intent;
			miaActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					FragmentManager fm = getFragmentManager();
					FragmentTransaction transaction = fm.beginTransaction();
					sFragment = new SendFragment();
					rFragment = new ReceiveFragment();
					dFragment = new DeleteFragment();
					switch (mintent.getIntExtra("flag", 0)) {
					case 0:
						((TextView) miaActivity
								.findViewById(R.id.mia_text_title))
								.setText("�ռ���");
						transaction.replace(R.id.mia_framelayout_content,
								rFragment);
						break;
					case 1:
						((TextView) miaActivity
								.findViewById(R.id.mia_text_title))
								.setText("������");
						transaction.replace(R.id.mia_framelayout_content,
								sFragment);
						break;
					case 2:
						((TextView) miaActivity
								.findViewById(R.id.mia_text_title))
								.setText("����վ");
						transaction.replace(R.id.mia_framelayout_content,
								dFragment);
						break;
					default:
						break;
					}
					transaction.commit();
				}
			});
		}
	}

	public class SendMailButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			try {
				Intent intent = new Intent();
				intent.setClass(MIActivity.this, SendEmailActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG_TAG, "�޷���ת����SendEmail");
			}
		}
	}

	public class UpdateMailButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			initFragment();
		}
	}
}
