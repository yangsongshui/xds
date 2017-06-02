package com.pgt.xds.my;

import android.view.View;
import android.widget.RadioGroup;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class CompileSexActivity extends BaseActivity {
    @BindView(R.id.compile_sex)
    RadioGroup compileSex;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compile_sex;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        compileSex.check(R.id.sex_boy_rb);
    }

    @Override
    protected void initListener() {
        compileSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sex_boy_rb:
                        break;
                    case R.id.sex_girl_rb:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick(R.id.left_layout)
    public void onViewClicked() {
        finish();
    }
}
