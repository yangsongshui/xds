package com.pgt.xds.club;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class NewTroopsActivity extends BaseActivity {
    private static final int RESULT = 1;
    @BindView(R.id.next_btn)
    TextView nextBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_troops;
    }

    @Override
    protected void initView() {
        nextBtn.setEnabled(false);
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

    @OnClick({R.id.left_layout, R.id.add_pic_iv, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.add_pic_iv:
                nextBtn.setEnabled(true);
                nextBtn.setTextColor(getResources().getColor(R.color.white));
                nextBtn.setBackgroundResource(R.drawable.title_right_click_bg);
                break;
            case R.id.next_btn:
                startActivityForResult(new Intent(this, SetTroopsActivity.class), RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT) {
            startActivity(new Intent(this, RidingTroopsActivity.class));
            finish();
        }
    }
}
