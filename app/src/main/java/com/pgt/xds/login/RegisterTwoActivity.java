package com.pgt.xds.login;

import android.app.ProgressDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.R;
import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.bean.Msg;
import com.pgt.xds.login.presenter.MsgPresenterImp;
import com.pgt.xds.login.view.MsgView;
import com.pgt.xds.utils.Toastor;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册第二个界面
 * Created by zheng on 2017/5/3.
 */
public class RegisterTwoActivity extends BaseActivity implements TextWatcher, MsgView {

    private EditText nicknameEdit;
    private ImageView manImage;
    private ImageView womanImage;
    private EditText birthdayEdit;
    private TextView completeBtn;
    /**
     * 对象定义
     **/
    private MsgPresenterImp weatherPresenterImp = null;
    private ProgressDialog progressDialog = null;
    private Toastor toastor;
    private int gender = 1;//性别类型 1：男 2：女

    @Override
    protected int getContentViewId() {
        return R.layout.register_two_activity;
    }

    @Override
    protected void initView() {
        nicknameEdit = (EditText) findViewById(R.id.register_nickname_edit);
        manImage = (ImageView) findViewById(R.id.man_select_image);
        womanImage = (ImageView) findViewById(R.id.woman_select_image);
        birthdayEdit = (EditText) findViewById(R.id.birthday_edit);
        completeBtn = (TextView) findViewById(R.id.complete_btn);
        completeBtn.setEnabled(false);
    }

    @Override
    protected void initData() {
        toastor = new Toastor(this);
        weatherPresenterImp = new MsgPresenterImp(this, this);
        progressDialog = new ProgressDialog(RegisterTwoActivity.this);
//        progressDialog.setTitle();
        progressDialog.setMessage("正在请求获取数据,请稍等!!!");
    }

    @Override
    protected void initListener() {
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.register_delete_image).setOnClickListener(this);
        manImage.setOnClickListener(this);
        womanImage.setOnClickListener(this);
        findViewById(R.id.skip_text).setOnClickListener(this);
        completeBtn.setOnClickListener(this);
        nicknameEdit.addTextChangedListener(this);
        birthdayEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.register_delete_image://删除按钮点击事件
                nicknameEdit.setText("");
                break;
            case R.id.man_select_image://男
                manImage.setImageResource(R.drawable.select);
                womanImage.setImageResource(R.drawable.unselect);
                gender = 1;
                break;
            case R.id.woman_select_image://女
                manImage.setImageResource(R.drawable.unselect);
                womanImage.setImageResource(R.drawable.select);
                gender = 2;
                break;
            case R.id.skip_text://跳过点击事件
                finish();
                break;
            case R.id.complete_btn://完成点击事件
                Map<String, String> map = new HashMap<>();
                map.put("phoneNumber", "12345678901");
                map.put("passWord", "123456");
                map.put("isThird", "0");
                map.put("role", "0");
                weatherPresenterImp.loadWeather(map);
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
        String nickname = nicknameEdit.getText().toString().trim();
        String birthday = birthdayEdit.getText().toString().trim();
        if (nickname.length() > 0 && birthday.length() > 0) {
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        } else {
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(Msg tData) {
        toastor.showSingletonToast(tData.getResMessage());
    }

    @Override
    public void loadDataError(Throwable throwable) {
        String errorMsg = throwable.getMessage();
        toastor.showSingletonToast(errorMsg);
        Log.e("TAG",errorMsg);
    }
}
