package com.kisen.autoscrollrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by huangwy on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.AutoViewHolder> {

    private List<String> items;

    AutoAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
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

        void bind(final String item) {
            TextView viewById = (TextView) itemView.findViewById(R.id.item);
            viewById.setText(item);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "TOAST : " + item, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
