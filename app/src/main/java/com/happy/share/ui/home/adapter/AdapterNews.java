package com.happy.share.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.happy.share.R;
import com.happy.share.common.glide.ImageLoader;
import com.happy.share.ui.home.bean.NewsBean;
import com.happy.share.widget.recyclerview.BaseViewHolder;
import com.happy.share.widget.vlayout.AdapterBase;


/**
 * desc: 首页新闻列表适配器 <br/>
 * time: 2018/11/24 15:29 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class AdapterNews extends AdapterBase<AdapterNews.NewsVH, NewsBean> {

    public AdapterNews(@NonNull Context context) {
        super(context, (NewsBean) null);
    }

    @NonNull
    @Override
    public AdapterNews.NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater();
        View root = inflater.inflate(R.layout.item_recycler_news_layout, parent, false);
        return new AdapterNews.NewsVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNews.NewsVH holder, int position) {
        super.onBindViewHolder(holder, position);
        NewsBean item = getList().get(position);
        if (item != null) {
            holder.tvTitle.setText(item.getNewTitle());
            holder.tvFrom.setText(item.getFromName());
            holder.tvCount.setText(String.valueOf(item.getViewCounts()));

            ImageLoader.getInstance().load(item.getNewsUrl())
                    .with(getContext())
                    .placeholder(R.drawable.icon_big_pic_default)
                    .transforms(new CenterCrop(),new RoundedCorners(5))
                    .into(holder.ivIcon);
        }
    }

    static class NewsVH extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvFrom;
        private TextView tvCount;

        NewsVH(View itemView) {
            super(itemView);
            findView(itemView);
        }

        private void findView(View itemView) {
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvFrom = itemView.findViewById(R.id.tv_from);
            tvCount = itemView.findViewById(R.id.tv_count);

            addOnClickListener(itemView.findViewById(R.id.rl_item));
        }

    }
}
