package com.happy.share.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.happy.share.R;

/**
 * desc: Dialog工具类 <br/>
 * time: 2017/10/31 20:21 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */

public class ToolDialog {

	/**
	 * 显示错误信息(单个按钮)
	 *
	 * @param context
	 * @param title
	 * @param message
	 * @param canCancelOutside 点击外部是否可取消
	 * @param onClickListener  不用处理可以直接给null
	 */
	public static void showSingleButtonDialog(Context context, String title, String message, boolean canCancelOutside,
											  DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setMessage(message)
				.setNegativeButton(R.string.ok, onClickListener);

		//设置tilte
		if (ToolText.isNotEmpty(title)) {
			builder.setTitle(title);
		}

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(canCancelOutside);
		alertDialog.show();
	}

	/**
	 * 显示两个按钮的dialog
	 *
	 * @param context
	 * @param title
	 * @param message
	 * @param canCancelOutside
	 * @param onPositiveClickListener 不用处理可以直接给null
	 * @param onNegativeClickListener 不用处理可以直接给null
	 */
	public static void showDialog(Context context, String title, String message, boolean canCancelOutside,
								  DialogInterface.OnClickListener onPositiveClickListener,
								  DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setMessage(message)
				.setPositiveButton(R.string.confirm, onPositiveClickListener)
				.setNegativeButton(R.string.cancel, onNegativeClickListener);

		//设置tilte
		if (ToolText.isNotEmpty(title)) {
			builder.setTitle(title);
		}

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(canCancelOutside);
		alertDialog.show();
	}


	/**
	 * 单选框dialog
	 *
	 * @param context          上下文
	 * @param title            标题
	 * @param choices          选择文案
	 * @param selected         默认选择
	 * @param canCancelOutside 点击外部取消
	 * @param canKeyBack       是否禁用返回键
	 * @param onClickListener  点击监听
	 */
	public static void singleChoiceDialog(Context context, String title, String[] choices, int selected,
										  boolean canCancelOutside, boolean canKeyBack, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
				.setSingleChoiceItems(choices, selected, onClickListener);
		if (!canKeyBack) { //禁用返回键
			builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
				}
			});
		}
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(canCancelOutside);
		dialog.show();
	}

	/**
	 * 关闭dialog
	 *
	 * @param dialogInterface
	 */
	public static void dialogDismiss(DialogInterface dialogInterface) {
		if (dialogInterface instanceof Dialog) {
			Dialog dialog = (Dialog) dialogInterface;
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
}
