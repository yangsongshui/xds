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
import android.widget.Toast;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.MainActivity;
import com.pgt.xds.R;
import com.pgt.xds.utils.CommonUtils;

/**
 * 登录界面
 * Created by zheng on 2017/5/2.
 */
public class LoginActivity extends BaseActivity implements TextWatcher{


    private EditText phoneEdit;
    private EditText passwordEdit;
    private ImageView showPasswordImage;
    private TextView loginBtn;

    private boolean showAndHindPassword;


    @Override
    protected int getContentViewId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {

        phoneEdit = (EditText) findViewById(R.id.login_phone_edit);
        passwordEdit = (EditText) findViewById(R.id.login_password_edit);
        showPasswordImage = (ImageView) findViewById(R.id.show_password_image);
        loginBtn = (TextView) findViewById(R.id.login_btn);
        loginBtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.register_text_click).setOnClickListener(this);
        findViewById(R.id.show_password_image_layout).setOnClickListener(this);
        findViewById(R.id.login_delete_image).setOnClickListener(this);
        findViewById(R.id.forget_password_text).setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        phoneEdit.addTextChangedListener(this);
        passwordEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_text_click://注册点击事件
                startActivity(new Intent(this,RegisterOneActivity.class));
                break;
            case R.id.login_delete_image://删除按钮点击事件
                phoneEdit.setText("");
                break;
            case R.id.show_password_image_layout://显示或隐藏密码点击事件
                if (!showAndHindPassword){
                    showPasswordImage.setImageResource(R.drawable.show_password);
                    showAndHindPassword = true;
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    showPasswordImage.setImageResource(R.drawable.hind_password);
                    showAndHindPassword = false;
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                passwordEdit.setSelection(passwordEdit.getText().length());
                break;
            case R.id.forget_password_text://忘记密码点击事件
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.login_btn://登录按钮点击事件
                String phone = phoneEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                if (!CommonUtils.isNumber(phone) || phone.length() != 11){
                    Toast.makeText(this,getResources().getString(R.string.phone_incorrectness),Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
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
        String password = passwordEdit.getText().toString().trim();
        if (phone.length() > 0 && password.length() > 0){
            loginBtn.setEnabled(true);
            loginBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        }else{
            loginBtn.setEnabled(false);
            loginBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }
}
