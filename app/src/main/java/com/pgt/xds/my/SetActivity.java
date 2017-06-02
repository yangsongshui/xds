package com.pgt.xds.my;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

public class SetActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener {


    @BindView(R.id.set_gprs_tv)
    TextView setGprsTv;
    @BindView(R.id.set_gprs)
    SwitchButton setGprs;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.set_prevent_tv)
    TextView setPreventTv;
    @BindView(R.id.set_prevent)
    SwitchButton setPrevent;
    @BindView(R.id.set_voice_tv)
    TextView setVoiceTv;
    @BindView(R.id.set_voice)
    SwitchButton setVoice;
    @BindView(R.id.set_bright_tv)
    TextView setBrightTv;
    @BindView(R.id.set_bright)
    SwitchButton setBright;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        setGprs.setOnCheckedChangeListener(this);
        setPrevent.setOnCheckedChangeListener(this);
        setVoice.setOnCheckedChangeListener(this);
        setBright.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.set_seek, R.id.set_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.set_seek:
                startActivity(new Intent(this, SeekActivity.class
                ));
                break;
            case R.id.set_about:
                break;
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.set_gprs:
                break;
            case R.id.set_prevent:
                break;
            case R.id.set_voice:
                break;
            case R.id.set_bright:
                break;

        }
    }
}
