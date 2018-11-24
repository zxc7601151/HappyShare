package com.happy.share.tools;

import android.support.annotation.Nullable;

import java.util.Map;

/**
 * desc: Map工具类 <br/>
 * time: 2018/7/19 下午2:56 <br/>
 * author: Logan <br/>
 * since V 1.0 <br/>
 */
public interface ToolMap {

    /**
     * Map是否为空（null 或 size==0）。
     *
     * @param map 源map数据
     * @return true：为空
     */
    static <KEY, VALUE> boolean isNullOrEmpty(@Nullable Map<KEY, VALUE> map) {
        return (map == null || map.size() == 0);
    }

    /**
     * map中的元素个数是否大于0。
     *
     * @param map 源map数据
     * @return true：是
     */
    static <KEY, VALUE> boolean isNotEmpty(@Nullable Map<KEY, VALUE> map) {
        return !isNullOrEmpty(map);
    }

    /**
     * 获取Map中元素个数。
     *
     * @param map 源map数据
     * @return 元素个数
     */
    static <KEY, VALUE> int getSize(@Nullable Map<KEY, VALUE> map) {
        return map == null ? 0 : map.size();
    }

    /**
     * 将源Map中的元素 复制到 目标Map集合中。
     *
     * @param sourceMap 数据源
     * @param destMap   目的源
     */
    static <KEY, VALUE> void copy(@Nullable Map<KEY, VALUE> sourceMap, @Nullable Map<KEY, VALUE> destMap) {
        if (isNotEmpty(sourceMap) && destMap != null) {
            destMap.putAll(sourceMap);
        }
    }

    /**
     * 将非null key和value 添加到 map中
     *
     * @param map   源数据
     * @param key   key值
     * @param value value值
     */
    static <KEY, VALUE> void putNotNull(@Nullable Map<KEY, VALUE> map, @Nullable KEY key, @Nullable VALUE value) {
        if ((map != null) && (key != null) && (value != null)) {
            map.put(key, value);
        }
    }

    /**
     * 清除map中的元素
     *
     * @param map map数据
     */
    static <KEY, VALUE> void clear(@Nullable Map<KEY, VALUE> map) {
        if (isNotEmpty(map)) {
            map.clear();
        }
    }

}
