package com.happy.share.tools;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * desc: 视图工具类 <br/>
 * time: 2018/8/21 下午3:50 <br/>
 * author: 匡衡 <br/>
 * since V 1.2
 */
public interface ToolView {

    @IntDef({View.VISIBLE, View.GONE, View.INVISIBLE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewVisibility {
    }

    /**
     * 设置控件Click监听事件
     *
     * @param onClickListener 监听实例
     * @param views           视图集合
     */
    static void setOnClickListener(@Nullable final View.OnClickListener onClickListener, @Nullable final View... views) {
        if (views != null && views.length > 0 && onClickListener != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(onClickListener);
                }
            }
        }
    }

    /**
     * 显示View视图
     *
     * @param views 视图集合
     */
    static void showView(@Nullable final View... views) {
        setVisibility(View.VISIBLE, views);
    }

    /**
     * 以GONE方式，隐藏View视图
     *
     * @param views 视图集合
     */
    static void hideView(@Nullable final View... views) {
        setVisibility(View.GONE, views);
    }

    /**
     * 设置View显示/隐藏
     *
     * @param visibility 值见:{@link View#VISIBLE}、{@link View#GONE}、{@link View#INVISIBLE}
     * @param views      视图集合
     */
    static void setVisibility(@ViewVisibility final int visibility, @Nullable final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(visibility);
                }
            }
        }
    }

    /**
     * 给TextView 设置
     *
     * @param tv         TextView
     * @param resIdOrTxt 值
     */
    static void setText(@Nullable final TextView tv, @Nullable final Object resIdOrTxt) {
        //空处理
        if (tv == null || resIdOrTxt == null) {
            return;
        }

        if (resIdOrTxt instanceof String) {
            tv.setText((String) resIdOrTxt);
        } else if (resIdOrTxt instanceof CharSequence) {
            tv.setText((CharSequence) resIdOrTxt);
        } else if (resIdOrTxt instanceof Integer) {
            /* 捕捉传入的int类型不是字符串Id */
            try {
                tv.setText((Integer) resIdOrTxt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 设置多个文本颜色
     *
     * @param ctx       上下文
     * @param colorRes  色值资源，例如R.color.xx
     * @param textViews TextView集合
     */
    static void setTextColor(@Nullable final Context ctx, @ColorRes final int colorRes, @Nullable final TextView... textViews) {
        if (ctx == null || textViews == null || textViews.length == 0) {
            return;
        }
        for (TextView tv : textViews) {
            if (tv != null) {
                tv.setTextColor(ContextCompat.getColor(ctx, colorRes));
            }
        }
    }

    /**
     * 设置多个文本颜色
     *
     * @param colorString 以'#'开头的色值字符串，例如：#RRGGBB，#AARRGGBB
     * @param textViews   TextView集合
     */
    static void setTextColor(@Size(min = 7, max = 9) final String colorString, @Nullable final TextView... textViews) {
        // 空处理
        if (ToolText.isEmptyOrNull(colorString) || textViews == null || textViews.length == 0) {
            return;
        }

        // 色值字符串检测
        if (colorString.charAt(0) != '#' || (colorString.length() != 7 && colorString.length() != 9)) {
            return;
        }

        final int color = Color.parseColor(colorString);
        for (TextView tv : textViews) {
            if (tv != null) {
                tv.setTextColor(color);
            }
        }
    }

    /**
     * 设置View的Tag
     *
     * @param view 视图
     * @param tag  Tag值
     */
    static void setTag(@Nullable final View view, @Nullable final Object tag) {
        // 空处理
        if (view == null || tag == null) {
            return;
        }

        view.setTag(tag);
    }

    /**
     * 以指定的key，设置View的Tag
     *
     * @param view   视图
     * @param tagKey Tag的Key值，注意要传入Ids资源文件中定义的id值，例如：R.id.x
     * @param tag    Tag值
     */
    static void setTag(@Nullable final View view, @IdRes final int tagKey, @Nullable final Object tag) {
        // 空处理或者异常处理
        if (view == null || tag == null || ((tagKey >>> 24) < 2)) {
            return;
        }

        view.setTag(tagKey, tag);
    }

    /**
     * 设置视图的背景图片
     *
     * @param view  视图
     * @param resId 背景图片资源，例如R.drawable.xx
     */
    static void setBackgroundResource(@Nullable final View view, @DrawableRes int resId) {
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * 设置视图的背景色
     *
     * @param view     视图
     * @param colorRes 背景色值，例如R.color.xx
     */
    static void setBackgroundColor(@Nullable final View view, @ColorRes int colorRes) {
        if (view != null) {
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), colorRes));
        }
    }

}
