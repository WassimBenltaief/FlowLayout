package com.beltaief.flowlayoutexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.list_content_example)
    Button listContentExample;
    @BindView(R.id.empty_example)
    Button emptyExample;
    @BindView(R.id.custom_empty_example)
    Button customEmptyExample;
    @BindView(R.id.custom_connectivity_text_color)
    Button customConnectivityTextColor;
    @BindView(R.id.custom_connectivity_layout)
    Button customConnectivityLayout;
    @BindView(R.id.custom_progress)
    Button customProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.list_content_example,
            R.id.custom_connectivity_text_color,
            R.id.custom_connectivity_layout,
            R.id.empty_example,
            R.id.custom_empty_example,
            R.id.custom_progress
            })
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.list_content_example:
                intent = new Intent(this, ContentActivity.class);
                break;
            case R.id.empty_example:
                intent = new Intent(this, EmptyActivity.class);
                break;
            case R.id.custom_connectivity_text_color:
                intent = new Intent(this, CustomConnectivityColorActivity.class);
                break;
            case R.id.custom_connectivity_layout:
                intent = new Intent(this, CustomConnectivityLayoutActivity.class);
                break;
            case R.id.custom_empty_example:
                intent = new Intent(this, CustomEmptyActivity.class);
                break;
            case R.id.custom_progress:
                intent = new Intent(this, CustomProgressActivity.class);
                break;
        }
        startActivity(intent);
    }
}
