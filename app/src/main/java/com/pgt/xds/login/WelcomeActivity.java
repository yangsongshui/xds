package com.pgt.xds.login;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.R;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

/**
 * 启动页
 * Created by zheng on 2017/5/2.
 */
public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler();
    private Runnable myRunnable;
    private final static int REQUECT_CODE_COARSE = 1;

    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MPermissions.requestPermissions(WelcomeActivity.this, REQUECT_CODE_COARSE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.welcome_activity;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_COARSE)
    public void requestSdcardSuccess() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginSelectActivity.class));
                finish();
            }
        }, 3000);
        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_COARSE)
    public void requestSdcardFailed() {
        Toast.makeText(this, "定位权限获取失败", Toast.LENGTH_SHORT).show();


    }
}
