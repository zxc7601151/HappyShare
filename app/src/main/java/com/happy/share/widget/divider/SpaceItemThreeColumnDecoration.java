package com.happy.share.widget.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * desc: 3列表格布局间距 <br/>
 * time:2018/3/15<br/>
 * author: 周峰 <br/>
 * since V 1.2.0 <br/>
 */

public class SpaceItemThreeColumnDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemThreeColumnDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % 3 == 0) {
            outRect.left = 0;
            outRect.bottom = space;
        } else if (parent.getChildLayoutPosition(view) % 3 == 1) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = space;
        }
    }

}

