package com.happy.share.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.happy.share.ApplicationBase;
import com.happy.share.common.config.UserConfig;
import com.happy.share.network.retrofit.MapUtil;
import com.happy.share.network.retrofit.SignUtil;
import com.happy.share.tools.ToolApp;
import com.happy.share.tools.ToolText;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * desc: 网络请求辅助类 <br/>
 * time: 2018/3/31 <br/>
 * author: Vincent <br/>
 * since V1.0.0 <br/>
 */

public class NetHelper {
    private static FileUploadObserver mFileUploadObserver;
    /**
     * 添加单张图片
     *
     * @param name
     * @param file
     * @param extraParamsMap
     * @return
     */
    public static List<MultipartBody.Part> createPartForOne(@NonNull String name, @NonNull File file
            , @Nullable HashMap<String, Object> extraParamsMap) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(name, file.getName(), fileBody);

        addOtherParams(extraParamsMap, builder);

        return builder.build().parts();
    }

    /**
     * 创建part
     *
     * @param map            需要上传的文件
     * @param extraParamsMap 其他参数
     * @return
     */
    public static List<MultipartBody.Part> createPart(@NonNull HashMap<String, File> map
            , @Nullable HashMap<String, Object> extraParamsMap,boolean isNeedProgress) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Map.Entry<String, File> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            File file = entry.getValue();
            if ("video".equals(entry.getKey())) {
                builder.addFormDataPart(entry.getKey(), file.getName(), creatRequestBody(file,isNeedProgress));
            }else{
                builder.addFormDataPart(entry.getKey(), file.getName(), creatRequestBody(file,false));
            }
        }

        addOtherParams(extraParamsMap, builder);

        return builder.build().parts();
    }

    private static RequestBody creatRequestBody(@NonNull File file,boolean isNeedProgress){
        RequestBody fileBody = null;
        if (isNeedProgress){
            fileBody = new UploadFileRequestBody(file,mFileUploadObserver);
        }else{
            fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
        return fileBody;
    }

    /**
     * 添加其他参数(包括公共参数)
     */
    private static void addOtherParams(HashMap<String, Object> extraParamsMap, MultipartBody.Builder builder) {
        if (extraParamsMap == null) {
            extraParamsMap = new HashMap<>();
        }
        //添加公共参数
        addCommonParams(extraParamsMap);
        //将参数添加进builder
        for (Map.Entry<String, Object> entry : extraParamsMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            builder.addFormDataPart(entry.getKey(), entry.getValue() + "");
        }
    }


    /**
     * 添加公共参数
     */
    public static void addCommonParams(@NonNull HashMap<String, Object> paramsMap) {
        String userId = UserConfig.getInstance().getUserId();
        if (ToolText.isNotEmpty(userId)) {
            paramsMap.put(NetRequestKeys.KEY_USER_ID, userId);
        }
        paramsMap.put("appTimestamp", String.valueOf(System.currentTimeMillis()));
//        paramsMap.put("appKey", DefaultRequestConst.VALUE_ANDROID_APPKEY);
//        paramsMap.put("terminalType", DefaultRequestConst.VALUE_TERMINAL_TYPE);
        paramsMap.put("appVersion", ToolApp.getVersionName(ApplicationBase.getInstance()));
//        paramsMap.put("appId", ApplicationBase.getInstance().getAppConfig().getAppId());

//        String signData = SignUtil.encryptMD5ToString(MapUtil.sortMap2String(paramsMap));
//        paramsMap.put("sign",signData);
    }

    /**
     * 设置上传文件进度监听
     * @param fileUploadObserver
     */
    public static void setFileUploadObserver(FileUploadObserver fileUploadObserver){
        mFileUploadObserver = fileUploadObserver;
    }
}
