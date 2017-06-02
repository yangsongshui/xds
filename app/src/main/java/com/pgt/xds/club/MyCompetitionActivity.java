package com.pgt.xds.club;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCompetitionActivity extends BaseActivity {


    @BindView(R.id.my_competition_rv)
    RecyclerView myCompetitionRv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_competition;
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
