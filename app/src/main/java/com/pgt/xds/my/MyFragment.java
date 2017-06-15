package com.pgt.xds.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.base.BaseFragment;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的Fragment
 * Created by zheng on 2017/5/4.
 */
public class MyFragment extends BaseFragment {


    @BindView(R.id.my_pic)
    ImageView myPic;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_grade)
    ImageView myGrade;
    @BindView(R.id.my_km)
    TextView myKm;
    @BindView(R.id.my_time)
    TextView myTime;
    @BindView(R.id.my_calorie)
    TextView myCalorie;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {
        return R.layout.my_fragment;
    }


    @OnClick({R.id.compile_info_bt, R.id.my_history_bt, R.id.my_statistics_bt, R.id.my_bicycle_bt, R.id.my_wallet_bt, R.id.my_indent_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.compile_info_bt://点击编辑信息
                startActivity(new Intent(getActivity(), CompileInfoActivity.class));
                break;
            case R.id.my_history_bt://点击历史记录
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
            case R.id.my_statistics_bt://骑行统计
                startActivity(new Intent(getActivity(), StatisticsActivity.class));
                break;
            case R.id.my_bicycle_bt://我的单车
                startActivity(new Intent(getActivity(), BicycleActivity.class));
                break;
            case R.id.my_wallet_bt://我的钱包
                break;
            case R.id.my_indent_bt://我的订单
                break;
        }
    }
}
