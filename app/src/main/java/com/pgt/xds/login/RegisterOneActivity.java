package com.pgt.xds.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

/**
 * 注册第一个界面
 * Created by zheng on 2017/5/2.
 */
public class RegisterOneActivity extends BaseActivity implements TextWatcher{


    private EditText phoneEdit;
    private EditText verificationCodeEdit;
    private TextView getVerificationCode;
    private ImageView checkedImage;
    private TextView nextBtn;

    private boolean isChecked = true;

    @Override
    protected int getContentViewId() {
        return R.layout.register_one_activity;
    }

    @Override
    protected void initView() {

        phoneEdit = (EditText) findViewById(R.id.register_phone_edit);
        verificationCodeEdit = (EditText) findViewById(R.id.verification_code_edit);
        getVerificationCode = (TextView) findViewById(R.id.get_verification_code_text);
        checkedImage = (ImageView) findViewById(R.id.checked_image);
        nextBtn = (TextView) findViewById(R.id.next_btn);
        nextBtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.register_delete_image).setOnClickListener(this);
        checkedImage.setOnClickListener(this);
        findViewById(R.id.user_agreement_text_click).setOnClickListener(this);
        getVerificationCode.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        phoneEdit.addTextChangedListener(this);
        verificationCodeEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_back:
                finish();
                break;
            case R.id.register_delete_image://删除按钮点击事件
                phoneEdit.setText("");
                break;
            case R.id.checked_image://用户协议勾选点击事件
                if (isChecked){
                    checkedImage.setImageResource(R.drawable.unchecked);
                    isChecked = false;
                }else{
                    checkedImage.setImageResource(R.drawable.checked);
                    isChecked = true;
                }
                break;
            case R.id.user_agreement_text_click://用户协议点击事件
                break;
            case R.id.get_verification_code_text://获得验证码点击事件

                break;
            case R.id.next_btn://提交按钮
                startActivity(new Intent(this,RegisterTwoActivity.class));
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
        String phone = phoneEdit.getText().toString().trim();
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
