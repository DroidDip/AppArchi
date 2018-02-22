package com.droiddip.apparchi.busevents.events;

import android.support.annotation.StringRes;
import android.view.View;

public class EventSnackBarMessage {

    private View rootView;
    private int messageId;
    private boolean isError;

    public EventSnackBarMessage(View rootView, @StringRes int messageId) {
        this.rootView = rootView;
        this.messageId = messageId;
    }

    public EventSnackBarMessage(View rootView, @StringRes int messageId, boolean isError) {
        this.rootView = rootView;
        this.messageId = messageId;
        this.isError = isError;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public int getMessage() {
        return messageId;
    }

    public void setMessage(@StringRes int messageId) {
        this.messageId = messageId;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
