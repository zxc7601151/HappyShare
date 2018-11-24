package com.happy.share.common.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.Stack;


/**
 * desc: Activity管理工具类 <br/>
 * time: 2017/9/11 下午4:02 <br/>
 * author: 居廉 <br/>
 * since V 1.0 <br/>
 */
public class MyActivityManager {

    /**
     * 所有Activity堆栈
     */
    private Stack<FragmentActivity> mActivityStack;
    /**
     * 当前对象例子
     */
    private static MyActivityManager mInstance;


    private MyActivityManager() {

    }

    public static MyActivityManager getInstance() {
        if (mInstance == null) {
            mInstance = new MyActivityManager();
        }
        return mInstance;
    }

    /**
     * 获取指定activity
     * @param className
     * @return
     */
    public FragmentActivity getTargetActivity(Class<?> className) {
        if (mActivityStack != null) {
            for (int i = 0; i < mActivityStack.size(); i++) {
                if (mActivityStack.get(i).getClass() == className) {
                    return mActivityStack.get(i);
                }
            }
        }

        return null;
    }

    /**
     * 添加指定Activity到堆栈
     */
    void addActivity(FragmentActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }

        mActivityStack.add(activity);
    }

    /**
     * 从Activity栈中移除指定的Activity
     */
    void removeActivity(@NonNull FragmentActivity activity) {
        if (mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity
     */
    @Nullable
    public FragmentActivity currentActivity() {
        if (mActivityStack == null || mActivityStack.empty()) {
            return null;
        }
        return mActivityStack.lastElement();
    }

    /**
     * 获取当前Activity 栈信息
     */
    public Stack<FragmentActivity> getAllActivity() {
        return mActivityStack;
    }

    /**
     * 结束多个Activity
     *
     * @param activityNumber 被结束Activity的个数
     */
    public void finishMoreActivity(int activityNumber) {
        while (activityNumber > 0) {
            if ((mActivityStack != null) && (mActivityStack.size() > activityNumber)) {
                finishActivity();
                activityNumber--;
            }
        }
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        if (mActivityStack != null) {
            FragmentActivity activity = mActivityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    private void finishActivity(FragmentActivity activity) {
        if ((mActivityStack != null) && (activity != null)) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (FragmentActivity activity : mActivityStack) {
            if (activity.getClass() == cls) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        if (mActivityStack != null) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (null != mActivityStack.get(i)) {
                    mActivityStack.get(i).finish();
                }
            }
            mActivityStack.clear();
        }
    }

    public FragmentActivity getMainActivity() {
        if (mActivityStack != null) {
            for (int i = 0; i < mActivityStack.size(); i++) {
                FragmentActivity activity = mActivityStack.get(i);
                if (activity instanceof ActivityBase) {
                    return activity;
                }
            }
        }

        return null;
    }
}