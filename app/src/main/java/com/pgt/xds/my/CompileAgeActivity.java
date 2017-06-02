package com.pgt.xds.my;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CompileAgeActivity extends BaseActivity {


    @BindView(R.id.my_age)
    TextView myAge;
    @BindView(R.id.compile_birthday)
    TextView compileBirthday;
    private TimePickerView timePickerView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compile_age;
    }

    @Override
    protected void initView() {
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                compileBirthday.setText(getTime(date));
                int age = -(DateUtil.yearDiff(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT), getTime(date)) - 1);
                myAge.setText(age + "");
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .build();
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

    @OnClick({R.id.left_layout, R.id.my_age_ll, R.id.compile_birthday_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.my_age_ll:
            case R.id.compile_birthday_ll:
                timePickerView.show();
                break;
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
