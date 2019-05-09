package com.music.arts.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * DSafeHandler
 *
 * @author Dipanjan Chakraborty
 */

public class DSafeHandler<T extends DSafeHandler.HandlerContainer> extends Handler {
    protected WeakReference<T> mRef;

    public DSafeHandler(WeakReference<T> ref) {
        mRef = ref;
    }

    public DSafeHandler(T obj) {
        mRef = new WeakReference<>(obj);
    }

    public T getContainer() {
        return mRef.get();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        HandlerContainer container = getContainer();
        if (container != null) {
            container.handleMessage(msg);
        }
    }

    public interface HandlerContainer {
        void handleMessage(Message message);
    }
}
