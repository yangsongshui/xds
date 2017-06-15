package com.pgt.xds.login.model;

import android.content.Context;

import com.pgt.xds.api.XdsServiceApi;
import com.pgt.xds.base.BaseModel;
import com.pgt.xds.base.IBaseRequestCallBack;
import com.pgt.xds.bean.Msg;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * 描述：MVP中的M实现类，处理业务逻辑数据
 */
public class MsgModelImp extends BaseModel implements MsgModel<Msg> {

    private Context context = null;
    private XdsServiceApi weatherServiceApi;
    private CompositeSubscription mCompositeSubscription;

    public MsgModelImp(Context mContext) {
        super();
        context = mContext;
        weatherServiceApi = retrofitManager.getService();
        mCompositeSubscription = new CompositeSubscription();

    }

    @Override
    public void loadWeather(Map<String, String> map, final IBaseRequestCallBack<Msg> iBaseRequestCallBack) {
        mCompositeSubscription.add(weatherServiceApi.loadLoginInfo(map)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())   //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<Msg>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(Msg msg) {
                        iBaseRequestCallBack.requestSuccess(msg);

                    }

                })
        );
    }

    @Override
    public void onUnsubscribe() {
        //判断状态
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.clear();  //注销
            mCompositeSubscription.unsubscribe();
        }
    }
}
