package com.pgt.xds.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.R;
import com.pgt.xds.base.BaseActivity;

import static com.pgt.xds.utils.PicUtil.isMobileNO;

/**
 * 注册第一个界面
 * Created by zheng on 2017/5/2.
 */
public class RegisterOneActivity extends BaseActivity implements TextWatcher {


    private EditText phoneEdit,pswEdit;
    private EditText verificationCodeEdit;
    private TextView getVerificationCode;
    private ImageView checkedImage,showPasswordImage;
    private TextView nextBtn;
    private boolean showAndHindPassword = false;
    private boolean isChecked = true;

    @Override
    protected int getContentViewId() {
        return R.layout.register_one_activity;
    }

    @Override
    protected void initView() {

        phoneEdit = (EditText) findViewById(R.id.register_phone_edit);
        pswEdit = (EditText) findViewById(R.id.register_password_edit);
        verificationCodeEdit = (EditText) findViewById(R.id.verification_code_edit);
        getVerificationCode = (TextView) findViewById(R.id.get_verification_code_text);
        checkedImage = (ImageView) findViewById(R.id.checked_image);
        showPasswordImage = (ImageView) findViewById(R.id.show_password_image);
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
        findViewById(R.id.show_password_image_layout).setOnClickListener(this);
        checkedImage.setOnClickListener(this);
        findViewById(R.id.user_agreement_text_click).setOnClickListener(this);
        getVerificationCode.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        phoneEdit.addTextChangedListener(this);
        pswEdit.addTextChangedListener(this);
        verificationCodeEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.register_delete_image://删除按钮点击事件
                phoneEdit.setText("");
                break;
            case R.id.show_password_image_layout://显示或隐藏密码点击事件
                if (!showAndHindPassword) {
                    showPasswordImage.setImageResource(R.drawable.show_password);
                    showAndHindPassword = true;
                    pswEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showPasswordImage.setImageResource(R.drawable.hind_password);
                    showAndHindPassword = false;
                    pswEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                pswEdit.setSelection(pswEdit.getText().length());
                break;
            case R.id.checked_image://用户协议勾选点击事件
                if (isChecked) {
                    checkedImage.setImageResource(R.drawable.unchecked);
                    isChecked = false;
                } else {
                    checkedImage.setImageResource(R.drawable.checked);
                    isChecked = true;
                }
                break;
            case R.id.user_agreement_text_click://用户协议点击事件
                break;
            case R.id.get_verification_code_text://获得验证码点击事件
                String phone1 = phoneEdit.getText().toString().trim();
                if (isMobileNO(phone1)) {

                } else {
                    showToastor(getResources().getString(R.string.phone_incorrectness));
                }
                break;
            case R.id.next_btn://提交按钮
                String phone = phoneEdit.getText().toString().trim();
                String psw = pswEdit.getText().toString().trim();
                if (isChecked) {
                    if (isMobileNO(phone)&&psw.length()>5) {
                        startActivity(new Intent(this, RegisterTwoActivity.class).putExtra("phone", phone));
                        finish();
                    } else {
                        showToastor(getResources().getString(R.string.phone_incorrectness));
                    }
                } else {
                    showToastor(getResources().getString(R.string.register_check));
                }

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
        if (phone.length() > 0 && verificationCode.length() > 0) {
            nextBtn.setEnabled(true);
            nextBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        } else {
            nextBtn.setEnabled(false);
            nextBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }
}
