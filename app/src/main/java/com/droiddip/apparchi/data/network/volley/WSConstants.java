package com.droiddip.apparchi.data.network.volley;


public class WSConstants {

    private WSConstants() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static String API_KEY = "Value of the API Key"; //Replace it with your own project key

    public static String RESPONSE_200 = "200";// OK
    public static String RESPONSE_201 = "201";//Failed
    public static String RESPONSE_400 = "400";// error.400=Bad Request
    public static String RESPONSE_401 = "401";// error.401=Unauthorized
    public static String RESPONSE_402 = "402";// error.402=Payment Required
    public static String RESPONSE_403 = "403";// error.403=Forbidden
    public static String RESPONSE_404 = "404";// error.404=Not Found
    public static String RESPONSE_405 = "405";// error.405=Method Not Allowed
    public static String RESPONSE_408 = "408";// error.408=Request Timeout
    public static String RESPONSE_500 = "500";// error.500=Internal Server
    // Error
    public static String RESPONSE_502 = "502";// error.502=Bad Gateway
    public static String RESPONSE_503 = "503";// error.503=Service
    // Unavailable
    public static String RESPONSE_504 = "504";// error.504=Gateway Timeout

    public static String RESPONSE_SUCCESS = "Success";

}
