package com.pgt.xds.login.model;

import com.pgt.xds.base.IBaseRequestCallBack;

import java.util.Map;


/**
 * 描述：MVP中的M；处理获取网络天气数据
 * 作者：dc on 2017/2/16 11:03
 * 邮箱：597210600@qq.com
 */
public interface MsgModel<T> {

    /**
     * @descriptoin	获取网络数据
     * @author	dc
     * @param map 请求参数集合
     * @param iBaseRequestCallBack 数据的回调接口
     * @date 2017/2/17 19:01
     */
    void loadWeather(Map<String, String> map, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @author
     * @param
     * @date 2017/2/17 19:02
     * @return
     */
    void onUnsubscribe();
}
