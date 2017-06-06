package com.pgt.xds.club;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.club.adapter.MyCompetitionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCompetitionActivity extends BaseActivity {


    @BindView(R.id.my_competition_rv)
    RecyclerView myCompetitionRv;

    MyCompetitionAdapter adapter;
    List<String> mList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_competition;
    }

    @Override
    protected void initView() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            myCompetitionRv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add("13");
        mList.add("13");
        mList.add("13");
        adapter = new MyCompetitionAdapter(mList, this);
        myCompetitionRv.setAdapter(adapter);
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
