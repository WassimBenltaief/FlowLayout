package com.beltaief.flowlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beltaief.flowlayout.util.ColorUtil;
import com.beltaief.flowlayout.util.ConnectivityListener;
import com.beltaief.flowlayout.util.NetworkReceiver;
import com.beltaief.flowlayout.util.NetworkUtil;

/**
 * Created by wassim on 9/20/16.
 */

/**
 * A custom Relative layout that is able to display content, empty view, progress bar
 * and network connectivity status.
 * The network connectivity and the empty view are customizable.
 * The network connectivity view text, textColor and background color can be overridden usnig the
 * appropriates methods or via attributes in Xml.
 */
public class FlowLayout extends RelativeLayout implements ConnectivityListener {

    private boolean isConnected = false;
    private boolean mConnectivityAware = false;

    // connected
    private int mConnectedText = R.string.text_connected;
    private int mConnectedTextColor = R.color.connectivity_color;
    private int mConnectedBackground = R.color.connected_color;

    // disconnected

    private int mDisconnectedText = R.string.text_disconnected;
    private int mDisconnectedTextColor = R.color.connectivity_color;
    private int mDisconnectedBackground = R.color.disconnected_color;

    // error
    private int mErrorText = R.string.text_error;

    // empty
    private int mEmptyLayout = R.layout.layout_empty;
    private int mEmptyText = R.string.text_empty;
    private int mEmptyTextColor = R.color.text_empty_color;

    // refresh
    private int mProgressStyle = R.style.progress_bar;

    private Context mContext;
    private RelativeLayout contentView;
    private RelativeLayout emptyView;
    private TextView textEmpty;
    private RelativeLayout connectivityView;
    private TextView connectivityText;
    private RelativeLayout progressView;

    private NetworkReceiver mReceiver;

    public enum MODE {
        PROGRESS,
        EMPTY,
        CONTENT
    }

