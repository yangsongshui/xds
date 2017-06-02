package com.pgt.xds.utils.converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * <br />
 * created by CxiaoX at 2016/12/19 13:35.
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {


    public JsonResponseBodyConverter() {
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(value.string());
            return (T)jsonObj;
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }

    }
}
