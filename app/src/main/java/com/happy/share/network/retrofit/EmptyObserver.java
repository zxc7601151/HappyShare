package com.happy.share.network.retrofit;


/**
 * desc: 不做任何处理的Observer <br/>
 * author: 林佐跃 <br/>
 * date: 2018/7/2 <br/>
 * since V 1.3.2 <br/>
 */
public class EmptyObserver<T> extends BaseObserver<T>{

    @Override
    protected void onResponse(boolean isSuccess, T t) {

    }
}
