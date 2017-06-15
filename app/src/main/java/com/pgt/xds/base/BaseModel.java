package com.pgt.xds.base;


import com.pgt.xds.helper.RetrofitManager;

/**
 * 描述：业务对象的基类
 */
public class BaseModel {
    //retrofit请求数据的管理类
    public RetrofitManager retrofitManager;

    public BaseModel() {
        retrofitManager = RetrofitManager.builder();
    }
}
