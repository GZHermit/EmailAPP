package com.example.likewind;

import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MILeftFragment extends Fragment {
	private final String LOG_TAG = "MILeftFragment";
	private final String Action = "com.example.likewind.fragment";
	private View mView;
	private View miaView;
	private ListView mCategories;
	private List<String> mDatas = Arrays.asList("�ռ���", "������", "����վ");
	private ListAdapter mAdapter;
	private DeleteFragment dFragment;
	private SendFragment sFragment;
	private ReceiveFragment rFragment;
	private LayoutInflater miaInflater;
	private FrameLayout miaFramelayout;

	// private RecyclerView mRecyclerView;
	// private EmailKindAdapter ekAdapter;
	// private RecyclerView.LayoutManager layoutManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			initView(inflater, container);
		}
		return mView;
	}

	/*
	 * ��1������һ�����������ListView��item�����ݡ�
	 * ��2��ͨ��ʵ��ArrayAdapter�Ĺ��캯��������һ��ArrayAdapter�Ķ���
	 * ��3��ͨ��ListView��setAdapter()������ArrayAdapter��
	 */

	public void initView(LayoutInflater inflater, ViewGroup container) {

		// ��ȡmia.xml�Ĳ����ļ�
		// miaInflater = LayoutInflater.from(getActivity());
		// miaView = miaInflater.inflate(R.layout.mia, container);
		// miaFramelayout = (FrameLayout) miaView
		// .findViewById(R.id.mia_framelayout_content);

		mView = inflater.inflate(R.layout.mia_left, container, false);
		mCategories = (ListView) mView
				.findViewById(R.id.mia_listview_categories);
		// ΪmCategories����Adapter��������
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mDatas);
		mCategories.setAdapter(mAdapter);
		mCategories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();

				Intent intent = new Intent();
				intent.setAction(Action);
				// ͨ���㲥�ķ�ʽ��MIActivity������Ϣ��mia��Framelayout�������¼���
				switch (position) {
				case 0:
					intent.putExtra("flag", 0);
					intent.putExtra("name", "�ռ���");
					getActivity().sendBroadcast(intent);
					break;
				case 1:
					intent.putExtra("flag", 1);
					intent.putExtra("name", "������");
					getActivity().sendBroadcast(intent);
					break;
				case 2:
					intent.putExtra("flag", 2);
					intent.putExtra("name", "����վ");
					getActivity().sendBroadcast(intent);

					break;
				default:
					break;
				}

			}

		});
	}
}