    public FlowLayout(Context context) {
        super(context);
        mContext = context;
        init(null, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    /**
     * Init view
     * @param attrs use attributes to build the view
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            // connectivity
            mConnectivityAware = a.getBoolean(R.styleable.FlowLayout_isConnectivityAware, mConnectivityAware);

            // connected
            mConnectedText = a.getResourceId(R.styleable.FlowLayout_connectedText, mConnectedText);
            mConnectedTextColor = a.getResourceId(R.styleable.FlowLayout_connectedTextColor, mConnectedTextColor);
            mConnectedBackground = a.getResourceId(R.styleable.FlowLayout_connectedBackground, mConnectedBackground);

            // disconnected
            mDisconnectedText = a.getResourceId(R.styleable.FlowLayout_disconnectedText, mDisconnectedText);
            mDisconnectedTextColor = a.getResourceId(R.styleable.FlowLayout_disconnectedTextColor, mDisconnectedTextColor);
            mDisconnectedBackground = a.getResourceId(R.styleable.FlowLayout_disconnectedBackground, mDisconnectedBackground);

            // error
            mErrorText = a.getResourceId(R.styleable.FlowLayout_errorText, mErrorText);

            // empty
            mEmptyLayout = a.getResourceId(R.styleable.FlowLayout_emptyLayout, mEmptyLayout);
            mEmptyText = a.getResourceId(R.styleable.FlowLayout_emptyText, mEmptyText);
            mEmptyTextColor = a.getResourceId(R.styleable.FlowLayout_emptyTextColor, mEmptyTextColor);

        } finally {
            a.recycle();
        }
        postInit();
    }

    /**
     * Inflate the custom view default layout
     */
    private void postInit() {
        // initialize connectivity status one time
        isConnected = NetworkUtil.getConnectivityStatus(mContext);
        // inflate view
        LayoutInflater.from(mContext).inflate(R.layout.reve_layout, this);

        contentView = (RelativeLayout) findViewById(R.id.content);

        emptyView = (RelativeLayout) findViewById(R.id.emptyView);
        textEmpty = (TextView) findViewById(R.id.text_empty);

        connectivityView = (RelativeLayout) findViewById(R.id.connectivity);
        connectivityText = (TextView) findViewById(R.id.connectivity_text);

        progressView = (RelativeLayout) findViewById(R.id.progress_view);

        initConnectivity();
        inflateLayouts();
    }

    /**
     * set different parts of the custom view using the attributes passed by
     * via XML or programmatically.
     */
    private void inflateLayouts() {
        inflateEmptyView();
        inflateEmptyText();
        inflateEmptyTextColor();
        if (mConnectivityAware) {
            inflateConnectedText();
            inflateConnectedTextColor();
            inflateConnectedBackground();
        }
    }

    /**
     * Set the connectivity background color when phone is connected
     */
    private void inflateConnectedBackground() {
        if (mConnectedBackground != R.color.connected_color) {
            connectivityView.setBackgroundColor(ColorUtil.getColorWrapper(mContext, mConnectedBackground));
        }
    }

    /**
     * Set the connectivity text color when phone is connected
     */
    private void inflateConnectedTextColor() {
        if (mConnectedTextColor != R.color.connectivity_color) {
            connectivityText.setTextColor(ColorUtil.getColorWrapper(mContext, mConnectedTextColor));
        }
    }

    /**
     * Set the connectivity text when phone is connected
     */
    private void inflateConnectedText() {
        if (mConnectedText != R.string.text_connected) {
            connectivityText.setText(mConnectedText);
        }
    }

    /**
     * Set the empty view text color
     */
    private void inflateEmptyTextColor() {
        if (mEmptyTextColor != R.color.text_empty_color) {
            if (mEmptyLayout != R.layout.layout_empty) {
                throw new RuntimeException("Cannot assign the emptyTextColor attribute. " +
                        "You already overridden the entire empty view, no need to specify " +
                        "custom color or custom text message");
            } else {
                textEmpty.setTextColor(ColorUtil.getColorWrapper(mContext, mEmptyTextColor));
            }
        }
    }

    /**
     * Set the empty view text message
     */
    private void inflateEmptyText() {
        if (mEmptyText != R.string.text_empty) {
            if (mEmptyLayout != R.layout.layout_empty) {
                throw new RuntimeException("Cannot assign the EmptyText attribute. " +
                        "You already overridden the entire emptyLayout, no need to specify " +
                        "custom color or custom text message");
            } else {
                textEmpty.setText(getResources().getString(R.string.text_empty));
            }
        }
    }

    /**
     * Set a custom empty view, this can be done via XML or programmatically
     * using setEmptyLayout(int layoutId)
     */
    private void inflateEmptyView() {
        if (mEmptyLayout != R.layout.layout_empty) {
            emptyView.removeAllViewsInLayout();
            LayoutInflater.from(mContext).inflate(mEmptyLayout, emptyView);
        }
    }

    /**
     * check if the view is declared to be aware of the connectivity.
     * if yes, start a broadcast receiver to get udpates.
     */
    private void initConnectivity() {
        if (!mConnectivityAware) {
            connectivityView.setVisibility(GONE);
            return;
        }
        if (mConnectivityAware && mReceiver == null) {
            // register broadcast receiver
            mReceiver = new NetworkReceiver(this);
            mContext.registerReceiver(mReceiver,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    /**
     * check if a broadcast receiver have been already created, if yes unregister it.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    /**
     * Add the main content of the RelativeLayout custom view to the subView contentView.
     * This is the main content that will contains the childs of FlowLayout.
     * @param child
     * @param index
     * @param params
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (contentView == null) {
            super.addView(child, index, params);
        } else {
            //Forward these calls to the content view
            contentView.addView(child, index, params);
        }
    }

    /**
     * set connectivity aware programmatically
     * @param aware
     */
    public void setConnectivityAware(boolean aware) {
        mConnectivityAware = aware;
        initConnectivity();
    }

    /**
     * Set the text to appear on the connectivity view when phone turn connected.
     * @param mConnectedText
     */
    public void setConnectedText(int mConnectedText) {
        this.mConnectedText = mConnectedText;
    }


    /**
     * Set the text color to appear on the connectivity view when phone turn connected.
     * @param mConnectedTextColor
     */
    public void setConnectedTextColor(int mConnectedTextColor) {
        this.mConnectedTextColor = mConnectedTextColor;
    }

    /**
     * Set the text to appear on the connectivity view when phone turn disconnected.
     * @param mDisconnectedText
     */
    public void setDisconnectedText(int mDisconnectedText) {
        this.mDisconnectedText = mDisconnectedText;
    }


    /**
     * Set the text color to appear on the connectivity view when phone turn disconnected.
     * @param mDisconnectedTextColor an id representing the color resource.
     */
    public void setDisconnectedTextColor(int mDisconnectedTextColor) {
        this.mDisconnectedTextColor = mDisconnectedTextColor;
    }

    /**
     * set a custom empty view programmatically
     * @param mEmptyLayout an id representing the layout resource.
     */
    public void setEmptyLayout(int mEmptyLayout) {
        this.mEmptyLayout = mEmptyLayout;
        inflateEmptyView();
    }

    /**
     * set the empty text to appear in the empty view
     * @param mEmptyText an id representing the string resource.
     */
    public void setEmptyText(int mEmptyText) {
        this.mEmptyText = mEmptyText;
        inflateEmptyText();
    }

    /**
     * set the text color to display in the empty view
     * @param mEmptyTextColor an id representing the color resource.
     */
    public void setEmptyTextColor(int mEmptyTextColor) {
        this.mEmptyTextColor = mEmptyTextColor;
        inflateEmptyTextColor();
    }

    /**
     * @return the connectivity status : true if connected, false otherwise.
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * the callback called by the broadcast receiver when a connectivity status change is detected
     * @param status
     */
    @Override
    public void onChanged(boolean status) {
        isConnected = status;
        inflateConnectivity();
    }

    /**
     * check connectivity and inflate connectivity view accordingly
     */
    private void inflateConnectivity() {
        if (isConnected) {
            if (connectivityView.getVisibility() == VISIBLE) {
                showConnected();
            }
        } else {
            showDisconnected();
        }
    }

    /**
     * show connectivity view in disconnected mode.
     */
    private void showDisconnected() {
        connectivityView.setBackgroundColor(
                ColorUtil.getColorWrapper(mContext, mDisconnectedBackground));
        connectivityText.setTextColor(
                ColorUtil.getColorWrapper(mContext, mDisconnectedTextColor));
        connectivityText.setText(getResources().getString(mDisconnectedText));

        if (connectivityView.getVisibility() == GONE) {
            connectivityView.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, -connectivityView.getHeight(), 0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            connectivityView.startAnimation(animate);
        }
    }

    /**
     * show connectivity view in connected mode.
     */
    private void showConnected() {
        connectivityView.setBackgroundColor(
                ColorUtil.getColorWrapper(mContext, mConnectedBackground));
        connectivityText.setTextColor(
                ColorUtil.getColorWrapper(mContext, mConnectedTextColor));
        connectivityText.setText(getResources().getString(mConnectedText));

        connectivityText.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isConnected) {
                    TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -connectivityView.getHeight());
                    animate.setDuration(200);
                    connectivityView.startAnimation(animate);
                    connectivityView.setVisibility(View.GONE);
                }
            }
        }, 2000);
    }

