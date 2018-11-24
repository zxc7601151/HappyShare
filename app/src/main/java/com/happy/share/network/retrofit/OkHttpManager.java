package com.happy.share.network.retrofit;

import com.happy.share.ApplicationBase;
import com.happy.share.common.config.UserConfig;
import com.happy.share.network.logger.Level;
import com.happy.share.network.logger.LoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * desc: 网络请求管理 <br/>
 * time: 2017/10/23 10:01 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */
public class OkHttpManager {
    /**
     * 是否测试环境, true:是
     */
    private boolean mIsProductionEnvironment = true;
    private static  OkHttpClient mClient;
    private static OkHttpManager mOkHttpManager;


    private OkHttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ForbiddenInterceptor())
                .addInterceptor(new PrefixPathInterceptor());
        //非正式环境打开log
        if (mIsProductionEnvironment) {
//            builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addInterceptor(new LoggingInterceptor.Builder()
                    .loggable(true)
                    .setLevel(Level.BASIC)
                    .log(okhttp3.internal.platform.Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .build());
        }
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        mClient = builder.build();

    }

    /**
     * 获取OkHttpManager单例
     * @return
     */
    public static OkHttpManager getInstance() {
        if (mOkHttpManager == null){
            synchronized (OkHttpManager.class){
                if (mOkHttpManager == null){
                    mOkHttpManager = new OkHttpManager();
                }
            }
        }
        return mOkHttpManager;
    }
    public OkHttpClient getClient(){
        return mClient;
    }

    public static class ForbiddenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            final TimeWatch timeWatch = new TimeWatch();
            timeWatch.start();

            //-------------------------------------------------------------------------------------------------------------------------
            //begin 添加统一Header
            Request oldRequest = chain.request();
            HttpUrl httpUrl = oldRequest.url();
            String url = httpUrl.toString();
            String host = httpUrl.host();
            Request.Builder builder = oldRequest.newBuilder();
            builder.header("Accept", "application/json;charset=UTF-8");
            builder.header("Content-type", "application/json;charset=UTF-8");
            builder.header("token", UserConfig.getInstance().getUserToken());
            //end 添加统一Header
            //-------------------------------------------------------------------------------------------------------------------------

//            if (oldRequest.method().equals("GET")) {//Get请求添加公共参数
//                HttpUrl httpUrlGet = oldRequest.url()
//                        .newBuilder()
//                        .removeAllEncodedQueryParameters("appVersion")
//                        .addQueryParameter("appVersion", "7.2")
//                        .build();
//                builder.url(httpUrlGet).build();
//            }

            Request request = builder.build();


            Response response = chain.proceed(request);

            //-------------------------------------------------------------------------------------------------------------------------
            timeWatch.stop();
            double ms = timeWatch.getTotalTime(1);
            if (response != null) {
                if (ms >= 1000) {
                    //耗时过长
                }

                //异常code处理
                final int code = response.code();
                if (code != 200) {
//                  ToolLog.e("topvid", url + "访问错误响应：code" + code);
                }

//                ToolLog.e("topvid", "host==" + host);
            }
            //-------------------------------------------------------------------------------------------------------------------------
            return response;
        }
    }


}