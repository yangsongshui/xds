package com.pgt.xds.my;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class BicycleActivity extends BaseActivity {


    @BindView(R.id.bicycle_rl)
    RecyclerView bicycleRl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bicycle;
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


    @OnClick({R.id.left_layout, R.id.right_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                startActivity(new Intent(this, AddActivity.class));
                break;
        }
    }
}
