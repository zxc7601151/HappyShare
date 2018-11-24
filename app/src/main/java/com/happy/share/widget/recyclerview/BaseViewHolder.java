package com.happy.share.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.happy.share.common.base.cliker.ViewClickerInterceptor;

/**
 * desc: ViewHolder基类 -- 实现Item点击效果 <br/>
 * time: 2018/11/24 14:45 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


    private AdapterRecyclerBase.OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private AdapterRecyclerBase.OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;
    private boolean mHasClick;//是否设置点击监听
    private boolean mHasLongClick;//是否设置长按监听
    private View mContainerView;
    private ViewClickerInterceptor mClickerInterceptor;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContainerView = itemView;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (getClickerInterceptor().isFastClick(v)) {
            return;
        }
        if (mOnRecyclerItemClickListener != null) {
            mOnRecyclerItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnRecyclerItemLongClickListener != null) {
            mOnRecyclerItemLongClickListener.onItemLongClick(v, getLayoutPosition());
        }
        return false;
    }

    /**
     * 添加对子View的点击事件
     *
     * @param view 子View
     */
    public void addOnClickListener(View view) {
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(v -> {
                if (mOnRecyclerItemClickListener != null) {
                    mOnRecyclerItemClickListener.onItemClick(v, getLayoutPosition());
                }
            });
        }
    }

    /**
     * 添加对子View的长按事件
     *
     * @param view 子View
     */
    public void addOnLongClickListener(View view) {
        if (view != null) {
            if (!view.isLongClickable()) {
                view.setLongClickable(true);
            }
            view.setOnLongClickListener(v -> {
                if (mOnRecyclerItemLongClickListener != null) {
                    mOnRecyclerItemLongClickListener.onItemLongClick(v, getLayoutPosition());
                    return true;
                }
                return false;
            });
        }
    }

    public void setOnRecyclerItemLongClickListener(AdapterRecyclerBase.OnRecyclerItemLongClickListener onRecyclerItemLongClickListener) {
        mHasLongClick = true;
        this.mOnRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    public void setOnRecyclerItemClickListener(AdapterRecyclerBase.OnRecyclerItemClickListener onRecyclerItemClickListener) {
        mHasClick = true;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public boolean hasClick() {
        return mHasClick;
    }

    public boolean hasLongClick() {
        return mHasLongClick;
    }

    public View getContainerView() {
        return mContainerView;
    }

    /**
     * {@link View.OnClickListener#onClick(View)}拦截器
     */
    @NonNull
    protected ViewClickerInterceptor getClickerInterceptor() {
        if (mClickerInterceptor == null) {
            mClickerInterceptor = new ViewClickerInterceptor();
        }
        return mClickerInterceptor;
    }

}