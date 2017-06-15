package com.pgt.xds.login.presenter;

import android.content.Context;

import com.pgt.xds.base.BasePresenterImp;
import com.pgt.xds.bean.Msg;
import com.pgt.xds.login.model.MsgModelImp;
import com.pgt.xds.login.view.MsgView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 * 作者：dc on 2017/2/16 15:17
 * 邮箱：597210600@qq.com
 */
public class MsgPresenterImp extends BasePresenterImp<MsgView,Msg> implements MsgPresenter {
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private MsgModelImp msgPresenterImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/2/16 15:12
     */
    public MsgPresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.msgPresenterImp = new MsgModelImp(context);
    }

    @Override
    public void loadWeather(Map<String, String> map) {
        msgPresenterImp.loadWeather(map, this);
    }

    @Override
    public void unSubscribe() {

    }
}
