package jp.co.jpmobile.coolguidejapan.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragmentCommon extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		init();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		initListener();
		super.onActivityCreated(savedInstanceState);
	}

	public void init() {

	}

	public void initListener() {

	}

	public void initData() {

	}
	protected abstract View initView();

}
