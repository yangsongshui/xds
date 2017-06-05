package com.pgt.xds.my;

import android.view.View;
import android.widget.ExpandableListView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.history_rv)
    ExpandableListView historyRv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick(R.id.left_layout)
    public void onViewClicked() {
        finish();
    }
}
