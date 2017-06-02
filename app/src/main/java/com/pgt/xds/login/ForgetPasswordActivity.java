package com.pgt.xds.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

/**
 * 忘记密码界面
 * Created by zheng on 2017/5/3.
 */
public class ForgetPasswordActivity extends BaseActivity implements TextWatcher{


    private EditText numberEdit;
    private EditText verificationCodeEdit;
    private TextView getVerificationCode;
    private TextView nextBtn;


    @Override
    protected int getContentViewId() {
        return R.layout.forget_password_activity;
    }

    @Override
    protected void initView() {
        numberEdit = (EditText) findViewById(R.id.forget_phone_number_edit);
        verificationCodeEdit = (EditText) findViewById(R.id.forget_verification_code_edit);
        getVerificationCode = (TextView) findViewById(R.id.forget_get_verification_code_text);
        nextBtn = (TextView) findViewById(R.id.next_btn);
        nextBtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.forget_delete_image).setOnClickListener(this);
        getVerificationCode.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        numberEdit.addTextChangedListener(this);
        verificationCodeEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_back:
                finish();
                break;
            case R.id.forget_delete_image://删除按钮点击事件
                numberEdit.setText("");
                break;
            case R.id.forget_get_verification_code_text://获得验证码点击事件
                break;
            case R.id.next_btn://下一步点击事件
                startActivity(new Intent(this,ResetActivity.class));
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
        String phone = numberEdit.getText().toString().trim();
        String verificationCode = verificationCodeEdit.getText().toString().trim();
        if (phone.length() > 0 && verificationCode.length() > 0){
            nextBtn.setEnabled(true);
            nextBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        }else{
            nextBtn.setEnabled(false);
            nextBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }
}
