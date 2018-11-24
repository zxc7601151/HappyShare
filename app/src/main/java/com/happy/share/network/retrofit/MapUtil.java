package com.happy.share.network.retrofit;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * desc: map按照ASCII的顺序排序 <br/>
 * time:2018/1/18<br/>
 * author: 周峰 <br/>
 * since V 1.0 <br/>
 */

public class MapUtil {
    /**
     * 将接口请求参数的map排序，转化为字符串
     *
     * @param requestMap
     * @return
     */
    public static String sortMap2String(@NonNull Map<String, Object> requestMap) {

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(requestMap.entrySet());

        //排序方法
        Collections.sort(infoIds, (o1, o2) -> (o1.getKey()).toString().compareTo(o2.getKey()));
        StringBuffer sb = new StringBuffer();
//        sb.append(DefaultRequestConst.VALUE_APP_KEY);
        for (Map.Entry<String, Object> m : infoIds) {
            if (m.getValue() instanceof List){
                continue;
            }
            sb.append(m.getKey()).append(m.getValue());
        }
//        sb.append(DefaultRequestConst.VALUE_APP_KEY);
        return sb.toString();
    }
}
