package com.pgt.xds.login;

import android.content.Intent;
import android.view.View;

import com.pgt.xds.MainActivity;
import com.pgt.xds.R;
import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.utils.Toastor;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 登录选择界面
 * Created by zheng on 2017/5/2.
 */
public class LoginSelectActivity extends BaseActivity {
    UMShareAPI mShareAPI;
    Toastor toastor;

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
        mShareAPI = UMShareAPI.get(this);
        toastor = new Toastor(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_number_btn_layout://手机号登录
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.wei_xin_btn_layout://微信登录
                mShareAPI.getPlatformInfo(LoginSelectActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.qq_btn_layout://QQ登录
                mShareAPI.getPlatformInfo(LoginSelectActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            toastor.showSingletonToast(platform + " 信息获取成功");
            startActivity(new Intent(LoginSelectActivity.this, MainActivity.class));
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            toastor.showSingletonToast(platform + " 信息获取失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            toastor.showSingletonToast(platform + " 登陆取消");
        }
    };
}
