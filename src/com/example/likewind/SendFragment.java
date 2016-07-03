package com.example.likewind;

import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SendFragment extends Fragment {
	private View sView;
	private ListView sCategories;
	private ListAdapter sAdapter;
	private List<String> semails;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (sView == null) {
			initView(inflater, container);
		}
		return sView;
	}

	public void initView(LayoutInflater inflater, ViewGroup container) {
		// ((TextView)getActivity().findViewById(R.id.mia_text_title)).setText("发件箱");
		sView = inflater.inflate(R.layout.receive, container, false);
		sCategories = (ListView) sView
				.findViewById(R.id.recevie_listview_rcategories);
		getEmails();
		sAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, semails);
		sCategories.setAdapter(sAdapter);

	}

	private List<String> getEmails() {
		String[] emails = { "这是第一条邮件", "这是第二条邮件", "这是第三条邮件", "这是第四条邮件",
				"这是第五条邮件" };
		semails = Arrays.asList(emails);
		return semails;
	}
}
