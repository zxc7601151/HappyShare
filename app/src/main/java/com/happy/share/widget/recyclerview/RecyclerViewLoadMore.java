package com.happy.share.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

/**
 * desc：重写RecyclerView 实现上拉加载更多功能 <br/>
 * time: 16/8/17 上午10:57 <br/>
 * author：匡衡 <br/>
 * since V 4.6 <br/>
 */
public class RecyclerViewLoadMore extends RecyclerView {
	private static final int LOADED_MORE_DEFAULT_POSITION = 3;//进行自动加载时最后显示的位置默认值

	private AdapterLoadMoreForRecyclerView mLoadMoreAdapter;
	private OnLoadMoreListener mOnLoadMoreListener;
	private OnRecycleViewScrollListener mOnRecycleViewScrollListener;

	private boolean mIsLoadMoreEnable = true;//是否允许加载更多
	private boolean mIsLoadingMore;//正在加载更多标示
	private boolean mIsLoadFailed;//加载失败标示

	private int mLastLoadedIndex;//自动加载时的最后显示的位置参数

	/**
	 * 没有条目时显示的视图
	 */
	private View mEmptyView;

	public RecyclerViewLoadMore(Context context) {
		super(context);
		initScrollListener();
	}

	public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initScrollListener();
	}

	public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initScrollListener();
	}

	public boolean isLoadMoreEnable() {
		return mIsLoadMoreEnable;
	}

	public void setLoadMoreEnable(boolean loadMoreEnable) {
		this.mIsLoadMoreEnable = loadMoreEnable;
	}

	public boolean isLoadingMore() {
		return mIsLoadingMore;
	}

	public void setLoadingMore(boolean loadingMore) {
		this.mIsLoadingMore = loadingMore;
	}

	public void setLastLoadedIndex(int lastLoadedIndex) {
		this.mLastLoadedIndex = lastLoadedIndex;
	}

	@Override
	public void setAdapter(Adapter adapter) {
		if (mLoadMoreAdapter == null) {
			mLoadMoreAdapter = new AdapterLoadMoreForRecyclerView(this);
		}
		if (adapter != null) {
			mLoadMoreAdapter.setInternalAdapter(adapter);
		}
		super.setAdapter(mLoadMoreAdapter);
	}

	@Override
	public void setLayoutManager(LayoutManager layout) {
		if (layout instanceof GridLayoutManager) {
			mLastLoadedIndex = ((GridLayoutManager) layout).getSpanCount();
		}
		super.setLayoutManager(layout);
	}

	/**
	 * 通知更多的数据已经加载
	 * <p/>
	 * 每次加载完成之后添加了Data数据，用notifyItemRemoved来刷新列表展示，
	 * 而不是用notifyDataSetChanged来刷新列表
	 *
	 * @param hasMore 是否还有更多数据， true: 还有更多数据，上拉到底部会显示加载更多
	 */
	public void loadMoreFinish(boolean hasMore) {
		mIsLoadingMore = false;
		setLoadMoreEnable(hasMore);
	}

    public boolean isLoadFailed() {
        return mIsLoadFailed;
    }

	/**
	 * 加载失败时调用
	 */
	public void loadMoreFailed() {
		mIsLoadFailed = true;
		loadMoreFinish(false);
		getAdapter().notifyItemChanged(mLoadMoreAdapter.getItemCount() - 1);
	}

	/**
	 * 加载失败时，点击重新加载更多
	 */
	public void loadMoreAgainForClick() {
		if (!mIsLoadFailed) {
			return;
		}
		preLoadMoreView();
		this.scrollToPosition(mLoadMoreAdapter.getItemCount() - 1);
		mOnLoadMoreListener.onLoadMore();
	}

	private void initScrollListener() {
		mLastLoadedIndex = LOADED_MORE_DEFAULT_POSITION;
		((DefaultItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
		super.addOnScrollListener(new OnLoadMoreScrollListener());
	}

	/**
	 * @return 获取最后一个显示的位置
	 */
	private int getLastVisiblePosition() {
		int position;
		if (getLayoutManager() instanceof LinearLayoutManager) {
			position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
		} else if (getLayoutManager() instanceof GridLayoutManager) {
			position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
		} else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
			int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
			position = getMaxPosition(lastPositions);
		} else {
			position = getLayoutManager().getItemCount() - 1;
		}
		return position;
	}

	/**
	 * @param positions 位置数据
	 * @return 获得最大的位置
	 */
	private int getMaxPosition(int[] positions) {
		int maxPosition = Integer.MIN_VALUE;
		for (int position : positions) {
			maxPosition = Math.max(maxPosition, position);
		}
		return maxPosition;
	}

	/**
	 * 在加载更多之前做准备工作
	 */
	private void preLoadMoreView() {
		mIsLoadFailed = false;
		mIsLoadingMore = true;
		setLoadMoreEnable(true);
		if (getAdapter() != null) {
			getAdapter().notifyItemChanged(mLoadMoreAdapter.getItemCount() - 1);
		}
	}

	/**
	 * 设置适配器为空时显示的视图
	 *
	 * @param emptyView 适配器为空时显示的视图
	 */
	public void setEmptyView(View emptyView) {
		mEmptyView = emptyView;
	}

	public View getEmptyView() {
		return mEmptyView;
	}

	public void setRecycleViewScrollListener(OnRecycleViewScrollListener scrollListener) {
		this.mOnRecycleViewScrollListener = scrollListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.mOnLoadMoreListener = onLoadMoreListener;
	}

	/**
	 * 滑动监听
	 */
	public interface OnRecycleViewScrollListener {
		void onScrollStateChanged(RecyclerView recyclerView, int newState);
		void onScrolled(RecyclerView recyclerView, int dx, int dy);
	}

	/**
	 * 加载更多监听
	 */
	public interface OnLoadMoreListener {
		void onLoadMore();
	}

	/**
	 * 滑动监听，用于处理加载更多功能 <br/>
	 */
	private class OnLoadMoreScrollListener extends OnScrollListener {

		private int mNorScrollY = -1;

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			if (newState == SCROLL_STATE_DRAGGING && !mIsLoadingMore  && mNorScrollY == 0
					&& mIsLoadMoreEnable && null != mOnLoadMoreListener) {
                int lastVisiblePosition = getLastVisiblePosition();
                if (lastVisiblePosition >= mLoadMoreAdapter.getItemCount() - mLastLoadedIndex) {
                    preLoadMoreView();
                    mOnLoadMoreListener.onLoadMore();
                }
			}
			if (mOnRecycleViewScrollListener != null) {
				mOnRecycleViewScrollListener.onScrollStateChanged(recyclerView, newState);
			}
		}

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			mNorScrollY = dy;
			if (null != mOnLoadMoreListener && mIsLoadMoreEnable && !mIsLoadingMore && dy > 0) {
				int lastVisiblePosition = getLastVisiblePosition();
				if (lastVisiblePosition >= mLoadMoreAdapter.getItemCount() - mLastLoadedIndex) {
					preLoadMoreView();
					mOnLoadMoreListener.onLoadMore();
				}
			}
			if (mOnRecycleViewScrollListener != null) {
				mOnRecycleViewScrollListener.onScrolled(recyclerView, dx, dy);
			}
		}

	}

}
