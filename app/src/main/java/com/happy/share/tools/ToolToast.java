package com.happy.share.tools;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.happy.share.ApplicationBase;
import com.happy.share.R;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * desc: 自定义Toast提示框 <br/>
 * time: 2017/9/12 上午10:29 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ToolToast {

    private static Toast toast;


    private ToolToast() {

    }

    /**
     * 显示toast
     * Note: 如果使用context.getResources()获取msg 请直接使用{@link #showToast(int)},
     * context.getResources()线上有NullPointerException 异常情况
     */
    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
     */
    public static void showToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
     *
     * @param resIdOrMsg 字符串资源id或者显示文案字符串
     * @param duration   显示时长，值为：{@link Toast#LENGTH_SHORT}或 {@link Toast#LENGTH_LONG}
     */
    public static void showToast(Object resIdOrMsg, int duration) {
        Context context = ApplicationBase.getInstance();
        String msg = null;

        if (resIdOrMsg instanceof Integer) {
            if (context.getResources() == null) {
                return;
            }
            msg = context.getResources().getString((Integer) resIdOrMsg);
        } else if (resIdOrMsg instanceof String) {
            msg = (String) resIdOrMsg;
        }

        showToast(context, msg, duration, Gravity.CENTER, 0, 0);
    }

    /**
     * 显示toast(位置自定义)
     *
     * @param resId    字符串资源id
     * @param duration 值为：{@link Toast#LENGTH_SHORT}或 {@link Toast#LENGTH_LONG}
     * @param gravity  文案排版方向，值如：{@link Gravity#CENTER}等
     * @param xOffset  x轴偏移量
     * @param yOffset  y轴偏移量
     */
    public static void showToast(int resId, int duration, int gravity, int xOffset, int yOffset) {
        Context context = ApplicationBase.getInstance();

        if (context.getResources() != null) {
            showToast(context, context.getResources().getString(resId), duration, gravity, xOffset, yOffset);
        }
    }

    /**
     * 显示toast
     *
     * @param context  上下文
     * @param msg      显示内容
     * @param duration 显示时长，值为：{@link Toast#LENGTH_SHORT}或 {@link Toast#LENGTH_LONG}
     * @param gravity  文案排版方向
     * @param xOffset  x轴偏移量
     * @param yOffset  y轴偏移量
     */
    @SuppressLint("InflateParams")
    private static void showToast(Context context, String msg, int duration, int gravity, int xOffset, int yOffset) {
        if (ToolText.isEmptyOrNull(msg)) {
            return;
        }

        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            if (toast == null) {
                View view = LayoutInflater.from(context).inflate(R.layout.module_base_view_toast_custom, null);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.module_base_bg_toast_custom);

                // 设置透明度为70%
                if (drawable != null) {
                    drawable.setAlpha((int) (255 * 0.7));
                    view.setBackground(drawable);
                }

                TextView text = view.findViewById(R.id.tv_msg);
                if (text == null) {
                    return;
                }

                text.setText(msg);
                toast = new Toast(context);
                toast.setView(view);
            } else {
                View view = toast.getView();
                TextView tv = view.findViewById(R.id.tv_msg);
                tv.setText(msg);
            }

            toast.setDuration(duration);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        });
    }

}
