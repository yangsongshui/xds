package com.pgt.xds.utils;

import com.pgt.xds.utils.converter.JsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 网络请求类
 * Created by zheng on 2017/3/30.
 */
public class RetrofitHttp {

    public static final String BASE_URL = "http://coollinebike.com:8088/CoolBike/app/";//外网
    //public static final String BASE_URL = "http://192.168.100.44:8080/CoolBike/app/";//本地
    public static final String OTHER_URL = "http://coollinebike.com:8088/CoolBike/";//外网其他URL
    //public static final String OTHER_URL = "http://192.168.100.44:8080/CoolBike/";//本地其他URL

    private static RetrofitHttp retrofitHttp;
    private Retrofit retrofit;

    public static synchronized RetrofitHttp getInstance(){
        if (retrofitHttp == null){
            retrofitHttp = new RetrofitHttp();
        }
        return retrofitHttp;
    }

    public RetrofitHttp(){
        initRetrofit();
    }

    public static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JsonConverterFactory.create())
                .build();
    }
    public <T>T  create(Class<T> reqServer){
        return retrofit.create(reqServer);
    }
}
