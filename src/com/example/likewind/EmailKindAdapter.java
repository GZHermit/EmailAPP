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

	// �Զ����ViewHolder������ÿ��Item�ĵ����н���Ԫ��
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView emailKindsTextView;

		public ViewHolder(View view) {
			super(view);
			// emailKindsTextView = (TextView)
			// view.findViewById(R.id.mia_textview_emailkinds);
		}
	}

	// ����emalKinds���ݼ���С
	@Override
	public int getItemCount() {
		return emailKinds.size();
	}

	// �������������а�
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int pos) {
		viewHolder.emailKindsTextView.setText(emailKinds.get(pos));
	}

	// ������View����LayoutManager������
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.mia_left, viewGroup, false);
		ViewHolder vh = new ViewHolder(view);
		return vh;

	}
}
