package com.happy.share.network.retrofit;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * desc: Retrofit管理类 <br/>
 * time: 2017/10/23 10:55 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */
public class RetrofitManager {

    private Retrofit mRetrofit;
    private static RetrofitManager mRetrofitManager;

    public static RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    /**
     * 获取RetrofitManager单例
     *
     * @return 单例
     */
    private RetrofitManager() {
        // TODO: 2018/11/24 baseUrl
        mRetrofit = new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getClient())
                .baseUrl("")
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * @param service 服务接口
     * @return T
     */
    public <T> T createService(final Class<T> service) {
        return mRetrofit.create(service);
    }

}