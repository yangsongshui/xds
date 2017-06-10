package com.pgt.xds.riding;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    @BindView(R.id.run_fragment_temperature)
    TextView runFragmentTemperature;
    @BindView(R.id.left_instrument_current_value)
    TextView leftInstrumentCurrentValue;
    @BindView(R.id.center_instrument_current_value)
    TextView centerInstrumentCurrentValue;
    @BindView(R.id.right_instrument_current_value)
    TextView rightInstrumentCurrentValue;
    @BindView(R.id.run_time_tv)
    TextView runTimeTv;
    @BindView(R.id.run_linear_tv)
    TextView runLinearTv;
    @BindView(R.id.run_mileage_tv)
    TextView runMileageTv;
    @BindView(R.id.run_calorie_tv)
    TextView runCalorieTv;
    @BindView(R.id.riding_begin)
    RelativeLayout ridingBegin;
    @BindView(R.id.pause_ll)
    LinearLayout pauseLl;





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


    @OnClick({R.id.riding_begin, R.id.run_pause, R.id.run_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.riding_begin:
                pauseLl.setVisibility(View.VISIBLE);
                ridingBegin.setVisibility(View.GONE);
                break;
            default:
                pauseLl.setVisibility(View.GONE);
                ridingBegin.setVisibility(View.VISIBLE);
                break;
        }
    }


}
