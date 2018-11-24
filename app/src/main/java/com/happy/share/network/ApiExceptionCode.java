package com.happy.share.network;

/**
 * desc: 异常请求码枚举类（考虑到需要遍历，故不使用@Intdef） <br/>
 * time: 2018/9/3 12:01 <br/>
 * author: 钟宾 <br/>
 * since V mello 2.0.0 <br/>
 */
public enum ApiExceptionCode {
    /**
     * 重复登录
     */
    LOGIN_REPEAT(17),
    /**
     * 未知
     */
    UN_KNOW(-1);

    int exceptionCode;

    ApiExceptionCode(int code) {
        exceptionCode = code;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public static ApiExceptionCode getCode(int code){
        for (ApiExceptionCode contentType : ApiExceptionCode.values()) {
            if (contentType.getExceptionCode() == code) {
                return contentType;
            }
        }
        return ApiExceptionCode.UN_KNOW;
    }
}
