package com.happy.share.network;

import com.happy.share.ApplicationBase;

/**
 * desc: HTTP请求异常类 <br/>
 * time: 2018/9/3 12:00 <br/>
 * author: 钟宾 <br/>
 * since V mello 2.0.0 <br/>
 */
public class ApiException extends RuntimeException {
    private int mCode;
    private String mMsg;

    public ApiException(int resultCode, String msg) {
        this(msg);
        this.mCode = resultCode;
        this.mMsg = msg;
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public void apiExceptionCode(int code) {
        if (ApplicationBase.getInstance() != null){
//            ApplicationBase.getInstance().exceptionDo(code,mMsg);
        }
    }
}
