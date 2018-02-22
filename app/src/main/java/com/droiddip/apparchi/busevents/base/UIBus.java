package com.droiddip.apparchi.busevents.base;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;


import com.droiddip.apparchi.utils.DLogger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Dipanjan Chakraborty on 21-02-2018.
 */

public class UIBus extends EventBus {

    private static final String TAG = UIBus.class.getSimpleName();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public UIBus() {
    }

    public void postOnUIThread(final Object event, Activity activity) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    UIBus.this.post(event);
                }
            });
        } else {
            DLogger.e(TAG, "Attempted to post event on UI thread with null activity!");
        }

    }

    public void postOnNonUIThread(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.post(event);
        } else {
            this.mHandler.post(new Runnable() {
                public void run() {
                    UIBus.this.post(event);
                }
            });
        }

    }

    public void postOnUIThreadDelayed(final Object event, int delay) {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                UIBus.this.post(event);
            }
        }, (long) delay);
    }
}
