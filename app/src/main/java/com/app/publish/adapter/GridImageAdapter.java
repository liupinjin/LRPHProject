package com.app.publish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.app.publish.ICallBack;
import com.app.publish.holder.ImageHolder;
import com.punuo.sys.app.recyclerview.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by han.chen.
 * Date on 2019-06-21.
 **/
public class GridImageAdapter extends BaseRecyclerViewAdapter<String> {
    private ICallBack mCallBack;
    public GridImageAdapter(Context context, List<String> data, ICallBack callBack) {
        super(context, data);
        mCallBack = callBack;
    }

    public void resetData(List<String> list) {
        mData.clear();
        if (list.size() < 9) {
            mData.addAll(list);
            mData.add("add");
        } else {
            mData.addAll(list.subList(0, 9));
        }
        notifyDataSetChanged();
    }

    public void addData(String image) {
        mData.add(image);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(mContext, parent, mCallBack);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder baseViewHolder, int position) {
        if (baseViewHolder instanceof ImageHolder) {
            ((ImageHolder) baseViewHolder).bind(getItem(position), position);
        }
    }

    @Override
    public int getBasicItemType(int position) {
        return 0;
    }

    @Override
    public int getBasicItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
