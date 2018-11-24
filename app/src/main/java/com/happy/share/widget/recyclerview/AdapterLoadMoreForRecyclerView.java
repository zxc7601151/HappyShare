package com.happy.share.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.happy.share.R;
import com.happy.share.tools.ToolSize;

import java.util.List;


/**
 * desc: 实现RecyclerView 自动加载更多功能 <br/>
 * time: 2018/11/24 14:32 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class AdapterLoadMoreForRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	/**
	 * 数据为空时显示的header
	 */
	public final static int TYPE_HEADER_EMPTY = 2000;
	/**
	 * 上拉加载更多时显示loading footer
	 */
	public final static int TYPE_FOOTER_LOADING = 2001;
	/**
	 * 上拉没有更多数据时显示的footer
	 */
	public final static int TYPE_FOOTER_NO_MORE = 2002;
	/**
	 * 上拉加载失败时显示的footer
	 */
	public final static int TYPE_FOOTER_LOAD_FAILED = 2003;
	/**
	 * 上拉加载未结束时显示的占位footer
	 */
	public final static int TYPE_FOOTER_LOADING_PLACE = 2004;

	private RecyclerView.Adapter mInternalAdapter;
	private RecyclerViewLoadMore mRecyclerView;
	private AdapterLoadMoreDataObserver mObserver;

	public AdapterLoadMoreForRecyclerView(RecyclerViewLoadMore recyclerViewLoadMore) {
		this.mRecyclerView = recyclerViewLoadMore;
		mObserver = new AdapterLoadMoreDataObserver();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_FOOTER_LOADING) {
			return new FooterViewHolder(TYPE_FOOTER_LOADING, LayoutInflater.from(parent.getContext()).inflate(R.layout.module_base_layout_recycler_load_more, parent, false));
		} else if (viewType == TYPE_FOOTER_NO_MORE) {
			return new FooterViewHolder(TYPE_FOOTER_NO_MORE, LayoutInflater.from(parent.getContext()).inflate(R.layout.module_base_item_recycler_no_more, parent, false));
		} else if (viewType == TYPE_FOOTER_LOAD_FAILED) {
			return new FooterViewHolder(TYPE_FOOTER_LOAD_FAILED, LayoutInflater.from(parent.getContext()).inflate(R.layout.module_base_item_recycler_load_failed, parent, false));
		} else if (viewType == TYPE_HEADER_EMPTY) {
			return new FooterViewHolder(TYPE_HEADER_EMPTY, createEmptyView());
		} else if (viewType == TYPE_FOOTER_LOADING_PLACE) {
			View view = new View(parent.getContext());
			RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
					, ToolSize.dp2Px(parent.getContext(), 100));
			view.setLayoutParams(layoutParams);
			return new FooterViewHolder(TYPE_FOOTER_LOADING_PLACE, view);
		}
		return mInternalAdapter.onCreateViewHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		//不实现
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
		super.onBindViewHolder(holder, position, payloads);
		int type = getItemViewType(position);
		if (type == TYPE_HEADER_EMPTY) {
			updateEmptyView(holder);
		}
		if (type != TYPE_FOOTER_LOADING && type != TYPE_FOOTER_LOAD_FAILED && type != TYPE_FOOTER_NO_MORE && type != TYPE_HEADER_EMPTY && type != TYPE_FOOTER_LOADING_PLACE) {
			mInternalAdapter.onBindViewHolder(holder, position, payloads);
		}
	}

	private View createEmptyView() {
		FrameLayout result = new FrameLayout(mRecyclerView.getContext());
		RecyclerView.LayoutParams layoutParams1 = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		result.setLayoutParams(layoutParams1);
		View emptyView = mRecyclerView.getEmptyView();
		if (null == emptyView) {
			return result;
		}
		if (null != emptyView.getParent()) {
            ((ViewGroup)emptyView.getParent()).removeAllViews();
        }
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		result.addView(emptyView, layoutParams);
		return result;
	}

	private void updateEmptyView(RecyclerView.ViewHolder holder) {
		View emptyView = mRecyclerView.getEmptyView();
		if (null == emptyView) {
			return;
		}

		((FrameLayout)holder.itemView).removeAllViews();
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		((FrameLayout)holder.itemView).addView(emptyView, layoutParams);
	}

	@Override
	public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
		if (holder.getItemViewType() != TYPE_FOOTER_LOADING
				&& holder.getItemViewType() != TYPE_FOOTER_NO_MORE
				&& holder.getItemViewType() != TYPE_HEADER_EMPTY
				&& holder.getItemViewType() != TYPE_FOOTER_LOADING_PLACE) {
			mInternalAdapter.onViewAttachedToWindow(holder);
		}
	}

	@Override
	public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
		if (holder.getItemViewType() != TYPE_FOOTER_LOADING
				&& holder.getItemViewType() != TYPE_FOOTER_NO_MORE
				&& holder.getItemViewType() != TYPE_HEADER_EMPTY
				&& holder.getItemViewType() != TYPE_FOOTER_LOADING_PLACE) {
			mInternalAdapter.onViewDetachedFromWindow(holder);
		}
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mInternalAdapter.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
		mInternalAdapter.onDetachedFromRecyclerView(recyclerView);
	}

	@Override
	public int getItemCount() {
		if (mInternalAdapter == null) {
			return 0;
		}

		return mInternalAdapter.getItemCount() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getItemCount() - 1) {
			int countTemp = mInternalAdapter.getItemCount();
			if (countTemp != 0) {
				if (mRecyclerView.isLoadMoreEnable()) {
					if (mRecyclerView.isLoadingMore()) {
						return TYPE_FOOTER_LOADING;
					} else {
						return TYPE_FOOTER_LOADING_PLACE;
					}
				} else if (mRecyclerView.isLoadFailed()) {
					return TYPE_FOOTER_LOAD_FAILED;
				} else {
					return TYPE_FOOTER_NO_MORE;
				}
			} else {
				return TYPE_HEADER_EMPTY;
			}
		}
		return mInternalAdapter.getItemViewType(position);
	}

	@Override
	public void onViewRecycled(RecyclerView.ViewHolder holder) {
		super.onViewRecycled(holder);
		if (holder instanceof FooterViewHolder) {
			return;
		}
		mInternalAdapter.onViewRecycled(holder);
	}

	public void setInternalAdapter(RecyclerView.Adapter internalAdapter) {
		if (mInternalAdapter != null) {
			mInternalAdapter.unregisterAdapterDataObserver(mObserver);
		}
		this.mInternalAdapter = internalAdapter;
		if (internalAdapter != null) {
			internalAdapter.registerAdapterDataObserver(mObserver);
		}
	}

	class FooterViewHolder extends RecyclerView.ViewHolder {

		public FooterViewHolder(int itemViewType,View itemView) {
			super(itemView);
			if (itemViewType == TYPE_FOOTER_LOAD_FAILED) {
				itemView.setOnClickListener(v -> mRecyclerView.loadMoreAgainForClick());
			}
		}
	}

	private class AdapterLoadMoreDataObserver extends RecyclerView.AdapterDataObserver {

		@Override
		public void onChanged() {
			notifyDataSetChanged();
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			notifyItemRangeRemoved(positionStart, itemCount);
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			notifyItemRangeInserted(positionStart, itemCount);
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			notifyItemMoved(fromPosition, toPosition);
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {
			notifyItemRangeChanged(positionStart, itemCount);
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
			notifyItemRangeChanged(positionStart, itemCount, payload);
		}
	}
}
