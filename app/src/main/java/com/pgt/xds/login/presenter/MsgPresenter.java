package com.pgt.xds.login.presenter;

import java.util.Map;

/**
 * 描述：MVP中的P接口定义
 */
public interface MsgPresenter {

    /**
     * @descriptoin	请求天气数据
     * @author	dc
     * @param map<String,String> map
     * @date 2017/2/17 19:36
     * @return
     */
    void loadWeather(Map<String, String> map);

    /**
     * @descriptoin	注销subscribe
     * @author	dc
     * @date 2017/2/17 19:36
     */
    void unSubscribe();
}
