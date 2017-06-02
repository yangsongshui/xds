package com.pgt.xds.my;

import android.view.View;
import android.widget.EditText;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class CompileNameActivity extends BaseActivity {


    @BindView(R.id.compile_name_et)
    EditText compileNameEt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compile_name;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.compile_delete_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.compile_delete_image:
                compileNameEt.setText("");
                break;
        }
    }
}
