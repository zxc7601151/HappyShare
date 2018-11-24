package com.happy.share.widget.vlayout;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.Range;
import com.happy.share.tools.ToolList;
import com.happy.share.tools.ToolText;
import com.happy.share.widget.recyclerview.AdapterRecyclerBase;
import com.happy.share.widget.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: Vlayout Adapter基类 也可以当普通 <br/>
 * NOTE:
 * author: rooky <br/>
 * date: 2018/1/16 <br/>
 * since v1.0 <br/>
 */
public abstract class AdapterBase<VH extends BaseViewHolder, T> extends JCDelegateAdapter.Adapter<VH> {

    /**
     * 未匹配到代理的类型
     */
    final int TYPE_NONE = -1;

    private LayoutHelper layoutHelper;
    private Context mContext;
    private List<T> mList;
    private LayoutInflater mLayoutInflater;
    private AdapterRecyclerBase.OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    private AdapterRecyclerBase.OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;
    private int itemType = TYPE_NONE;

    public AdapterBase(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public AdapterBase(Context context, T item) {
        this.mContext = context;
        if (item != null) {
            this.mList = new ArrayList<>();
            this.mList.add(item);
        }
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setLayoutHelper(@NonNull LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemViewType(int position) {
        return itemType < 0 ? super.getItemViewType(position) : itemType;
    }

    /**
     * PS：子类重写时 一定要带上 <br/>
     * super.onBindViewHolder(viewHolder, position);
     */
    @Override
    @CallSuper
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.setOnRecyclerItemClickListener(mOnRecyclerItemClickListener);
        holder.setOnRecyclerItemLongClickListener(mOnRecyclerItemLongClickListener);

        //设置spmItemValue值到视图控件中
//        setSpmItemValue2View(holder, position);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * 获取偏移量
     */
    public int getOffset() {
        if (layoutHelper != null) {
            Range<Integer> range = layoutHelper.getRange();
            Integer lower = range.getLower();
            return lower < 0 ? 0 : lower;
        }
        return 0;
    }

    /**
     * NOTE: 这个方法在 Vlayout 中才会调用, 普通的只会调用{@link #onBindViewHolder(BaseViewHolder, int)}
     */
    @Override
    protected final void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
    }

    /**
     * NOTE: 这个方法在 Vlayout 中才会调用, 普通的只会调用{@link #onBindViewHolder(BaseViewHolder, int)}
     */
    @Override
    protected final void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal, List<Object> payloads) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal, payloads);
    }

    @Override
    public int getItemCount() {
        return ToolList.isNullOrEmpty(mList) ? 0 : mList.size();
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public Context getContext() {
        return mContext;
    }

    public void addAll(List<T> list) {
        if (mList != null) {
            mList.addAll(list);
        }
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    public void setItem(T item) {
        if (item == null) {
            mList = null;
            return;
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.add(item);
    }

    public void setOnItemClickListener(AdapterRecyclerBase.OnRecyclerItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            this.mOnRecyclerItemClickListener = onItemClickListener;
        }
    }

    public void setOnItemLongClickLitener(AdapterRecyclerBase.OnRecyclerItemLongClickListener onItemLongClickListener) {
        if (onItemLongClickListener != null) {
            this.mOnRecyclerItemLongClickListener = onItemLongClickListener;
        }
    }

    /**
     * 创建每个Item视图的spmItemValue值 <br/>
     * PS:用于OnItemClick方法中对spmItemValue赋值
     *
     * @param position 列表下标
     * @return itemSpm值
     */
    public String createSpmItemValue(int position) {
        //子类重写
        return null;
    }

    /**
     * 获取创建后的spmItemValue值，设置到Item的View中
     *
     * @param holder   BaseViewHolder
     * @param position 列表下标
     */
    private void setSpmItemValue2View(VH holder, int position) {
        String spmItemValue = createSpmItemValue(position);

        //当存在spmItemValue有值时，ViewTraceModel为空
        if (ToolText.isNotEmpty(spmItemValue)) {
            Log.e(this.getClass().getSimpleName(), "setSpmItemValue2View() spmItemValue有值" +
                    " ViewTraceModel实例为null --> 需要setViewTraceModel()");
        }

        //当ViewTraceModel不为空时，spmItemValue值为空
        if (ToolText.isEmptyOrNull(spmItemValue)) {
            Log.e(this.getClass().getSimpleName(), "setSpmItemValue2View() ViewTraceModel实例不为null" +
                    " spmItemValue值为空 --> 需要重写createSpmItemValue()方法");
        }
    }

}