    /**
     * set the mode of the custom view.
     * This is called usually from the activity/fragment to change the status of the view
     * Example:
     *  // before loading data :
     *  flowLayout.setMode(FlowLayout.Mode.PROGRESS);
     *
     *  // if data is loaded correctly and is not empty
     *  flowLayout.setMode(FlowLayout.Mode.CONTENT);
     *
     *  // otherwise
     *  flowLayout.setMode(FlowLayout.Mode.EMPTY);
     *
     */
    public void setMode(MODE mode) {
        switch (mode) {
            case PROGRESS:
                setProgress(VISIBLE);
                setEmpty(GONE);
                setContent(GONE);
                break;
            case EMPTY:
                setProgress(GONE);
                setEmpty(VISIBLE);
                setContent(GONE);
                break;
            case CONTENT:
                setProgress(GONE);
                setEmpty(GONE);
                setContent(VISIBLE);
                break;
        }
    }

    /**
     * Set the content view visibility
     * @param visibility
     */
    private void setContent(int visibility) {
        contentView.setVisibility(visibility);
    }

    /**
     * Set the empty view visibility
     * @param visibility
     */
    private void setEmpty(int visibility) {
        emptyView.setVisibility(visibility);
    }

    /**
     * Set the progress view visibility
     * @param visibility
     */
    private void setProgress(int visibility) {
        progressView.setVisibility(visibility);
    }

}
