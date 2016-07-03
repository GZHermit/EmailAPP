package com.example.xmlviewbyself;

import com.example.likewind.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class EmailEditText extends EditText {
	private final static String TAG = "EmailEditText";
	private Drawable imgInable;
	private Drawable imgAble;
	private Context mContext;
	public boolean isEmail;

	public EmailEditText(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public EmailEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public EmailEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		imgInable = mContext.getResources().getDrawable(R.drawable.delete_gray);
		imgAble = mContext.getResources().getDrawable(R.drawable.delete);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				divideEmail(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				setDrawable();
			}
		});
		setDrawable();
	}

	// 分隔Email文本 当输完一个邮箱地址后输入空格可以转化成分号
	private void divideEmail(CharSequence s) {
		try {
			Log.e(TAG, "step0:" + s.toString());
			int len = s.length();
			if (len == 0)
				return;
			else {
				char[] temps = s.toString().toCharArray();
				if (temps[len - 1] == ' ') {
					int tempselection = EmailEditText.this.getSelectionEnd();

					Log.e(TAG, Integer.toString(EmailEditText.this
							.getSelectionEnd()));
					temps[len - 1] = ';';
					String ans = new String(temps);
					EmailEditText.this.setText(ans);
					EmailEditText.this.setSelection(tempselection);
				}
			}
		} catch (RuntimeException e) {
			Log.e(TAG, Integer.toString(EmailEditText.this.getSelectionEnd()));
			e.printStackTrace();
		}
	}

	// 设置删除图片
	private void setDrawable() {
		if (length() < 1)
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
	}

	// 处理删除事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 50;
			if (rect.contains(eventX, eventY))
				setText("");
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
