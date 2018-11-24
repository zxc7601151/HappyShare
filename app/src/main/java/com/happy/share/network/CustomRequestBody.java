package com.happy.share.network;


import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.happy.share.ApplicationBase;
import com.happy.share.common.config.UserConfig;
import com.happy.share.network.retrofit.RequestParams;
import com.happy.share.tools.ToolApp;
import com.happy.share.tools.ToolJson;
import com.happy.share.tools.ToolText;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * desc: 自定义创建RequestBody
 * post请求专用 添加公共参数 <br/>
 * time: 2017/12/19 15:39 <br/>
 * author: Zpeak <br/>
 * since V1.0 <br/>
 */
public class CustomRequestBody {

    /**
     * bean参数创建json请求体
     *
     * @param requestParams 请求参数
     * @return
     */
    public static RequestBody create(@NonNull RequestParams requestParams) {

        HashMap map = transformRequest2Map(requestParams);
//        String signData = SignUtil.encryptMD5ToString(MapUtil.sortMap2String(map));
//        map.put(DefaultRequestConst.KEY_REQUEST_PARAM_SIGN, signData);
        String jsonSignString = ToolJson.map2Json(map);
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonSignString);
    }

    /**
     * 将请求参数转化为map
     *
     * @param requestParams
     */
    private static HashMap transformRequest2Map(@NonNull RequestParams requestParams) {
        String userId = UserConfig.getInstance().getUserId();
        if (ToolText.isNotEmpty(userId)) {
            requestParams.setUserId(userId);
        }

        requestParams.setAppTimestamp(String.valueOf(System.currentTimeMillis()));

//        requestParams.setCookieId(CookieManager.getInstance().getCookieId());

        requestParams.setAppVersion(ToolApp.getVersionName(ApplicationBase.getInstance()));
//        requestParams.setAppId(ApplicationBase.getInstance().getAppConfig().getAppId());
        String jsonString = ToolJson.object2Json(requestParams);
        return JSONObject.parseObject(jsonString, HashMap.class);
    }
}
