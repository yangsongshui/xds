package com.pgt.xds.club;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.R;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateActivity extends BaseActivity {


    @BindView(R.id.team_pic)
    ImageView teamPic;
    @BindView(R.id.team_name)
    TextView teamName;
    @BindView(R.id.team_lv)
    TextView teamLv;
    @BindView(R.id.team_id)
    TextView teamId;
    @BindView(R.id.team_news)
    SwitchButton teamNews;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_update;
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

    @OnClick({R.id.left_layout, R.id.team_member, R.id.team_entrance, R.id.team_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.team_member:
                startActivity(new Intent(this,MemberActivity.class));
                break;
            case R.id.team_entrance:
                break;
            case R.id.team_out:
                break;
        }
    }
}
