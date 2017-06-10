package com.pgt.xds.connector;

import com.amap.api.services.weather.LocalWeatherLiveResult;

/**
 * Created by omni20170501 on 2017/6/10.
 */

public interface OnWeatherListener {
    void OnWeather(LocalWeatherLiveResult localWeatherLiveResult);
}
