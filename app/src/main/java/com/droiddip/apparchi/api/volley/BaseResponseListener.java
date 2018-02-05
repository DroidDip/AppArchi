package com.droiddip.apparchi.api.volley;


public interface BaseResponseListener {

    void requestCompleted(String response);

    void requestEndedWithError(String errorMsg);
}
