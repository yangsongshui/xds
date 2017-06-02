package com.pgt.xds.utils.converter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * <br />
 * created by CxiaoX at 2016/12/19 11:59.
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE=MediaType.parse("application/json; charset=UTF-8");


    public JsonRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE,value.toString());
    }
}
