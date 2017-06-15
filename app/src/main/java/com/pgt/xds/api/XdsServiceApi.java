package com.pgt.xds.api;


import com.pgt.xds.bean.Msg;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * 描述：retrofit的接口service定义
 */
public interface XdsServiceApi {
    @POST("t_user_app/register?")
    Observable<Msg> loadLoginInfo(@QueryMap Map<String, String> map);


}
