package com.droiddip.apparchi.api.volley;

import java.util.HashMap;
import java.util.Map;

import static com.droiddip.apparchi.api.volley.WSConstants.API_KEY;


/**
 * Class used to build API body params
 */

public class RequestParamBuilder {

    //API Input Params Key
    private static String EMAIL_ID = "email_id";
    private static String USER_NAME = "user_name";
    private static String PASSWORD = "password";
    private static String SOCIAL_ID = "social_id";
    private static String DEVICE_ID = "device_id";
    private static String DEVICE_TOKEN = "device_token";
    private static String SOCIAL_TYPE = "social_type";
    public static String APIKEY = "apikey";
    private static String USER_ID = "user_id";
    private static String LIMIT = "limit";
    private static String OFFSET = "offset";
    private static String CONTENT_TYPE = "Content-Type";
    private static String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded;charset=UTF-8";


    private RequestParamBuilder() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static Map getHeaderParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(APIKEY, API_KEY);
        return params;
    }

    public static Map getMultipartHeaderParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(APIKEY, API_KEY);
        params.put(CONTENT_TYPE, "multipart/form-data");
        return params;
    }

    public static Map getLoginParams(String email_id, String password, String social_id, String device_id, String device_token, String social_type) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(EMAIL_ID, email_id);
        params.put(PASSWORD, password);
        params.put(SOCIAL_ID, social_id);
        params.put(DEVICE_ID, device_id);
        params.put(DEVICE_TOKEN, device_token);
        params.put(SOCIAL_TYPE, social_type);
        return params;
    }
}
