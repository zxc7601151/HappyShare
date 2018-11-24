package com.happy.share.widget.vlayout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.Range;
import com.happy.share.R;
import com.happy.share.tools.ToolList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * desc: 适配器包装类,支持拼接或插入功能 <br/>
 *
 * ps:设计这个适配器包装类的目的是为了提高Adapter复用率，降低维护的成本<br/>
 * author: 林佐跃 <br/>
 * date: 2018/5/2 <br/>
 */
public class AdapterWrapper extends JCDelegateAdapter.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = AdapterWrapper.class.getSimpleName();

    /**
     * 总共个数
     */
    private int mTotal;

    /**
     * 其他通过{@link #addAdapter} 方法添加进来的 适配器列表
     */
    @NonNull
    private final List<Pair<AdapterDataObserver, RecyclerView.Adapter>> mAdapters = new ArrayList<>();

    /**
     * 主适配器
     */
    private Pair<AdapterDataObserver, RecyclerView.Adapter> mMainPair;

    /**
     * 更新mAdapterRanges时 用来计数
     */
    private int countPosition = 0;

    /**
     * 将[0,{@link #mTotal}) 区间，拆分若干个小区间，每个小区间范围[{@link AdapterRange#startPositionOuter}
     * , {@link AdapterRange#startPositionOuter} + {@link AdapterRange#rangeSize})
     * 每个AdapterRange 对应一个 {@link RecyclerView.Adapter}
     */
    private LinkedList<Pair<AdapterRange, RecyclerView.Adapter>> mAdapterRanges;

    /**
     * 存放 itemViewType 对应的 adapter
     */
    private SparseArray<RecyclerView.Adapter> mItemTypeAry = new SparseArray<>();

    private LayoutHelper layoutHelper;

    public AdapterWrapper() {
    }

    public AdapterWrapper(LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
    }

    public void setLayoutHelper(@NonNull LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    /**
     * 获取偏移量
     *
     * @return
     */
    public int getOffset() {
        if (layoutHelper != null) {
            Range<Integer> range = layoutHelper.getRange();
            Integer lower = range.getLower();
            return lower < 0 ? 0 : lower;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mTotal;
    }

    @Override
    public final int getItemViewType(int position) {
        Pair<AdapterRange, RecyclerView.Adapter> adapterByPosition = findAdapterByPosition(position);

        if (adapterByPosition == null || adapterByPosition.second == null || adapterByPosition.first == null) {
            return RecyclerView.INVALID_TYPE;
        }
        int adapterPosition = getAdapterRangePosition(adapterByPosition.first, position);

        int itemViewType = adapterByPosition.second.getItemViewType(adapterPosition);
        mItemTypeAry.put(itemViewType, adapterByPosition.second);

        return itemViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.Adapter adapter = mItemTypeAry.get(viewType);
        if (adapter == null) {
            return null;
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Pair<AdapterRange, RecyclerView.Adapter> adapterByPosition = findAdapterByPosition(position);

        if (adapterByPosition == null || adapterByPosition.second == null || adapterByPosition.first == null) {
            return;
        }
        adapterByPosition.second.onBindViewHolder(holder
                , getAdapterRangePosition(adapterByPosition.first, position));

        holder.itemView.setTag(R.id.key_adapter_wrapper, adapterByPosition.second);
    }

    @Override
    protected final void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
    }

    @Override
    protected final void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal, List<Object> payloads) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal, payloads);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        RecyclerView.Adapter adapter = (RecyclerView.Adapter) holder.itemView.getTag(R.id.key_adapter_wrapper);
        if (adapter == null) {
            return;
        }
        adapter.onViewRecycled(holder);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        RecyclerView.Adapter adapter = (RecyclerView.Adapter) holder.itemView.getTag(R.id.key_adapter_wrapper);
        if (adapter == null) {
            return;
        }
        adapter.onViewAttachedToWindow(holder);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        RecyclerView.Adapter adapter = (RecyclerView.Adapter) holder.itemView.getTag(R.id.key_adapter_wrapper);
        if (adapter == null) {
            return;
        }
        adapter.onViewDetachedFromWindow(holder);

    }

    /**
     * 设置主Adapter
     *
     * @param mainAdapter
     */
    public void setMainAdapter(RecyclerView.Adapter mainAdapter) {
        if (this.mMainPair != null) {
            mMainPair.second.unregisterAdapterDataObserver(mMainPair.first);
        }
        mMainPair = null;
        if (mainAdapter == null) {
            return;
        }
        AdapterDataObserver adapterDataObserver = new AdapterDataObserver(null);
        mainAdapter.registerAdapterDataObserver(adapterDataObserver);
        mMainPair = new Pair<>(adapterDataObserver, mainAdapter);
    }

    /**
     * 获取适配器中的位置
     *
     * @param adapterRange
     * @param position
     * @return
     */
    private int getAdapterRangePosition(AdapterRange adapterRange, int position) {
        if (adapterRange == null) {
            return RecyclerView.NO_POSITION;
        }
        return position - adapterRange.startPositionOuter + adapterRange.startPositionInner;
    }

    private Pair<AdapterRange, RecyclerView.Adapter> findAdapterByPosition(int position) {
        if (ToolList.isNullOrEmpty(mAdapterRanges)) {
            // 数据异常抛出异常
            Log.e(AdapterWrapper.class.getSimpleName(),
                    "mAdapterRanges is empty");
        }
        final int count = mAdapterRanges.size();

        int s = 0, e = count - 1, m;
        Pair<AdapterRange, RecyclerView.Adapter> rs = null;

        // binary search range
        while (s <= e) {
            m = (s + e) / 2;
            rs = mAdapterRanges.get(m);
            int endPosition = rs.first.startPositionOuter + rs.first.rangeSize - 1;

            if (rs.first.startPositionOuter > position) {
                e = m - 1;
            } else if (endPosition < position) {
                s = m + 1;
            } else if (rs.first.startPositionOuter <= position && endPosition >= position) {
                break;
            }

            rs = null;
        }

        if (rs == null) {
            // 数据异常抛出异常
            Log.e(AdapterWrapper.class.getSimpleName(),
                    "无法找到");
        }

        return rs;
    }


    public void addAdapter(RecyclerView.Adapter adapter) {
        addAdapter(ToolList.getSize(mAdapters), null, adapter);
    }

    public void addAdapter(ImplantFormula implantFormula, RecyclerView.Adapter adapter) {
        addAdapter(ToolList.getSize(mAdapters), implantFormula, adapter);
    }

    /**
     * 添加
     *
     * @param index
     * @param implantFormula 为null 就在AdapterBaseImplant后面插入数据流
     * @param adapter
     */
    public void addAdapter(int index, ImplantFormula implantFormula, RecyclerView.Adapter adapter) {

        if (adapter == null) {
            return;
        }
        if (implantFormula == null) {
            implantFormula = (position, startPosition, isEnd) -> {
                if (isEnd) {
                    return adapter.getItemCount();
                }
                return 0;
            };
        }

        List<Pair<AdapterDataObserver, RecyclerView.Adapter>> newAdapter = new ArrayList<>(mAdapters);

        newAdapter.add(index, new Pair<>(new AdapterDataObserver(implantFormula), adapter));
        setAdapters(newAdapter);
    }

    public void removeFirstAdapter() {
        if (!mAdapters.isEmpty()) {
            RecyclerView.Adapter targetAdatper = mAdapters.get(0).second;
            removeAdapter(targetAdatper);
        }
    }

    public void removeLastAdapter() {
        if (!mAdapters.isEmpty()) {
            RecyclerView.Adapter targetAdatper = mAdapters.get(mAdapters.size() - 1).second;
            removeAdapter(targetAdatper);
        }
    }

    public void removeAdapter(int adapterIndex) {
        if (adapterIndex >= 0 && adapterIndex < mAdapters.size()) {
            RecyclerView.Adapter targetAdatper = mAdapters.get(adapterIndex).second;
            removeAdapter(targetAdatper);
        }
    }

    public void removeAdapter(@Nullable RecyclerView.Adapter targetAdapter) {
        if (targetAdapter == null) {
            return;
        }
        removeAdapters(Collections.singletonList(targetAdapter));
    }

    public void removeAdapters(@Nullable List<RecyclerView.Adapter> targetAdapters) {
        if (targetAdapters == null || targetAdapters.isEmpty()) {
            return;
        }
        for (int i = 0, size = targetAdapters.size(); i < size; i++) {
            RecyclerView.Adapter one = targetAdapters.get(i);
            Iterator<Pair<AdapterDataObserver, RecyclerView.Adapter>> itr = mAdapters.iterator();
            while (itr.hasNext()) {
                Pair<AdapterDataObserver, RecyclerView.Adapter> pair = itr.next();
                RecyclerView.Adapter theOther = pair.second;
                if (theOther.equals(one)) {
                    theOther.unregisterAdapterDataObserver(pair.first);
                    itr.remove();
                    break;
                }
            }
        }

        List<Pair<AdapterDataObserver, RecyclerView.Adapter>> newAdapter = new ArrayList<>();
        newAdapter.addAll(mAdapters);
        setAdapters(newAdapter);
    }


    public void setAdapters(@Nullable List<Pair<AdapterDataObserver, RecyclerView.Adapter>> adapters) {
        clearData();

        if (adapters == null) {
            adapters = Collections.emptyList();
        }

        for (int i = 0; i < adapters.size(); i++) {
            Pair<AdapterDataObserver, RecyclerView.Adapter> pair = adapters.get(i);
            pair.second.registerAdapterDataObserver(pair.first);
            mAdapters.add(pair);
        }

        updateAdapterRange();
    }

    private void clearData() {
        mTotal = 0;
        countPosition = 0;
        mItemTypeAry.clear();


        for (Pair<AdapterDataObserver, RecyclerView.Adapter> p : mAdapters) {
            p.second.unregisterAdapterDataObserver(p.first);
        }
        if (mAdapters != null) {
            mAdapters.clear();
        }
        if (mAdapterRanges != null) {
            mAdapterRanges.clear();
        }
    }


    private void updateAdapterRange() {
        LinkedList<Pair<AdapterRange, RecyclerView.Adapter>> temp = new LinkedList<>();
        mTotal = 0;
        countPosition = 0;

        if (mMainPair == null) {
            updateAdapterRangeAppend(temp);
        } else {
            updateAdapterRangeInsert(temp);
        }
        mAdapterRanges = temp;
    }

    private void updateAdapterRangeAppend(LinkedList<Pair<AdapterRange, RecyclerView.Adapter>> temp) {

        for (int i = 0; i < mAdapters.size(); i++) {
            Pair<AdapterDataObserver, RecyclerView.Adapter> pair = mAdapters.get(i);

            int adapterRangeCount = pair.second.getItemCount();

            if (adapterRangeCount <= 0) {
                continue;
            }
            AdapterRange adapterRange = createAdapterRange(mTotal, 0, 0, adapterRangeCount);
            temp.add(new Pair<>(adapterRange, pair.second));
            mTotal += adapterRangeCount;
        }
    }

    private void updateAdapterRangeInsert(LinkedList<Pair<AdapterRange, RecyclerView.Adapter>> temp) {

        Map<Pair<AdapterDataObserver, RecyclerView.Adapter>, Integer> startPositionMap = new HashMap<>();
        int position = 0;
        int itemCount = mMainPair.second.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            boolean isEnd = i == itemCount - 1;
            mTotal++;
            boolean isAddSelf = false;
            for (int j = 0; j < mAdapters.size(); j++) {
                Pair<AdapterDataObserver, RecyclerView.Adapter> pair = mAdapters.get(j);
                Integer integer = startPositionMap.get(pair);
                int startPosition = integer == null ? 0 : integer;
                int adapterRangeCount = pair.first.getImplantMatch().getAdapterRangeCount(i, startPosition, isEnd);

                if (adapterRangeCount <= 0) {
                    continue;
                }
                int maxCount = pair.second.getItemCount() - startPosition;
                if (maxCount <= 0) {
                    continue;
                }
                if (!isAddSelf) {
                    isAddSelf = true;
                    int count = i - this.countPosition + 1;
                    AdapterRange adapterRange = createAdapterRange(position
                            , this.countPosition, this.countPosition, count);
                    this.countPosition = i + 1;
                    position += count;
                    temp.add(new Pair<>(adapterRange, mMainPair.second));
                }
                if (adapterRangeCount > maxCount) {
                    adapterRangeCount = maxCount;
                }
                AdapterRange adapterRange = createAdapterRange(mTotal, i, startPosition, adapterRangeCount);
                temp.add(new Pair<>(adapterRange, pair.second));
                startPosition += adapterRangeCount;
                startPositionMap.put(pair, startPosition);
                position += adapterRangeCount;
                mTotal += adapterRangeCount;
            }
            if (isEnd && !isAddSelf) {
                int count = i - this.countPosition + 1;
                AdapterRange adapterRange = createAdapterRange(position
                        , this.countPosition, this.countPosition, count);
                this.countPosition = i + 1;
                position += count;
                temp.add(new Pair<>(adapterRange, mMainPair.second));
            }
        }
    }

    private AdapterRange createAdapterRange(int startPositionOuter, int position
            , int startPositionInner, int rangeSize) {
        AdapterRange adapterRange = new AdapterRange();
        adapterRange.startPositionOuter = startPositionOuter;
        adapterRange.position = position;
        adapterRange.startPositionInner = startPositionInner;
        adapterRange.rangeSize = rangeSize;
        return adapterRange;
    }

    public void clear() {
        mMainPair = null;
        clearData();
    }

    public interface ImplantFormula {

        /**
         * 获取AdapterRange 的范围大小
         *
         * @param position      MainAdapter 的position
         * @param startPosition adapter 开始的位置
         * @param isEnd         MainAdapter 是否已经最后的位置了
         * @return <= 0 : 代表不给adapter 分配AdapterRange的范围
         */
        int getAdapterRangeCount(int position, int startPosition, boolean isEnd);
    }

    protected class AdapterDataObserver extends RecyclerView.AdapterDataObserver {

        ImplantFormula mImplantFormula;

        public AdapterDataObserver(ImplantFormula implantFormula) {
            this.mImplantFormula = implantFormula;
        }

        public ImplantFormula getImplantMatch() {
            return mImplantFormula;
        }

        @Override
        public void onChanged() {
            updateAdapterRange();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateAdapterRange();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateAdapterRange();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            updateAdapterRange();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            updateAdapterRange();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            updateAdapterRange();
            notifyDataSetChanged();
        }
    }


    /**
     * 适配器的区间
     */
    private class AdapterRange {

        /**
         * adapter 外部 的开始位置
         */
        private int startPositionOuter;

        /**
         * {@link #mMainPair} 中的位置
         */
        private int position;

        /**
         * adapter 内部 开始位置
         */
        private int startPositionInner;

        /**
         * 范围大小
         * NOTE: 不可能为0
         */
        private int rangeSize;
    }

}
