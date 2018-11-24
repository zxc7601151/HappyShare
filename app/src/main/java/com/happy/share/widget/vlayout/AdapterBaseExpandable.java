package com.happy.share.widget.vlayout;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.happy.share.widget.recyclerview.AdapterRecyclerBase;
import com.happy.share.widget.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * desc: Vlayout Adapter基类--- 支持展开和折叠 <br/>
 * NOTE:
 *  1.可全部展开一级二级视图，当作普通的一二级列表使用， 用法：重载 {@link #internalControlStatus()}强制return false，
 *  重载 {@link #isExpand(T)} 强制return true；禁止调用 {@link #changeExpandStatus(int, int, boolean)}
 *  {@link #collapseAll()} 相关代码
 *  2.展开和折叠的状态可基类控制或者子类控制，如果子类控制 用法：重载 {@link #internalControlStatus()}强制return false
 *  重载 {@link #isExpand(T)} 子类进行判断， 重载 {@link #onGroupExpandStatusChange(int, boolean)}回调更新状态
 *  3.当数据发生变化时一定要调用 {@link #refreshExpandStatus()} or {@link #resetExpandStatus() 这个方法目前
 *  是 private 的，如果有需要用到请通知作者商讨}
 *  4.目前仅支持一二级列表，后续如果业务有需要，会进行功能扩展
 * author: 林佐跃 <br/>
 * date: 2018/1/30 <br/>
 * since V 6.15 <br/>
 */
public abstract class AdapterBaseExpandable<GVH extends BaseViewHolder
        , CVH extends BaseViewHolder, T> extends AdapterBase<BaseViewHolder, T> {

    /**
     * 视图可见的item个数总合 ；
     * 父Item个数 + 子Item个数（NOTE : 如果子Item状态是折叠起来的 那么 子Item个数强制为0）;
     * mTotal 是变化的，在{@link #refreshExpandStatus()} 这个方法中更新，所以数据变化时一定要手动调用{@link #refreshExpandStatus()}
     */
    private int mTotal;

    // 父item 状态列表
    private SparseArray<ExpandStatus> expandableModels = new SparseArray<>();

    // group item 状态改变回调
    private OnExpandStatusChangeListener mOnExpandStatusChangeListener;

    // 点击事件回调
    private OnItemClickListener mOnItemClickListener;

    public AdapterBaseExpandable(Context context, List list) {
        super(context, list);
        resetExpandStatus();
        setOnItemClickListener(itemClickListener);
    }

    public AdapterBaseExpandable(Context context, T item) {
        super(context, item);
        resetExpandStatus();
        setOnItemClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mTotal;
    }

    @Override
    public int getItemViewType(int position) {
        int s = 0, e = getGroupCount() - 1, m;
        while (s <= e) {
            m = (s + e) / 2;
            T group = getList().get(m);
            ExpandStatus expandStatus = getExpandStatus(m);
            int positionStart = expandStatus.getPosition();
            int positionEnd = positionStart;
            if (isExpand(m)) {
                positionEnd += getChildCount(group);
            }
            if (positionStart > position) {
                e = m - 1;
            } else if (positionEnd < position) {
                s = m + 1;
            } else {
                if (position == positionStart) {
                    return getGroupItemType(group);
                } else {
                    return getChildItemType(group, position - positionStart);
                }
            }

        }
        // 无法匹配 抛出异常
        Log.e(AdapterBaseExpandable.class.getSimpleName(),
                        "No match item view type at position=" + position + " in data source");
        return TYPE_NONE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isGroupViewType(viewType)) {
            return onCreateGroupViewHolder(parent, viewType);
        } else if (isChildViewType(viewType)) {
            return onCreateChildViewHolder(parent, viewType);
        }
        return new BaseViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemPosition itemPosition = createItemPosition(position);
        if (itemPosition == null) {
            return;
        }
        int itemViewType = getItemViewType(position);
        if (isGroupViewType(itemViewType)) {
            onBindGroupViewHolderWithOffset((GVH) holder, itemPosition);
        } else if (isChildViewType(itemViewType)) {
            onBindChildViewHolderWithOffset((CVH) holder, itemPosition);
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();


    }

    protected int getGroupCount() {
        return super.getItemCount();
    }

    protected abstract int getChildCount(T group);

    protected abstract int getGroupItemType(T t);

    protected abstract int getChildItemType(T group, int childPosition);

    protected abstract boolean isGroupViewType(int viewType);

    protected abstract boolean isChildViewType(int viewType);

    protected abstract GVH onCreateGroupViewHolder(ViewGroup parent, int viewType);

    protected abstract CVH onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindGroupViewHolderWithOffset(GVH holder, ItemPosition positionModel);

    protected abstract void onBindChildViewHolderWithOffset(CVH holder, ItemPosition positionModel);

    // ======================== 折叠 展开 相关 start ============================= //

    /**
     * 是否内部控制expand 状态
     *
     * @return
     */
    protected boolean internalControlStatus() {
        return true;
    }

    /**
     * 获取expand 状态 默认false
     * {@link #internalControlStatus()} 为false时 必须实现这个方法
     *
     * @param group
     * @return
     */
    protected boolean isExpand(T group) {
        return false;
    }

    /**
     * 通知外部数据变化
     * {@link #internalControlStatus()} 为false时 必须实现这个方法
     *
     * @param groupPosition
     * @param expandStatus
     */
    protected void onGroupExpandStatusChange(int groupPosition, boolean expandStatus) {}

    /**
     * 状态监听设置
     *
     * @param mOnExpandStatusChangeListener
     */
    public void setOnExpandStatusChangeListener(OnExpandStatusChangeListener mOnExpandStatusChangeListener) {
        this.mOnExpandStatusChangeListener = mOnExpandStatusChangeListener;
    }

    /**
     * 刷新状态
     * NOTE：数据变化后需要调用该方法，刷新数据
     */
    public final void refreshExpandStatus() {
        mTotal = 0;
        if (getGroupCount() == 0) {
            expandableModels.clear();
            return;
        }
        SparseArray<ExpandStatus> tempExpandableModels = new SparseArray<>();
        for (int i = 0; i < getGroupCount(); i++) {
            T group = getList().get(i);
            ExpandStatus expandStatus = getExpandStatus(i);
            expandStatus.setPosition(mTotal);
            if (isExpand(i)) {
                mTotal += getChildCount(group);
            }
            tempExpandableModels.put(i, expandStatus);
            mTotal++;
        }
        expandableModels.clear();
        expandableModels = tempExpandableModels;
    }

    /**
     * 改变 折叠 展开 状态
     * @param groupPosition
     * @param position
     * @param singleExpandMode
     * @return 返回最终状态
     */
    public boolean changeExpandStatus(int groupPosition, int position, boolean singleExpandMode) {
        T group = getList().get(groupPosition);
        int childCount = getChildCount(group);
        if (childCount == 0) {
            return false;
        }
        if (singleExpandMode) {
            collapseAllWithout(groupPosition);
        }
        boolean expandStatus = isExpand(groupPosition);
        ExpandStatus expandable = getExpandStatus(groupPosition);
        if (internalControlStatus()) {
            expandable.setExpand(!expandStatus);
        } else {
            onGroupExpandStatusChange(groupPosition, !expandStatus);
        }
        refreshExpandStatus();
//        notifyDataSetChanged();
        if (!expandStatus) {
            notifyItemRangeInserted(position + 1, childCount);
            notifyItemRangeChanged(position + 1, childCount);
//            notifyItemRangeChanged(position + 1, getItemCount() - position - 1);
            if (mOnExpandStatusChangeListener != null) {
                mOnExpandStatusChangeListener.onGroupExpand(groupPosition, expandable.getPosition());
            }
        } else {
            notifyItemRangeRemoved(position + 1, childCount);
            notifyItemRangeChanged(position + 1, childCount);
//            notifyItemRangeChanged(position + 1, getItemCount() - position - 1);
            if (mOnExpandStatusChangeListener != null) {
                mOnExpandStatusChangeListener.onGroupCollapse(groupPosition, expandable.getPosition());
            }
        }
        return !expandStatus;
    }

    /**
     * 合并所有子item
     *
     * @return 是否折叠items
     */
    public boolean collapseAll() {
        boolean isCollapsed = false;
        for (int i = 0; i < getGroupCount(); i++) {
            ExpandStatus expandStatus = getExpandStatus(i);
            if (isExpand(i)) {
                if (!internalControlStatus()) {
                    onGroupExpandStatusChange(i, false);
                } else {
                    expandStatus.setExpand(false);
                }
                T group = getList().get(i);
                int positionStart = expandStatus.getPosition() + 1;
                refreshExpandStatus();
                int childCount = getChildCount(group);
                notifyItemRangeRemoved(positionStart, childCount);
                notifyItemRangeChanged(positionStart, childCount);
//                notifyItemRangeChanged(positionStart, getItemCount() - positionStart);
                isCollapsed = true;
            }
        }
        return isCollapsed;
    }

    /**
     * 合并所有子item 除了
     *
     * @param groupPosition 指定不需要折叠的位置
     * @return 是否折叠items
     */
    private boolean collapseAllWithout(int groupPosition) {
        boolean isCollapsed = false;
        for (int i = 0; i < getGroupCount(); i++) {
            if (i == groupPosition) {
                continue;
            }
            ExpandStatus expandStatus = getExpandStatus(i);
            if (isExpand(i)) {
                if (!internalControlStatus()) {
                    onGroupExpandStatusChange(i, false);
                } else {
                    expandStatus.setExpand(false);
                }
                T group = getList().get(i);
                int positionStart = expandStatus.getPosition() + 1;
                refreshExpandStatus();
                int childCount = getChildCount(group);
                notifyItemRangeRemoved(positionStart, childCount);
                notifyItemRangeChanged(positionStart, childCount);
//                notifyItemRangeChanged(positionStart, getItemCount() - positionStart);
                isCollapsed = true;
            }
        }
        return isCollapsed;
    }


    /**
     * 内部使用判断状态
     *
     * @param groupPosition
     * @return 获取状态，是否展开
     * @apiNote 私有方法，公开数据会有异常
     */
    private boolean isExpand(int groupPosition) {
        T group = getList().get(groupPosition);
        ExpandStatus expandStatus = getExpandStatus(groupPosition);
        if (internalControlStatus()) {
            return expandStatus.isExpand();
        } else {
            return isExpand(group);
        }
    }

    /**
     * 重新设置状态
     */
    private void resetExpandStatus() {
        mTotal = 0;
        expandableModels.clear();
        refreshExpandStatus();
    }

    /**
     * 获取 ExpandStatus
     *
     * @param key 一级
     * @return
     */
    private ExpandStatus getExpandStatus(int key) {
        ExpandStatus expandStatus = expandableModels.get(key);

        //容错处理
        if (expandStatus == null) {
            expandStatus = new ExpandStatus();
            expandableModels.put(key, expandStatus);
        }

        return expandStatus;
    }

    // ======================== 折叠 展开 相关 end ============================= //

    /**
     * 根据positon 创建ItemPosition
     *
     * @param position 当前adapter position
     * @return
     */
    private ItemPosition createItemPosition(int position) {
        int s = 0, e = getGroupCount() - 1, m;
        while (s <= e) {
            m = (s + e) / 2;
            T group = getList().get(m);
            ExpandStatus expandStatus = getExpandStatus(m);
            int positionStart = expandStatus.getPosition();
            int positionEnd = positionStart;
            if (isExpand(m)) {
                positionEnd += getChildCount(group);
            }
            if (positionStart > position) {
                e = m - 1;
            } else if (positionEnd < position) {
                s = m + 1;
            } else if (positionStart <= position && positionEnd >= position) {
                ItemPosition itemPosition = new ItemPosition();
                itemPosition.setGroupPosition(m);
                itemPosition.setPosition(position);
                if (position == positionStart) {
                    itemPosition.setGroup(true);
                    itemPosition.setChildPosition(0);
                } else {
                    itemPosition.setGroup(false);
                    itemPosition.setChildPosition(position - positionStart - 1);
                }
                return itemPosition;
            } else {
                break;
            }
        }

        // 无法匹配 抛出异常
        Log.e(AdapterBaseExpandable.class.getSimpleName(),
                "Can not create ItemPosition at position=" + position);
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private AdapterRecyclerBase.OnRecyclerItemClickListener itemClickListener = (v, position) -> {
        int i = position - getOffset();
        ItemPosition itemPosition = createItemPosition(position - getOffset());
        if (mOnItemClickListener == null || itemPosition == null) {
            return;
        }
        if (itemPosition.isGroup()) {
            mOnItemClickListener.onGroupItemClick(itemPosition);
        } else {
            mOnItemClickListener.onChildItemClick(itemPosition);
        }
    };

    /**
     * expand 状态改变 接口回调
     */
    public interface OnExpandStatusChangeListener {

        /**
         * 展开
         * @param groupPosition group item 位置
         * @param position
         */
        void onGroupExpand(int groupPosition, int position);

        /**
         * 折叠
         * @param groupPosition group item 位置
         * @param position
         */
        void onGroupCollapse(int groupPosition, int position);

    }

    /**
     * 点击事件回调
     */
    public interface OnItemClickListener {

        /**
         * group item 点击
         * @param itemPosition
         */
        void onGroupItemClick(ItemPosition itemPosition);

        /**
         * child item 点击
         * @param itemPosition
         */
        void onChildItemClick(ItemPosition itemPosition);

    }

    /**
     * 内部状态保存
     */
    class ExpandStatus {

        /**
         * adapter 中的实际位置
         */
        int position;

        /**
         * 状态 true：展开 false： 折叠
         */
        private boolean expand;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }
    }

    /**
     * 位置信息
     */
    public static class ItemPosition {

        /**
         * 是否是 goup item
         */
        boolean isGroup;
        /**
         * 在 group 列表 中的位置
         */
        int groupPosition;
        /**
         * 在某个group中 位置
         */
        int childPosition;
        /**
         * 在adapter 中的位置
         */
        int position;

        public boolean isGroup() {
            return isGroup;
        }

        public void setGroup(boolean group) {
            isGroup = group;
        }

        public int getGroupPosition() {
            return groupPosition;
        }

        public void setGroupPosition(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public int getChildPosition() {
            return childPosition;
        }

        public void setChildPosition(int childPosition) {
            this.childPosition = childPosition;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
