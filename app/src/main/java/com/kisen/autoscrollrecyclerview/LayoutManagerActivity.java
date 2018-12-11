package com.kisen.autoscrollrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.kisen.autoscrollrecyclerview.R;
import com.kisen.layoutmanager.ASLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class LayoutManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);
        setupView();
    }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ASLinearLayoutManager layout = new ASLinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(generateAdapter());
        layout.getLayoutManagerHelper().setOpenAuto(true);
    }

    private RecyclerView.Adapter generateAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Item " + i);
        }
        return new AutoAdapter(list);
    }
}
