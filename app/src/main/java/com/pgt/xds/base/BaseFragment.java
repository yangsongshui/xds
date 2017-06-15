package com.pgt.xds.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 *  基类Fragment,除特定Fragment之外所有Fragment都要继承基类
 * */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getName();
    protected View layout;
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(getContentView(), null);
        // 注解Fragment
        unbinder= ButterKnife.bind(this, layout);
        initData(layout, savedInstanceState);
        return layout;
    }

    protected abstract void initData(View layout, Bundle savedInstanceState);

    protected abstract int getContentView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
       // Log.e(TAG, "hidden:" + hidden);
    }
}
