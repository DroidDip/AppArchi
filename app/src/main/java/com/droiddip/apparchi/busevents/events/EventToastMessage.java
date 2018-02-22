package com.droiddip.apparchi.busevents.events;

public class EventToastMessage {

    private String message;
    private boolean isLongToast;

    public EventToastMessage(String message, boolean isLongToast) {
        this.message = message;
        this.isLongToast = isLongToast;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLongToast() {
        return isLongToast;
    }

    public void setLongToast(boolean longToast) {
        isLongToast = longToast;
    }
}
