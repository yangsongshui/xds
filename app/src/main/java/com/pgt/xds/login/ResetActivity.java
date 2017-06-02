package com.pgt.xds.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

/**
 * 重置密码界面
 * Created by zheng on 2017/5/3.
 */
public class ResetActivity extends BaseActivity implements TextWatcher{


    private EditText resetEdit;
    private EditText againEdit;
    private TextView completeBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.reset_activity;
    }

    @Override
    protected void initView() {
        resetEdit = (EditText) findViewById(R.id.reset_password_edit);
        againEdit = (EditText) findViewById(R.id.again_input_edit);
        completeBtn = (TextView) findViewById(R.id.complete_btn);
        completeBtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.reset_delete_image).setOnClickListener(this);
        completeBtn.setOnClickListener(this);
        resetEdit.addTextChangedListener(this);
        againEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_back:
                finish();
                break;
            case R.id.reset_delete_image://删除按钮点击事件
                resetEdit.setText("");
                break;
            case R.id.complete_btn://完成按钮点击事件
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String password = resetEdit.getText().toString().trim();
        String againPassword = againEdit.getText().toString().trim();
        if (password.length() > 0 && againPassword.length() > 0){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }
}
