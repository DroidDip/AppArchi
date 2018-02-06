package com.droiddip.apparchi.api.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.droiddip.apparchi.R;
import com.droiddip.apparchi.application.AppArchi;
import com.droiddip.apparchi.utils.DGsonUtils;
import com.droiddip.apparchi.utils.DLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.droiddip.apparchi.api.volley.WSConstants.RESPONSE_200;


public class RequestClient {

    /**
     * The default socket timeout in milliseconds
     */
    public static final int DEFAULT_TIMEOUT_MS = 2500;

    /**
     * The default number of retries
     */
    public static final int DEFAULT_MAX_RETRIES = 1;

    /**
     * The default backoff multiplier
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    /**
     * Method to get Login API
     *
     * @param context
     * @param bodyParams
     * @param mBaseResponseListener
     */
    public static void requestLoginApi(Context context, Map bodyParams, BaseResponseListener mBaseResponseListener) {
        post(context.getResources().getString(R.string.volley_request_tag), RequestUrlBuilder.getRequestUrl(context, ApiType.LOGIN),
                bodyParams, mBaseResponseListener);
    }

    /**
     * Download Bitmap from URL
     *
     * @param imageUrl
     * @param bitmapDownloadListener
     */
    public static void requestBitmapToDownload(String imageUrl, BitmapDownloadListener bitmapDownloadListener) {
        downloadBitmapFromUrl(imageUrl, bitmapDownloadListener);
    }

    /**
     * Method POST
     *
     * @param volleyRequestTag
     * @param requestUrl
     * @param bodyParams
     * @param mBaseResponseListener
     */
    private static void post(String volleyRequestTag, String requestUrl, final Map bodyParams, final BaseResponseListener mBaseResponseListener) {
        DLogger.e("post_url:- " + requestUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DLogger.e("post_response:- " + response);
                BaseResponse baseResponse = (BaseResponse) DGsonUtils.StringJSONTOObject(response, BaseResponse.class);
                if (baseResponse != null) {
                    if (baseResponse.getResponse_code().equalsIgnoreCase(RESPONSE_200)) {
                        if (mBaseResponseListener != null)
                            mBaseResponseListener.requestCompleted(response);
                    } else {
                        if (mBaseResponseListener != null)
                            mBaseResponseListener.requestEndedWithError(baseResponse.getResponse_msg());
                    }
                } else {
                    if (mBaseResponseListener != null)
                        mBaseResponseListener.requestEndedWithError(baseResponse.getResponse_msg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mBaseResponseListener != null)
                    mBaseResponseListener.requestEndedWithError(getVolleyErrorMsg(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                DLogger.e("post_body:- " + bodyParams.toString());
                return bodyParams;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return RequestParamBuilder.getHeaderParams();
            }
        };

        // Wait 20 seconds and don't retry more than once
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                DEFAULT_MAX_RETRIES,
                DEFAULT_BACKOFF_MULT));


        stringRequest.setTag(volleyRequestTag);
        AppArchi.getInstance().addToRequestQueue(stringRequest);

    }

    /**
     * Method GET
     *
     * @param volleyRequestTag
     * @param requestUrl
     * @param bodyParams
     * @param mBaseResponseListener
     */
    private static void get(String volleyRequestTag, String requestUrl, final Map bodyParams, final BaseResponseListener mBaseResponseListener) {
        DLogger.e("post_url:- " + requestUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DLogger.e("get_response:- " + response);
                BaseResponse baseResponse = (BaseResponse) DGsonUtils.StringJSONTOObject(response, BaseResponse.class);
                if (baseResponse.getResponse_code().equalsIgnoreCase(RESPONSE_200)) {
                    if (mBaseResponseListener != null)
                        mBaseResponseListener.requestCompleted(response);
                } else {
                    if (mBaseResponseListener != null)
                        mBaseResponseListener.requestEndedWithError(baseResponse.getResponse_msg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mBaseResponseListener != null)
                    mBaseResponseListener.requestEndedWithError(getVolleyErrorMsg(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                DLogger.e("post_body:- " + bodyParams.toString());
                return bodyParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return RequestParamBuilder.getHeaderParams();
            }
        };


        // Wait 20 seconds and don't retry more than once
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                DEFAULT_MAX_RETRIES,
                DEFAULT_BACKOFF_MULT));


        stringRequest.setTag(volleyRequestTag);
        AppArchi.getInstance().addToRequestQueue(stringRequest);

    }

