package com.pgt.xds.riding;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pgt.xds.BaseFragment;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 骑行开始与结束Fragment
 * Created by zheng on 2017/5/8.
 */
public class RunFragment extends BaseFragment {

    boolean isBegin;
    @BindView(R.id.run_begin)
    TextView runBegin;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected int getContentView() {
        return R.layout.run_fragment;
    }


    private void initData() {
        isBegin = true;
    }


    @OnClick({R.id.riding_begin})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.riding_begin:
                if (isBegin) {
                    isBegin = false;
                    runBegin.setText(R.string.run_pause);
                    Drawable drawable = getResources().getDrawable(R.drawable.pause);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    runBegin.setCompoundDrawables(drawable, null, null, null);
                } else {
                    isBegin = true;
                    runBegin.setText(R.string.run_begin);
                    Drawable drawable = getResources().getDrawable(R.drawable.begin);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    runBegin.setCompoundDrawables(drawable, null, null, null);
                }
                break;
        }
    }

}
