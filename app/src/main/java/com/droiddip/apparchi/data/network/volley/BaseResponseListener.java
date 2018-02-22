package com.droiddip.apparchi.data.network.volley;


public interface BaseResponseListener {

    void requestCompleted(String response);

    void requestEndedWithError(String errorMsg);
}
