package com.kisen.autoscrollrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisen.lib.AutoScrollRecyclerView;
import com.zhulong.eduvideo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoScrollRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView = (AutoScrollRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(generateAdapter());
//        recyclerView.openAutoScroll();
    }

    private RecyclerView.Adapter generateAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Item " + i);
        }
        return new AutoAdapter(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start:
                recyclerView.openAutoScroll();
                break;
            case R.id.action_revert:
                recyclerView.setReverse(!recyclerView.getReverse());
                break;
            case R.id.action_loop:
                recyclerView.setLoopEnabled(!recyclerView.isLoopEnabled());
                break;
            case R.id.action_can_touch:
                recyclerView.setCanTouch(!recyclerView.canTouch());
                break;
        }
        return super.onOptionsItemSelected(item);
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
