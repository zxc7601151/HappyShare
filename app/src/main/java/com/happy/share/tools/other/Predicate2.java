package com.happy.share.tools.other;

/**
 * desc: {@link java.util.function.Predicate}乞丐版 <br/>
 * time: 2018/9/19 下午4:17 <br/>
 * author: Logan <br/>
 * since V 1.2.0.3 <br/>
 */
public interface Predicate2<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);

}
