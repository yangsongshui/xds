package com.pgt.xds.my;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class CompilePhoneActivity extends BaseActivity implements TextWatcher {


    @BindView(R.id.compile_phone_edit)
    EditText compilePhoneEdit;
    @BindView(R.id.compile_code_edit)
    EditText compileCodeEdit;
    @BindView(R.id.countersign_btn)
    TextView countersignBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compile_phone;
    }

    @Override
    protected void initView() {
        countersignBtn.setEnabled(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        compilePhoneEdit.addTextChangedListener(this);
        compileCodeEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.compile_delete_image, R.id.compile_get_code_text, R.id.countersign_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.compile_delete_image:
                compilePhoneEdit.setText("");
                break;
            case R.id.compile_get_code_text:
                break;
            case R.id.countersign_btn:
                //确认
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String phone = compilePhoneEdit.getText().toString().trim();
        String verificationCode = compileCodeEdit.getText().toString().trim();
        if (phone.length() > 0 && verificationCode.length() > 0) {
            countersignBtn.setEnabled(true);
            countersignBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        } else {
            countersignBtn.setEnabled(false);
            countersignBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }

}
