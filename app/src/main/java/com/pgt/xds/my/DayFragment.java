package com.pgt.xds.my;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pgt.xds.BaseFragment;
import com.pgt.xds.R;
import com.pgt.xds.view.MyMarkerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends BaseFragment implements OnChartValueSelectedListener {

    @BindView(R.id.day_chart)
    LineChart mChart;

    public DayFragment() {
        // Required empty public constructor
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

        //设置透明度
        // mChart.setAlpha(0.8f);
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
       // mChart.set
        //隐藏Y轴
        mChart.getAxisRight().setEnabled(true);
        mChart.getAxisRight().setDrawGridLines(false);
        //不画网格
        // mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMaximum(25);
        mChart.getAxisLeft().setLabelCount(5, false);

        //  mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getAxisLeft().setTextColor(R.color.gray1);
        mChart.getAxisLeft().setAxisMinimum(0);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(R.color.gray1);
        xAxis.setAxisMaximum(15);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        xAxis.setLabelCount(5);
        mChart.getLegend().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
        //data.setValueTypeface(mTfLight);

        mChart.setOnChartValueSelectedListener(this);
        mChart.setData(getdayData());

        mChart.invalidate();
    }

    private LineData getdayData() {


        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            String[] day = new String[]{"07:00", "08:00", "09:00", "10:00", "11:00"
                    , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return day[(int) value % day.length];
            }


        };
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
        mChart.getAxisLeft().setValueFormatter(formatter1);
        mChart.getAxisRight().setValueFormatter(formatter2);
        mChart.getXAxis().setValueFormatter(formatter);
        ArrayList<Entry> values1 = new ArrayList<>();
        values1.add(new Entry(0, 2));
        values1.add(new Entry(1, 4));
        values1.add(new Entry(2, 5));
        values1.add(new Entry(3, 16));
        values1.add(new Entry(4, 14));
        values1.add(new Entry(5, 12));
        values1.add(new Entry(6, 8));
        values1.add(new Entry(7, 6));
        values1.add(new Entry(8, 16));
        values1.add(new Entry(9, 2));
        values1.add(new Entry(10, 17));
        values1.add(new Entry(11, 25));
        values1.add(new Entry(12, 11));
        values1.add(new Entry(13, 17));
        values1.add(new Entry(14, 22));
        values1.add(new Entry(15, 21));


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

    }

    @Override
    public void onNothingSelected() {

    }
}
