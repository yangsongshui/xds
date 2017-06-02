package com.pgt.xds.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.pgt.xds.R;

import java.util.List;


/**
 * Created by Administrator on 2016/5/28.
 */
public class MyMarkerView extends MarkerView {
    private TextView marker_time, marker_mileage;
    private List<Integer> time;

    public MyMarkerView(Context context, int layoutResource, List<Integer> time) {
        super(context, layoutResource);

        marker_time = (TextView) findViewById(R.id.marker_time);
        marker_mileage = (TextView) findViewById(R.id.marker_mileage);
        this.time = time;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            marker_mileage.setText(Utils.formatNumber(ce.getHigh(), 0, true));

        } else {

            marker_mileage.setText(Utils.formatNumber(e.getY(), 0, true));
        }
        marker_time.setText(time.get((int) e.getX()) + "");
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
