package com.droiddip.apparchi.data.network.volley;

import android.content.Context;


import com.droiddip.apparchi.R;

import java.io.File;


public class RequestUrlBuilder {

    private RequestUrlBuilder() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static String getRequestUrl(Context mContext, ApiType apiType) {
        String requestUrl = null;

        switch (apiType) {
            case LOGIN:
                requestUrl = mContext.getString(R.string.base_url) + File.separator + mContext.getString(R.string.registration) + File.separator + mContext.getString(R.string.method_login);
                break;

            case REGISTRATION:
                requestUrl = mContext.getString(R.string.base_url) + File.separator + mContext.getString(R.string.registration) + File.separator + mContext.getString(R.string.method_registration);
                break;
        }

        return requestUrl;
    }
}
