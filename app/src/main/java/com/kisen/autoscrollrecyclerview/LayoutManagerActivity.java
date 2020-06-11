package com.kisen.autoscrollrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.kisen.layoutmanager.ASLinearLayoutManager;
import com.kisen.layoutmanager.widget.LayoutManagerHelper;

import java.util.ArrayList;
import java.util.List;

public class LayoutManagerActivity extends AppCompatActivity {

    private LayoutManagerHelper mLayoutManagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);
        setupView();
    }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ASLinearLayoutManager manager = new ASLinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(generateAdapter());
        mLayoutManagerHelper = manager.getLayoutManagerHelper();
        mLayoutManagerHelper.setCanTouch(true);
    }

    private AutoAdapter generateAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Item " + i);
        }
        return new AutoAdapter(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_linear, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start:
                mLayoutManagerHelper.openAutoScroll();
                break;
            case R.id.action_revert:
                mLayoutManagerHelper.setReverseLayout(!mLayoutManagerHelper.getReverseLayout());
                break;
            case R.id.action_loop:
                mLayoutManagerHelper.setLoopEnabled(!mLayoutManagerHelper.isLoopEnabled());
                break;
            case R.id.action_can_touch:
                mLayoutManagerHelper.setCanTouch(!mLayoutManagerHelper.isCanTouch());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
