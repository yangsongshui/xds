package com.pgt.xds;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * 基类Activity,除特定Activity之外所有Activity都要继承基类
 * Created by zheng on 2017/5/2.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**向主线程发消息**/
    public void sendMainMessage(Handler handler, int what, Object object){
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.obj = object;
        msg.sendToTarget();
    }

    protected abstract int getContentViewId();
    protected  void init(){}
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    public abstract void onClick(View view);
}
