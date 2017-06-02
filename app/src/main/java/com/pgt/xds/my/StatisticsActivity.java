package com.pgt.xds.my;

import android.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import butterknife.BindView;
import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.statistics_rg)
    RadioGroup statisticsRg;
    private DayFragment dayFragment; //今日数据
    private WeekFragment weekFragment; //本周数据
    private MonthFragment monthFragment;//本月数据

    @Override
    protected int getContentViewId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void initView() {
        statisticsRg.check(R.id.day);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        statisticsRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick(R.id.left_layout)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.day_rb:
                checkedFragment(0);
                break;
            case R.id.week_rb:
                checkedFragment(1);
                break;
            case R.id.month_rb:
                checkedFragment(2);
                break;

        }
    }

    /**
     * Fragment选中状态
     **/
    private void checkedFragment(int index) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        switch (index) {
            case 0://天

                if (dayFragment == null) {
                    dayFragment = new DayFragment();
                    fragmentTransaction.add(R.id.statistics_fl, dayFragment);
                } else {
                    fragmentTransaction.show(dayFragment);
                }
                break;
            case 1://周

                if (weekFragment == null) {
                    weekFragment = new WeekFragment();
                    fragmentTransaction.add(R.id.statistics_fl, weekFragment);
                } else {
                    fragmentTransaction.show(weekFragment);
                }
                break;
            case 2://月
                if (monthFragment == null) {
                    monthFragment = new MonthFragment();
                    fragmentTransaction.add(R.id.statistics_fl, monthFragment);
                } else {
                    fragmentTransaction.show(monthFragment);
                }
                break;

        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏Fragment
     **/
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (dayFragment != null) {
            fragmentTransaction.hide(dayFragment);
        }
        if (weekFragment != null) {
            fragmentTransaction.hide(weekFragment);
        }
        if (monthFragment != null) {
            fragmentTransaction.hide(monthFragment);
        }

    }
}
