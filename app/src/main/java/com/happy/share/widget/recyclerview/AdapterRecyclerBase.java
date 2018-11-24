package com.happy.share.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * desc：RecyclerView适配器基类 <br/>
 * time: 2017/11/7 下午5:26 <br/>
 * author：匡衡 <br/>
 * since V 1.0 <br/>
 */
public abstract class AdapterRecyclerBase<VH extends BaseViewHolder, T> extends RecyclerView.Adapter<VH> {
	
	private Context mContext;
	private List<T> mList;
	private LayoutInflater mLayoutInflater;

	private OnRecyclerItemClickListener mOnRecyclerItemClickListener;
	private OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;

	public AdapterRecyclerBase(Context context, List<T> list) {
		this.mContext = context;
		this.mList = list;
		this.mLayoutInflater = LayoutInflater.from(context);
	}

	/**
	 * PS：子类重写时 一定要带上 <br/>
	 * super.onBindViewHolder(viewHolder, position);
	 */
	@Override
	public void onBindViewHolder(VH holder, int position) {
		if (!holder.hasClick() && mOnRecyclerItemClickListener != null) {
			holder.setOnRecyclerItemClickListener(mOnRecyclerItemClickListener);
		}

		if (!holder.hasLongClick() && mOnRecyclerItemLongClickListener != null) {
			holder.setOnRecyclerItemLongClickListener(mOnRecyclerItemLongClickListener);
		}
	}

	@Override
	public int getItemCount() {
		return mList == null ? 0 : mList.size();
	}

	public LayoutInflater getLayoutInflater() {
		return mLayoutInflater;
	}

	public Context getContext() {
		return mContext;
	}

	public void setList(List<T> list) {
		this.mList = list;
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

	public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
		if (onItemClickListener != null) {
			this.mOnRecyclerItemClickListener = onItemClickListener;
		}
	}

	public void setOnItemLongClickListener(OnRecyclerItemLongClickListener onItemLongClickListener) {
		if (onItemLongClickListener != null) {
			this.mOnRecyclerItemLongClickListener = onItemLongClickListener;
		}
	}

	public interface OnRecyclerItemClickListener {
		void onItemClick(View v, int position);
	}

	public interface OnRecyclerItemLongClickListener {
		void onItemLongClick(View v, int position);
	}

}

