package com.beltaief.flowlayoutexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wassim on 9/22/16.
 */
class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mCollection = new ArrayList<>();

    public ItemsAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder mHolder = (ItemHolder) holder;
        mHolder.itemText.setText(mCollection.get(position));
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void setData(List<String> collection) {
        mCollection.clear();
        mCollection.addAll(collection);
        notifyDataSetChanged();
    }

    private class ItemHolder extends RecyclerView.ViewHolder{

        TextView itemText;

        ItemHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.item_text);
        }

    }
}