    /**
     * Method POST with Cache
     *
     * @param volleyRequestTag
     * @param requestUrl
     * @param bodyParams
     * @param mBaseResponseListener
     */
    private static void postCache(String volleyRequestTag, String requestUrl, final Map bodyParams, final BaseResponseListener mBaseResponseListener) {
        DLogger.e("post_url:- " + requestUrl);

        CacheRequest cacheRequest = new CacheRequest(Request.Method.POST, requestUrl, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                DLogger.e("get_response:- " + response);
                String jsonString;
                try {
                    jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    String apiResponse = new JSONObject(jsonString).toString();
                    BaseResponse baseResponse = (BaseResponse) DGsonUtils.StringJSONTOObject(apiResponse, BaseResponse.class);
                    if (baseResponse.getResponse_code().equalsIgnoreCase(RESPONSE_200)) {
                        if (mBaseResponseListener != null)
                            mBaseResponseListener.requestCompleted(apiResponse);
                    } else {
                        if (mBaseResponseListener != null)
                            mBaseResponseListener.requestEndedWithError(baseResponse.getResponse_msg());
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mBaseResponseListener != null)
                    mBaseResponseListener.requestEndedWithError(getVolleyErrorMsg(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                DLogger.e("post_body:- " + bodyParams.toString());
                return bodyParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return RequestParamBuilder.getHeaderParams();
            }
        };

        // Wait 20 seconds and don't retry more than once
        cacheRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                DEFAULT_MAX_RETRIES,
                DEFAULT_BACKOFF_MULT));


        cacheRequest.setTag(volleyRequestTag);
        AppArchi.getInstance().addCacheToRequestQueue(cacheRequest);


    }


    /**
     * Download Image Bitmap from URL
     *
     * @param imageUrl
     * @param bitmapDownloadListener
     */
    private static void downloadBitmapFromUrl(String imageUrl, final BitmapDownloadListener bitmapDownloadListener) {
        DLogger.e("post_url:- " + imageUrl);

        ImageRequest imageRequest = new ImageRequest(
                imageUrl, //Image Url
                new Response.Listener<Bitmap>() { //Image Bitmap Listener
                    @Override
                    public void onResponse(Bitmap response) {
                        // Do something with response
                        if (bitmapDownloadListener != null)
                            bitmapDownloadListener.onBitmapDownloaded(response);
                    }
                },
                0, // Image width
                0, // Image height
                ImageView.ScaleType.FIT_XY, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        error.printStackTrace();
                        if (bitmapDownloadListener != null)
                            bitmapDownloadListener.onBitmapDownloadFailed();
                    }
                });
        AppArchi.getInstance().addImageRequestToQueue(imageRequest);
    }


    /**
     * Method to get volley error message
     *
     * @param error
     * @return
     */
    private static String getVolleyErrorMsg(VolleyError error) {
        String errorMsg = null;
        if (error != null) {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                errorMsg = AppArchi.getInstance().getString(R.string.error_network_timeout);
            } else if (error instanceof AuthFailureError) {
                errorMsg = AppArchi.getInstance().getString(R.string.error_auth_failure);
            } else if (error instanceof ServerError) {
                errorMsg = AppArchi.getInstance().getString(R.string.error_server);
            } else if (error instanceof NetworkError) {
                errorMsg = AppArchi.getInstance().getString(R.string.error_network);
            } else if (error instanceof ParseError) {
                errorMsg = AppArchi.getInstance().getString(R.string.error_parse);
            } else
                errorMsg = AppArchi.getInstance().getString(R.string.error_something_went_wrong);
        } else
            errorMsg = AppArchi.getInstance().getString(R.string.error_something_went_wrong);
        return errorMsg;
    }
}
