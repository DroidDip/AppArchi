package com.droiddip.apparchi.data.network.retrofit;

/**
 * Created by Dipanjan Chakraborty on 17-01-2018.
 * <p>
 * Configuration class to store API tags & configuration
 */

public interface ApiConfig {

    //Timeout
    long READ_TIMEOUT = 1200;
    long CONNECT_TIMEOUT = 1350;
    long WRITE_TIMEOUT = 1300;

    //Request Param Keys
    String API_KEY = "apikey";

    //Api Methods
    //Note: These are demo method, replace it with your own requirement
    String API_LOGIN = "v1/Login";
    String API_REGISTRATION = "v1/Register";
    String API_SOCIAL_LOGIN = "v1/Social_Login";
}
