package com.pgt.xds.login;

import android.content.Intent;
import android.view.View;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.R;

/**
 * 登录选择界面
 * Created by zheng on 2017/5/2.
 */
public class LoginSelectActivity extends BaseActivity{


    @Override
    protected int getContentViewId() {
        return R.layout.login_select_activity;
    }

    @Override
    protected void initView() {
        findViewById(R.id.phone_number_btn_layout).setOnClickListener(this);
        findViewById(R.id.wei_xin_btn_layout).setOnClickListener(this);
        findViewById(R.id.qq_btn_layout).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phone_number_btn_layout://手机号登录
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.wei_xin_btn_layout://微信登录
                break;
            case R.id.qq_btn_layout://QQ登录
                break;
        }
    }
}
