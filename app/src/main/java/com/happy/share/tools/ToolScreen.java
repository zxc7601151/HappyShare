package com.happy.share.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * desc: 屏幕工具类 <br/>
 * time: 2018/8/8 10:14 <br/>
 * author: 义仍 <br/>
 * since V 1.2 <br/>
 */
public interface ToolScreen {

    /**
     * 获取屏幕密度
     *
     * @param context context
     * @return 屏幕密度
     */
    static float getScreenDensity(@NonNull final Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕宽度像素值
     *
     * @param context context
     * @return 屏幕宽度像素值
     */
    static int getScreenWidth(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度像素值
     * 在有些机型上受menu bar的影响, 获取到的高度小于真实高度
     * 需要获取屏幕真实高度时请使用 {@link #getRealScreenSize(Context)}
     *
     * @param context context
     * @return 屏幕高度像素值
     */
    static int getScreenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕真实宽高(适用于SDK>17)
     * 包含status bar和menu bar
     *
     * @param context context
     * @return 获取屏幕真实宽高 int[0]-宽 int[1]-高
     */
    static int[] getRealScreenSize(@NonNull Context context) {
        int[] size = new int[2];
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point realSize = new Point();
            display.getRealSize(realSize);
            size[0] = realSize.x;
            size[1] = realSize.y;
        }
        return size;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param context context
     * @return 屏幕旋转角度，值为竖直{@link Surface#ROTATION_0}, 逆时针旋转90度{@link Surface#ROTATION_90}
     * , 倒立{@link Surface#ROTATION_180}, 顺时针旋转90度{@link Surface#ROTATION_270}之一
     */
    static int getScreenRotation(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            return windowManager.getDefaultDisplay().getRotation();
        }
        return Surface.ROTATION_0;
    }

    /**
     * 从一个view创建Bitmap
     * 绘制之前要清掉View的焦点，因为焦点可能会改变一个View的UI状态。
     *
     * @param view 需要转换成Bitmap的view
     * @return 根据view生成的bitmap, 可能为空
     */
    @Nullable
    static Bitmap createBitmapFromView(@NonNull View view) {
        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap != null) {
            Canvas canvas = new Canvas();
            canvas.setBitmap(bitmap);
            canvas.save();
            // 防止View上面有些区域空白导致最终Bitmap上有些区域变黑
            canvas.drawColor(Color.WHITE);
            view.draw(canvas);
            canvas.restore();
            canvas.setBitmap(null);
        }
        return bitmap;
    }

    /**
     * 进入或退出全屏
     *
     * @param activity activity
     */
    static void toggleFullScreen(@NonNull Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        Window window = activity.getWindow();
        if ((params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 设置activity为横屏
     *
     * @param activity activity
     */
    static void setLandScape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置activity为竖屏
     *
     * @param activity activity
     */
    static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
