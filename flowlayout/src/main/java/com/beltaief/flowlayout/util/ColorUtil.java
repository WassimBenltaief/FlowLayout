package com.beltaief.flowlayout.util;

import android.content.Context;
import android.os.Build;

/**
 * Created by wassim on 9/23/16.
 */

public class ColorUtil {

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }
}
