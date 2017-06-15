package com.pgt.xds.club;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SetTroopsActivity extends BaseActivity implements TextWatcher {
    private static final int RESULT = 2;
    @BindView(R.id.set_name_ed)
    EditText setNameEd;
    @BindView(R.id.troops_introduce_ed)
    EditText troopsIntroduceEd;
    @BindView(R.id.set_btn)
    TextView setBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_troops;
    }

    @Override
    protected void initView() {
        setBtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        setNameEd.addTextChangedListener(this);
        troopsIntroduceEd.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.set_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.set_btn:
                setResult(RESULT);
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
    public void afterTextChanged(Editable s) {
        String phone = setNameEd.getText().toString().trim();
        String password = troopsIntroduceEd.getText().toString().trim();
        if (phone.length() > 0 && password.length() > 0) {
            setBtn.setEnabled(true);
            setBtn.setBackgroundResource(R.drawable.title_right_click_bg);
        } else {
            setBtn.setEnabled(false);
            setBtn.setBackgroundResource(R.color.btn_no_click);
        }
    }
}
