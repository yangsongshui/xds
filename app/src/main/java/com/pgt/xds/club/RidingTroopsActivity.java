package com.pgt.xds.club;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RidingTroopsActivity extends BaseActivity {


    @BindView(R.id.troops_pic_iv)
    ImageView troopsPicIv;
    @BindView(R.id.troops_name_tv)
    TextView troopsNameTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_riding_troops;
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


    @OnClick({R.id.left_layout, R.id.invite_bts, R.id.entrance_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.invite_bts:
                break;
            case R.id.entrance_btn:
                break;
        }
    }
}
