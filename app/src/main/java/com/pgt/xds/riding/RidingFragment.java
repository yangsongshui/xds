package com.pgt.xds.riding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.pgt.xds.BaseFragment;
import com.pgt.xds.MainActivity;
import com.pgt.xds.R;
import com.pgt.xds.connector.OnWeatherListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 骑行Fragment
 * Created by zheng on 2017/5/4.
 */
public class RidingFragment extends BaseFragment implements View.OnClickListener, OnWeatherListener {

    @BindView(R.id.riding_current_time)
    TextView ridingCurrentTime;
    @BindView(R.id.riding_current_date)
    TextView ridingCurrentDate;
    @BindView(R.id.riding_current_week)
    TextView ridingCurrentWeek;
    @BindView(R.id.riding_weather_image)
    ImageView ridingWeatherImage;
    @BindView(R.id.riding_weather_description)
    TextView ridingWeatherDescription;
    @BindView(R.id.riding_sun_mileage_count)
    TextView ridingSunMileageCount;
    @BindView(R.id.riding_constant_speed_text)
    TextView ridingConstantSpeedText;
    @BindView(R.id.riding_max_speed_text)
    TextView ridingMaxSpeedText;
    @BindView(R.id.riding_total_time_text)
    TextView ridingTotalTimeText;
    @BindView(R.id.riding_temperature)
    TextView riding_temperature;



    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnWeatherListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.riding_fragment;
    }



    @Override
    public void OnWeather(LocalWeatherLiveResult localWeatherLiveResult) {
        ridingWeatherDescription.setText(localWeatherLiveResult.getLiveResult().getWeather());
        riding_temperature.setText(localWeatherLiveResult.getLiveResult().getTemperature() + "°");
        Log.e("天气数据", localWeatherLiveResult.getLiveResult().getWeather());
    }




    @OnClick(R.id.riding_begin)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), RidingStateActivity.class));
    }

    @Override
    public void onClick(View v) {

    }
}
