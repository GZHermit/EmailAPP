package com.example.likewind;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EmailKindAdapter extends
		RecyclerView.Adapter<EmailKindAdapter.ViewHolder> {

	List<String> emailKinds;

	public EmailKindAdapter(List<String> emailKinds) {
		this.emailKinds = emailKinds;
	}

	// 自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView emailKindsTextView;

		public ViewHolder(View view) {
			super(view);
			// emailKindsTextView = (TextView)
			// view.findViewById(R.id.mia_textview_emailkinds);
		}
	}

	// 返回emalKinds数据集大小
	@Override
	public int getItemCount() {
		return emailKinds.size();
	}

	// 将数据与界面进行绑定
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int pos) {
		viewHolder.emailKindsTextView.setText(emailKinds.get(pos));
	}

	// 创建新View，被LayoutManager所调用
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.mia_left, viewGroup, false);
		ViewHolder vh = new ViewHolder(view);
		return vh;

	}
}
