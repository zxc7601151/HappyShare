package com.happy.share.network;

/**
 * desc: 异常处理监听 <br/>
 * time: 2018/9/3 15:43 <br/>
 * author: 钟宾 <br/>
 * since V mello 2.0.0 <br/>
 */
public interface ExceptionListener {
    /**
     * 接口请求异常处理
     * @param code     服务端返的请求异常码
     * @param message  服务端返的请求异常信息
     */
    void exceptionDo(int code, String message);
}
