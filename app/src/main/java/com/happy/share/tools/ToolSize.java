package com.happy.share.tools;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;

/**
 * desc: 尺寸工具类 <br/>
 * time: 2018/8/7 10:01 <br/>
 * author: 义仍 <br/>
 * since V 1.2 <br/>
 */
public interface ToolSize {

    /**
     * 将dp值转换为px值
     *
     * @param context context
     * @param dpValue dp值
     * @return 转化后的px值
     */
    static int dp2Px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dp值
     *
     * @param context context
     * @param pxValue px值
     * @return 转化后的dp值
     */
    static int px2dp(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     *
     * @param context context
     * @param pxValue px值
     * @return 转化后的sp值
     */
    static int px2sp(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param context context
     * @param spValue sp值
     * @return 转化后的px值
     */
    static int sp2px(@NonNull Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 从资源文件id获取像素值
     *
     * @param context context
     * @param id      dimen文件id  R.dimen.resourceId
     * @return R.dimen.resourceId对应的尺寸具体的像素值
     */
    static int getDimension(@NonNull Context context, @DimenRes int id) {
        return (int) context.getResources().getDimension(id);
    }
}
