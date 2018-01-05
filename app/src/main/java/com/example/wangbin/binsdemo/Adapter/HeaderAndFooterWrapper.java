package com.example.wangbin.binsdemo.Adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by momo on 2017/12/28.
 */

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }

    private boolean isHeadViewPos(int position){
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size()+BASE_ITEM_TYPE_HEADER,view);
    }
    public void deleteHeaderView(){
        mHeaderViews.clear();
    }

    public void addFooterView(View view){
        mFootViews.put(mFootViews.size()+BASE_ITEM_TYPE_FOOTER ,view);
    }

    public int getHeadersCount(){
        return mHeaderViews.size();
    }
    public int getFootersCount(){
        return mFootViews.size();
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderViews.get(viewType)!=null){
            return new RecyclerAdapter.ViewHolder(mHeaderViews.get(viewType));
        }
        if (mFootViews.get(viewType)!=null){
            return new RecyclerAdapter.ViewHolder(mFootViews.get(viewType));
        }
        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (isHeadViewPos(position))
            return;
        if (isFooterViewPos(position))
            return;
        mInnerAdapter.onBindViewHolder(holder,position-getHeadersCount());

    }

    @Override
    public int getItemViewType(int position) {

        if (isHeadViewPos(position))
            return mHeaderViews.keyAt(position);
        if (isFooterViewPos(position))
            return mFootViews.keyAt(position);
        return mInnerAdapter.getItemViewType(position-getHeadersCount());
    }



    @Override
    public int getItemCount() {
        return getHeadersCount()+getRealItemCount()+getFootersCount();
    }
}
