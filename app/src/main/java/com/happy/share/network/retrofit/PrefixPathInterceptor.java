package com.happy.share.network.retrofit;

import android.text.TextUtils;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * desc 用于给HTTP请求中URL的path添加前缀 <br/>
 * author 陈阳 <br/>
 * date 2018/9/3 上午9:55 <br/>
 * since V mello 2.0.0 <br/>
 */
public class PrefixPathInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        String needPrefixPathStr = oldRequest.header("need_prefix_path");
        String prefixPath = oldRequest.header("prefix_path");
        if (TextUtils.isEmpty(needPrefixPathStr) || TextUtils.isEmpty(prefixPath)) {
            try {
                return chain.proceed(oldRequest);
            } catch (ConnectException e) {
            }
        }

        boolean needPrefixPath = Boolean.parseBoolean(needPrefixPathStr);
        if (!needPrefixPath) {
            return chain.proceed(oldRequest);
        }

        HttpUrl httpUrl = oldRequest.url();
        String url = httpUrl.toString();
        String path = httpUrl.encodedPath();
        url = url.replace(path, prefixPath + path);
        Request request = oldRequest
                .newBuilder()
                .removeHeader("need_prefix_path")
                .removeHeader("prefix_path")
                .url(url)
                .build();
        return chain.proceed(request);
    }
}
