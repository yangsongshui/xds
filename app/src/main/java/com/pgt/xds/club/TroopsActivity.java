package com.pgt.xds.club;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TroopsActivity extends BaseActivity {


    @BindView(R.id.recommend_one_iv)
    ImageView recommendOneIv;
    @BindView(R.id.recommend_one_tv)
    TextView recommendOneTv;
    @BindView(R.id.recommend_two_iv)
    ImageView recommendTwoIv;
    @BindView(R.id.recommend_two_tv)
    TextView recommendTwoTv;
    @BindView(R.id.recommend_three_iv)
    ImageView recommendThreeIv;
    @BindView(R.id.recommend_three_tv)
    TextView recommendThreeTv;
    @BindView(R.id.recommend_four_iv)
    ImageView recommendFourIv;
    @BindView(R.id.recommend_four_tv)
    TextView recommendFourTv;
    @BindView(R.id.troops_rv)
    RecyclerView troopsRv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_troops;
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

    @OnClick({R.id.left_layout, R.id.right_layout, R.id.club_search_ll, R.id.recommend_one_ll, R.id.recommend_two_ll, R.id.recommend_three_ll, R.id.recommend_four_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                break;
            case R.id.right_layout:
                startActivity(new Intent(this, NewTroopsActivity.class));
                break;
            case R.id.club_search_ll:
                break;
            case R.id.recommend_one_ll:
                break;
            case R.id.recommend_two_ll:
                break;
            case R.id.recommend_three_ll:
                break;
            case R.id.recommend_four_ll:
                break;
        }
    }
}
