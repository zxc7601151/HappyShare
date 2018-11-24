package com.happy.share.tools;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * desc: Json工具类 <br/>
 * time: 2018/8/8 <br/>
 * author 杨斌才 <br/>
 * since V 1.2 <br/>
 */
public interface ToolJson {

    /**
     * list to json
     *
     * @param list the list that will transform to json string
     * @return the json string of list transform
     */
    @NonNull
    static String list2Json(@Nullable List list) {
        return JSON.toJSONString(list);
    }

    /**
     * map to json
     *
     * @param map the map that will transform to json string
     * @return the json string of map transform
     */
    @NonNull
    static String map2Json(@Nullable Map map) {
        return JSONObject.toJSONString(map);
    }

    /**
     * object array to json
     *
     * @param objects the object array that will transform to json string
     * @return the json string of array transform
     */
    @NonNull
    static String array2Json(@Nullable Object[] objects) {
        return JSON.toJSONString(objects);
    }

    /**
     * object to json
     *
     * @param object the object that will transform to json string
     * @return the json string of object
     */
    @NonNull
    static String object2Json(@Nullable Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * json to list
     *
     * @param json  the json string that will transform to list
     * @param clazz the class of the list's element
     * @param <T>   the generic of the class
     * @return the list that json string transform
     */
    @Nullable
    static <T> List<T> json2List(@Nullable String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * json to map
     *
     * @param json json string that will transform to map
     * @return the map fo json string
     */
    @Nullable
    static Map json2Map(@Nullable String json) {
        return JSONObject.parseObject(json);
    }

    /**
     * json string to object array
     *
     * @param json  the json string will transform to object array
     * @param clazz the class of the json will transform
     * @param ts    the real object array
     * @param <T>   the real object
     * @return the array of json string
     */
    @NonNull
    static <T> T[] json2Array(@Nullable String json, Class<T> clazz, @NonNull T[] ts) {
        List<T> list = JSON.parseArray(json, clazz);
        if (list == null) {
            return ts;
        }
        return list.toArray(ts);
    }

    /**
     * json string to object
     *
     * @param json  the json string that will transform to object
     * @param clazz the class that json will transform
     * @param <T>   the object class
     * @return the object of json string
     */
    @Nullable
    static <T> T json2Object(@Nullable String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


}
