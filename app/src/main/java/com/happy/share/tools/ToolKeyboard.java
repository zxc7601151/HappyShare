package com.happy.share.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * desc: 软键盘工具类 <br/>
 * PS:修改方法时，请运行APP项目中的'TestToolKeyboradActivity'进行测试验证<br/>
 * time: 2018/8/24 下午3:50 <br/>
 * author: 匡衡 <br/>
 * since V 1.2
 */
public interface ToolKeyboard {

    /**
     * 打开软键盘
     *
     * @param context  上下文
     * @param editText 需要打开软键盘的编辑框
     */
    static void showKeyboard(@Nullable Context context, @Nullable EditText editText) {
        if (context == null || editText == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        //当视图未获取焦点时，手动使其获取焦点
        if (!editText.isFocused()) {
            editText.requestFocus();
        }

        imm.showSoftInput(editText, 0);
    }

    /**
     * 关闭软键盘 <br/>
     * PS:若获取不到Activity的Context，请调用{@link #closeKeyboard(View)}方法
     *
     * @param context 上下文 <br/>
     *                PS：此处的参数不能传入View.getContext()，在5.0以下的手机getContext方法获取的类型为TintContextWrapper
     *                不能转化为Activity类型，也不能传入ApplicationContext
     */
    static void closeKeyboard(@Nullable Context context) {
        if (context == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        View view = null;
        if (context instanceof Activity) {
            view = ((Activity) context).getWindow().getDecorView();
        }

        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param view 视图，当前页面中任意一个视图
     */
    static void closeKeyboard(@Nullable View view) {
        if (view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断软键盘是否显示，以200px为最小高度来判断键盘的高度
     *
     * @param context 上下文，注意不能传入View.getContext()和ApplicationContext
     * @return true: 显示，反之则不显示或者传入的context为空
     */
    static boolean isSoftInputVisible(@Nullable final Context context) {
        return isSoftInputVisible(context, 200);
    }

    /**
     * 根据传入的键盘最小高度，判断软键盘是否显示
     *
     * @param context              上下文，注意不能传入View.getContext()和ApplicationContext
     * @param minHeightOfSoftInput 键盘最小高度
     * @return true: 显示，反之则不显示或者传入的context为空
     */
    static boolean isSoftInputVisible(@Nullable final Context context, final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(context) >= minHeightOfSoftInput;
    }

    /**
     * 获取页面中显示的第一个视图的高度 <br/>
     * PS:目前只针对软键盘高度获取有效，其它用处未经测试，请慎用！
     *
     * @param context 上下文，注意不能传入View.getContext()和ApplicationContext
     * @return 返回第一个可视视图的高度，如果异常情况（Context为空，或者Context不是Activity）
     */
    static int getContentViewInvisibleHeight(@Nullable final Context context) {
        if (context != null && context instanceof Activity) {
            final FrameLayout contentView = ((Activity) context).findViewById(android.R.id.content);
            final View contentViewChild = contentView.getChildAt(0);
            final Rect outRect = new Rect();
            contentViewChild.getWindowVisibleDisplayFrame(outRect);
            return contentViewChild.getBottom() - outRect.bottom;
        }
        return 0;
    }

}
