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
	private List<String> mDatas = Arrays.asList("收件箱", "发件箱", "回收站");
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
	 * （1）定义一个数组来存放ListView中item的内容。
	 * （2）通过实现ArrayAdapter的构造函数来创建一个ArrayAdapter的对象。
	 * （3）通过ListView的setAdapter()方法绑定ArrayAdapter。
	 */

	public void initView(LayoutInflater inflater, ViewGroup container) {

		// 获取mia.xml的布局文件
		// miaInflater = LayoutInflater.from(getActivity());
		// miaView = miaInflater.inflate(R.layout.mia, container);
		// miaFramelayout = (FrameLayout) miaView
		// .findViewById(R.id.mia_framelayout_content);

		mView = inflater.inflate(R.layout.mia_left, container, false);
		mCategories = (ListView) mView
				.findViewById(R.id.mia_listview_categories);
		// 为mCategories设置Adapter来绑定数据
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
				// 通过广播的方式向MIActivity传递消息对mia的Framelayout进行重新加载
				switch (position) {
				case 0:
					intent.putExtra("flag", 0);
					intent.putExtra("name", "收件箱");
					getActivity().sendBroadcast(intent);
					break;
				case 1:
					intent.putExtra("flag", 1);
					intent.putExtra("name", "发件箱");
					getActivity().sendBroadcast(intent);
					break;
				case 2:
					intent.putExtra("flag", 2);
					intent.putExtra("name", "回收站");
					getActivity().sendBroadcast(intent);

					break;
				default:
					break;
				}

			}

		});
	}
}
