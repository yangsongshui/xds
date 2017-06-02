package com.pgt.xds.club;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.OnClick;

public class CompetitionActivity extends BaseActivity {


    RecyclerView competitionRv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_competition;
    }

    @Override
    protected void initView() {
        competitionRv = (RecyclerView) findViewById(R.id.competition_rv);
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
                startActivity(new Intent(this, MyCompetitionActivity.class));
                break;
        }
    }

}
