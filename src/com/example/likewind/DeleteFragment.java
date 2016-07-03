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

public class DeleteFragment extends Fragment {
	private View dView;
	private ListView dCategories;
	private ListAdapter dAdapter;
	private List<String> demails;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (dView == null) {
			initView(inflater, container);
		}
		return dView;
	}

	public void initView(LayoutInflater inflater, ViewGroup container) {
		// ((TextView)getActivity().findViewById(R.id.mia_text_title)).setText("发件箱");
		dView = inflater.inflate(R.layout.receive, container, false);
		dCategories = (ListView) dView
				.findViewById(R.id.recevie_listview_rcategories);
		getEmails();
		dAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, demails);
		dCategories.setAdapter(dAdapter);

	}

	private List<String> getEmails() {
		String[] emails = { "这是第一条邮件", "这是第二条邮件", "这是第三条邮件", "这是第四条邮件",
				"这是第五条邮件" };
		demails = Arrays.asList(emails);
		return demails;
	}
}
