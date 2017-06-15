package com.pgt.xds.club;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.connector.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.club.adapter.TroopsAdapter;
import com.pgt.xds.utils.Toastor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TroopsActivity extends BaseActivity implements OnItemCheckListener {


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

    TroopsAdapter adapter;
    Toastor toastor;
    List<String> mList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_troops;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        troopsRv.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        adapter = new TroopsAdapter(mList, this);
        troopsRv.setAdapter(adapter);
        toastor = new Toastor(this);
    }

    @Override
    protected void initData() {
        mList.add("123");
        mList.add("123");
        mList.add("123");
        mList.add("123");
        adapter.setItems(mList);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemCheckListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick({R.id.left_layout, R.id.right_layout, R.id.club_search_ll, R.id.recommend_one_ll, R.id.recommend_two_ll, R.id.recommend_three_ll, R.id.recommend_four_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                startActivity(new Intent(this, NewTroopsActivity.class));
                break;
            case R.id.club_search_ll:
                break;
            default:
                startActivity(new Intent(this,RecommendActivity.class).putExtra("type",1));
                break;
        }
    }

    @Override
    public void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position) {
        startActivity(new Intent(this,RecommendActivity.class).putExtra("type",0));
    }
}
