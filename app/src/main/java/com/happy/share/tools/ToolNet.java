package com.happy.share.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

/**
 * desc: 网络工具类 <br/>
 * author: 尾生<br/>
 * time: 2018/8/28 下午1:37<br/>
 * since V 1.2 <br/>
 */
public interface ToolNet {

    /**
     * 网络是否可用
     * (开启飞行模式或未开启移动网络和wifi等情况不可用)
     *
     * @param ctx 上下文
     * @return true 可用
     */
    static boolean isAvailable(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isAvailable();
    }

    /**
     * 网络是否连接
     *
     * @param ctx 上下文
     * @return true 已连接
     */
    static boolean isConnected(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isConnected();
    }

    /**
     * Wifi是否连接
     *
     * @param ctx 上下文
     * @return true 已连接
     */
    static boolean isWifiConnected(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isConnected() && netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 移动网络是否连接
     *
     * @param ctx 上下文
     * @return true 已连接
     */
    static boolean isMobileConnected(@NonNull Context ctx) {
        NetworkInfo netWorkInfo = getActiveNetWorkInfo(ctx);
        return netWorkInfo != null && netWorkInfo.isConnected() && netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 获取移动网路运营商名称
     *
     * @param ctx 上下文
     * @return 运营商名称
     */
    @Nullable
    static String getNetWorkOperatorName(@NonNull Context ctx) {
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager == null ? "" : telephonyManager.getNetworkOperatorName();
    }

    /**
     * 打开网络设置界面
     * 打开设置界面
     *
     * @param ctx 上下文
     */
    static void openWirelessSettings(@NonNull Context ctx) {
        try {
            ctx.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取网络信息
     * 需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param ctx 上下文
     * @return 网络信息类
     */
    @SuppressLint("MissingPermission")
    @Nullable
    static NetworkInfo getActiveNetWorkInfo(@NonNull Context ctx) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                return connectivityManager.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
