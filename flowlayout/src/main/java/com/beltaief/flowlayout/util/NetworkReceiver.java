package com.beltaief.flowlayout.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wassim on 9/13/16.
 */
public class NetworkReceiver extends BroadcastReceiver {

    private ConnectivityListener mListener;

    public NetworkReceiver(ConnectivityListener listener) {
        mListener = listener;
    }

    private static final String TAG = NetworkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean status = NetworkUtil.getConnectivityStatus(context);
        Log.d(TAG, status ? "connected" : "disconnected");

        mListener.onChanged(status);
    }
}
