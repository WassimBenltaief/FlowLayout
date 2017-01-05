package com.beltaief.flowlayoutexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beltaief.flowlayout.FlowLayout;
import com.beltaief.flowlayout.util.ViewMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.reveLayout)
    FlowLayout flowLayout;

    private ItemsAdapter mAdapter = new ItemsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);

        flowLayout.setConnectivityAware(true);
        flowLayout.setMode(ViewMode.PROGRESS);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter);

        getData();
    }

    /**
     * get empty result
     */
    private void getData() {
        final List<String> items = new ArrayList<>();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setData(items);
            }
        }, 2000);
    }

    private void setData(List<String> items) {
        if(items.size() > 0) {
            flowLayout.setMode(ViewMode.CONTENT);
        } else {
            flowLayout.setMode(ViewMode.EMPTY);
        }
        mAdapter.setData(items);
    }
}
