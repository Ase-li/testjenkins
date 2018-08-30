/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chd.chd56lc.net.GsonConverter;

import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.utils.Logger;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        T result = null;
        String responseString = value.string();
        try {
            BaseBean baseBean = gson.fromJson(responseString, BaseBean.class);
            if (baseBean.getStatus().getErrCode() == 200) {
                result = gson.fromJson(responseString, type);
            } else {
                baseBean.setData(null);
                result = (T) baseBean;
            }
            return result;
        } catch (Exception e) {
            Logger.d("网络请求数据解析异常", e.toString());
            String exceptionString = " { \"status\":{\"errCode\":209,\"message\":\"服务器繁忙,请稍候再试\"}}";
            return gson.fromJson(exceptionString, type);
        } finally {
            value.close();
        }
    }
}
