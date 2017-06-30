package com.kisen.authscrollrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisen.lib.AutoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        AutoScrollRecyclerView recyclerView = (AutoScrollRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(generateAdapter());
        recyclerView.setLoopEnabled(true);
        recyclerView.startAutoScroll();
    }

    private RecyclerView.Adapter generateAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Item " + i);
        }
        return new AutoAdapter(list);
    }

    private static class AutoAdapter extends RecyclerView.Adapter<AutoViewHolder> {

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
