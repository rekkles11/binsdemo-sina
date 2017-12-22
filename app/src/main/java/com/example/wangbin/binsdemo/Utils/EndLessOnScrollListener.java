package com.example.wangbin.binsdemo.Utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by momo on 2017/12/22.
 */

public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;
    private int page;
    private int totalItemCount;
    private int previousTotal =0;
    private int visibleItemCount;
    private int firstVisibleItemPosition;
    private boolean loading = true;

    public  EndLessOnScrollListener(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount =recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (loading){
            if (totalItemCount>previousTotal){
                loading = false;
                previousTotal =totalItemCount;
            }
        }
        if (!loading&&totalItemCount-visibleItemCount<=firstVisibleItemPosition&&page<2){
            page++;
            onLoadMore(page);
            loading = true;
        }

    }

    public abstract void onLoadMore(int page);


}
