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
		// 设置默认的Framgent
		initFragment();
		// 初始化SlideMenu
		initSlideMenu();
		// 动态注册广播
		initReceiveBroadcast();
	}

	private void initReceiveBroadcast() {
		// 接受从MILeftFragment传来的广播信息
		miBroadcastReceiver = new MIBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Action);
		registerReceiver(miBroadcastReceiver, filter);
	}

	private void initSlideMenu() {

		Fragment leftMenuFragment = new MILeftFragment();
		setBehindContentView(R.layout.mia_left_fragment);
		SlidingMenu menu = getSlidingMenu();
		// 设置滑动菜单的位置
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置阴影宽度
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// 设置阴影效果
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		getFragmentManager().beginTransaction()
				.replace(R.id.mialeft_framelayout_fragment, leftMenuFragment)
				.commit();
	}

	// 点击后显示左侧滑动Menu界面
	public void showLeftMenu(View view) {
		getSlidingMenu().showMenu();
	}

	private void initFragment() {
		// 开启Fragment事务,加载当前邮箱账号里的邮件
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
			// 得到广播后对mia的Framelayout进行重新加载
			Log.e(LOG_TAG, "已进入onReceive");
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
								.setText("收件箱");
						transaction.replace(R.id.mia_framelayout_content,
								rFragment);
						break;
					case 1:
						((TextView) miaActivity
								.findViewById(R.id.mia_text_title))
								.setText("发件箱");
						transaction.replace(R.id.mia_framelayout_content,
								sFragment);
						break;
					case 2:
						((TextView) miaActivity
								.findViewById(R.id.mia_text_title))
								.setText("回收站");
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
				Log.e(LOG_TAG, "无法跳转进入SendEmail");
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
