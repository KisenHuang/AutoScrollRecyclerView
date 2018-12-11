package com.kisen.autoscrollrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangwy on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.AutoViewHolder> {

    private List<String> items = new ArrayList<>();

    AutoAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public AutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new AutoViewHolder(content);
    }

    @Override
    public void onBindViewHolder(AutoViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class AutoViewHolder extends RecyclerView.ViewHolder {

        AutoViewHolder(View itemView) {
            super(itemView);
        }

        void bind(String item) {
            ((TextView) itemView.findViewById(R.id.item)).setText(item);
        }
    }
}
