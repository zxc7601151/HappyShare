package com.happy.share.api.bean;

/**
 * desc: 服务器返回数据 包含data实体<br/>
 * time: 2017/9/11 下午7:03 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public class NetResponseWithData<T> extends NetResponse{


    private T data;

    public NetResponseWithData() {

    }

    /* getter and setter */

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}