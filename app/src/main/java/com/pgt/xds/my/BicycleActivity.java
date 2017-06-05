package com.pgt.xds.my;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.my.adapter.BicycleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 我的单车界面
 * */
public class BicycleActivity extends BaseActivity {

    @BindView(R.id.bicycle_rl)
    RecyclerView bicycleRl;

    BicycleAdapter adapter;
    List<String> mList;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_bicycle;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bicycleRl.setLayoutManager(layoutManager);




    }

    @Override
    protected void initData() {
        mList=new ArrayList<>();
        mList.add("123");
        mList.add("123");
        mList.add("123");
        mList.add("123");
        adapter=new BicycleAdapter(mList,this);
        bicycleRl.setAdapter(adapter);
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
