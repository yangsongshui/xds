package com.pgt.xds.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;
import com.pgt.xds.my.adapter.HistoryAdapter;
import com.pgt.xds.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.history_rv)
    RecyclerView historyRv;
    @BindView(R.id.history_month_tv)
    TextView historyMonthTv;
    HistoryAdapter adapter;
    List<String> mList;
    private TimePickerView timePickerView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historyRv.setLayoutManager(layoutManager);
        mList=new ArrayList<>();
        adapter = new HistoryAdapter(mList, this);
        historyRv.setAdapter(adapter);
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String month=DateUtil.getMonth(date)+getResources().getString(R.string.history_month);
                historyMonthTv.setText(month);

            }
        }).setType(new boolean[]{true,true,false,false,false,false})
                .build();
    }

    @Override
    protected void initData() {
        mList.add("123");
        mList.add("123");
        mList.add("123");
        mList.add("123");
        adapter.setItems(mList);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.left_layout, R.id.history_month_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.history_month_tv:
                timePickerView.show();
                break;
        }
    }
}
