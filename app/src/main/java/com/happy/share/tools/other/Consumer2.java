package com.happy.share.tools.other;

/**
 * desc: {@link java.util.function.Consumer}乞丐版 <br/>
 * time: 2018/8/30 下午5:26 <br/>
 * author: Logan <br/>
 * since V 1.2 <br/>
 */
public interface Consumer2<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

}