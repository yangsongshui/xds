package com.pgt.xds.my;

import android.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.statistics_rg)
    RadioGroup statisticsRg;
    @BindView(R.id.statistics_time)
    TextView statisticsTime;
    @BindView(R.id.time_min)
    TextView timeMin;
    @BindView(R.id.time_km)
    TextView timeKm;
    @BindView(R.id.time_num)
    TextView timeNum;
    @BindView(R.id.time_call)
    TextView timeCall;
    @BindView(R.id.good_time)
    TextView goodTime;
    @BindView(R.id.good_km)
    TextView goodKm;
    @BindView(R.id.good_speed)
    TextView goodSpeed;
    @BindView(R.id.good_cal)
    TextView goodCal;
    private DayFragment dayFragment; //今日数据
    private DayFragment weekFragment; //本周数据
    private DayFragment monthFragment;//本月数据
    List<Integer> time;
    List<Integer> data;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void initView() {
        statisticsRg.check(R.id.day_rb);
        statisticsTime.setText(R.string.statistics_day);
    }

    @Override
    protected void initData() {
        time = new ArrayList<>();
        data = new ArrayList<>();
        checkedFragment(0);

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
                statisticsTime.setText(R.string.statistics_day);
                break;
            case R.id.week_rb:
                statisticsTime.setText(R.string.statistics_week);
                checkedFragment(1);
                break;
            case R.id.month_rb:
                statisticsTime.setText(R.string.statistics_month);
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
                initDayData(15);
                if (dayFragment == null) {
                    dayFragment = new DayFragment(time,data,1);
                    fragmentTransaction.add(R.id.statistics_fl, dayFragment);
                } else {
                    fragmentTransaction.show(dayFragment);
                }
                break;
            case 1://周
                initDayData(6);
                if (weekFragment == null) {
                    weekFragment = new DayFragment(time,data,2);

                    fragmentTransaction.add(R.id.statistics_fl, weekFragment);
                } else {
                    fragmentTransaction.show(weekFragment);
                }
                break;

            case 2://月
                initDayData(30);
                if (monthFragment == null) {
                    monthFragment = new DayFragment(time,data,3);
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

    private void initDayData(int date) {
        data.clear();
        time.clear();
        Random random = new Random();
        for (int i = 0; i <= date; i++) {
            data.add(random.nextInt(26));
            time.add(random.nextInt(300));
        }
    }

}
