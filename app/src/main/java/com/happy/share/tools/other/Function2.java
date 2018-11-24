package com.happy.share.tools.other;

/**
 * desc: {@link java.util.function.Function}乞丐版 <br/>
 * time: 2018/9/19 下午4:14 <br/>
 * author: Logan <br/>
 * since V 1.2.0.3 <br/>
 */
public interface Function2<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);

}