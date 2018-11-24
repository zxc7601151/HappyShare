package com.happy.share.tools;

import android.app.Activity;
import android.os.Build;


/**
 * desc: Activity工具 <br/>
 * time: 2017/9/11 下午3:18 <br/>
 * author: rooky <br/>
 * since V 1.p <br/>
 */
public class ToolActivity {

    /**
     * Activity 是否Activity生命周期结束
     *
     * @param activity activity
     * @return true：已经结束
     */
    public static boolean isActivityFinishing(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            return (activity == null || activity.isDestroyed());
        } else {
            return (activity == null || activity.isFinishing());
        }
    }

}
