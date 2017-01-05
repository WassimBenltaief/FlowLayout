package com.beltaief.flowlayoutexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.beltaief.flowlayout.FlowLayout;
import com.beltaief.flowlayout.util.ViewMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wassim on 10/10/16.
 */

public class ExperimentActivity extends AppCompatActivity {

    @BindView(R.id.statusLayout)
    FlowLayout flowLayout;

    private ItemsAdapter mAdapter = new ItemsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        ButterKnife.bind(this);

        flowLayout.setConnectivityAware(true);
        flowLayout.setMode(ViewMode.PROGRESS);

        getData();
    }

    /**
     * get empty result
     */
    private void getData() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showContent();
            }
        }, 2000);
    }

    private void showContent() {
        flowLayout.setMode(ViewMode.ERROR);
    }
}
