package com.droiddip.apparchi.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.droiddip.apparchi.R;
import com.droiddip.apparchi.api.volley.CacheRequest;


/**
 * Created by Dipanjan Chakraborty on 05-02-2018.
 */

public class AppArchi extends Application {

    public static final String TAG = AppArchi.class.getSimpleName();
    private static AppArchi mInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppArchi getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(StringRequest stringRequest) {
        getRequestQueue().add(stringRequest);
    }

    public void addCacheToRequestQueue(CacheRequest cacheRequest) {
        getRequestQueue().add(cacheRequest);
    }

    public void addImageRequestToQueue(ImageRequest imageRequest) {
        getRequestQueue().add(imageRequest);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelAllRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(getApplicationContext().getResources().getString(R.string.volley_request_tag));
        }

    }
}
