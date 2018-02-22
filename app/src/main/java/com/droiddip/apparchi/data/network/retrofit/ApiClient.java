package com.droiddip.apparchi.data.network.retrofit;

import android.content.Context;

import com.momenta.BuildConfig;
import com.momenta.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Dipanjan Chakraborty on 17-01-2018.
 * <p>
 * ApiClient used for instantiate Retrofit API service
 */

public class ApiClient {

    public static Retrofit mRetrofit;

    public static Retrofit retrofit(Context context, String apiServerUrl) {
        if (mRetrofit == null && context != null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            //Timeout
            httpClient.readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS);
            httpClient.connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS);
            httpClient.writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS);

            //Logging
            if (BuildConfig.DEBUG) {
                // Log
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                //Debug Log
                httpClient.addInterceptor(loggingInterceptor);
            }

            //Cache
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            httpClient.cache(cache);

            //Header Interceptor
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header(ApiConfig.API_KEY, context.getString(R.string.api_key))
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient okHttpClient = httpClient.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(apiServerUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
