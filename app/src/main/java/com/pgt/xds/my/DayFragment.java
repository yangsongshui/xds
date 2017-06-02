package com.pgt.xds.my;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.pgt.xds.BaseFragment;
import com.pgt.xds.R;
import com.pgt.xds.utils.DateUtil;
import com.pgt.xds.view.MyMarkerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends BaseFragment implements OnChartValueSelectedListener {
    private int DAY = 1;
    private int WEEK = 2;
    private int MONTH = 3;
    @BindView(R.id.day_chart)
    LineChart mChart;
    @BindView(R.id.run_mileage)
    TextView runMileage;
    @BindView(R.id.run_time)
    TextView runTime;

    List<Integer> time;
    List<Integer> data;
    int type = 1;

    public DayFragment(List<Integer> time, List<Integer> data, int type) {
        this.time = time;
        this.data = data;
        this.type = type;
    }

    public DayFragment() {

    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        initChart();
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_day;
    }

    private void initChart() {

        /**
         * ====================1.初始化-自由配置===========================
         */
        // 是否在折线图上添加边框
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(false);
        //设置是否可以缩放
        mChart.setScaleYEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(false);
        //设置四个边的间距
        mChart.setExtraOffsets(0, 30, 10, 0);
        mChart.getLegend().setEnabled(false);
        //设置XY轴
        initXYAxis();
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view, time);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setData(getdayData());

        mChart.invalidate();
    }

    private LineData getdayData() {
        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            values1.add(new Entry(i, data.get(i)));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values1, "");
        }
        set1.setLineWidth(1f);//设置线宽
        set1.setCircleRadius(5f);//设置焦点圆心的大小
        set1.setCircleHoleRadius(2f);
        set1.setCircleColorHole(Color.RED);
        set1.setCircleColor(Color.rgb(77, 175, 170));
        set1.setFillColor(Color.rgb(231, 239, 247));

        set1.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(false);  //设置有圆点
        set1.setDrawValues(false);  //不显示数据
        set1.setDrawHighlightIndicators(false);
        set1.setMode(LineDataSet.Mode.LINEAR); //设置为折线
        set1.setColor(Color.rgb(77, 175, 170));    //设置线的颜色

        return new LineData(set1);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        runMileage.setText( Utils.formatNumber(e.getY(), 0, true));
        runTime.setText(String.valueOf(time.get((int) e.getX())));
    }

    @Override
    public void onNothingSelected() {

    }

    private void initXYAxis() {
        YAxis mAxisLeft = mChart.getAxisLeft();
        YAxis mAxisRight = mChart.getAxisRight();
        XAxis xAxis = mChart.getXAxis();
        IAxisValueFormatter formatter = null;
        IAxisValueFormatter formatter1 = new IAxisValueFormatter() {
            String[] day1 = new String[]{"", "25km", "20km", "15km", "10km", "5km"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return day1[(int) value % day1.length];
            }


        };
        IAxisValueFormatter formatter2 = new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }


        };
        //设置坐标文字
        mAxisLeft.setValueFormatter(formatter1);
        mAxisRight.setValueFormatter(formatter2);
        //隐藏右边Y轴网格线
        mAxisRight.setDrawGridLines(false);
        //设置Y轴最大最小数据
        mAxisLeft.setAxisMaximum(25);
        mAxisLeft.setAxisMinimum(0);
        mAxisLeft.setLabelCount(5, false);
        //文字颜色
        mAxisLeft.setTextColor(R.color.gray1);
        xAxis.setTextColor(R.color.gray1);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        if (type == DAY) {
            Log.e("type", "DAY");
            xAxis.setAxisMaximum(15);
            xAxis.setLabelCount(5);
            formatter = new IAxisValueFormatter() {
                String[] day = new String[]{"07:00", "08:00", "09:00", "10:00", "11:00"
                        , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return day[(int) value % day.length];
                }


            };
        } else if (type == WEEK) {
            Log.e("type", "WEEK");
            xAxis.setAxisMaximum(6);
            formatter = new IAxisValueFormatter() {
                String[] day = new String[]{"SUN", "MON", "TUES", "WED", "THURS", "FRI", "SAT"};

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return day[(int) value % day.length];
                }


            };
        } else if (type == MONTH) {
            Log.e("type", "MONTH");
            MonthNext();
        }


        xAxis.setValueFormatter(formatter);
    }

    private void MonthNext() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        final List<String> mon = new ArrayList<>();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = DateUtil.getDaysOfMonth(year, month);
        for (int x = 1; x <= day; x++) {
            if (x < 10) {
                mon.add("0" + x + "号");
            } else {
                mon.add(x + "号");
            }
        }
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mon.get((int) value % mon.size());
            }


        };
        mChart.getXAxis().setValueFormatter(formatter);
    }
}
