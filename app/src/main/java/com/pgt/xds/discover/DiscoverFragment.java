package com.pgt.xds.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pgt.xds.base.BaseFragment;
import com.pgt.xds.R;

import butterknife.OnClick;

/**
 * 发现Fragment
 * Created by zheng on 2017/5/4.
 */
public class DiscoverFragment extends BaseFragment {

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {
        return R.layout.discover_fragment;
    }


    @OnClick({R.id.discover_inquire, R.id.discover_jd, R.id.discover_mall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.discover_inquire:
                startActivity(new Intent(getActivity(), OfflineStoreActivity.class));
                break;
            case R.id.discover_jd:
                startActivity(new Intent(getActivity(), JDActivity.class));
                break;
            case R.id.discover_mall:
                break;
        }
    }
}
