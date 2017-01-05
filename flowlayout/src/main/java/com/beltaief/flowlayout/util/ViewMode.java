package com.beltaief.flowlayout.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wassim on 1/5/17.
 */

public interface ViewMode {

    int PROGRESS = 0;
    int CONTENT = 1;
    int EMPTY = 2;
    int ERROR = 3;

    @Documented
    @IntDef({ PROGRESS, CONTENT, EMPTY, ERROR })
    @Retention(RetentionPolicy.SOURCE) @interface State {
    }
}
