package com.example.huynhphihau.cleanservice.data.rest;

import android.util.Log;

import com.example.huynhphihau.cleanservice.BuildConfig;
import com.example.huynhphihau.cleanservice.base.BaseApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by phihau on 5/8/2017.
 */

public class GetClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitGoogle = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient(headerHandlerInterceptor(), getLogInterceptor()))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientGoogle(String baseUrl) {
        if (retrofitGoogle == null) {
            retrofitGoogle = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitGoogle;
    }

    public static OkHttpClient getClient(Interceptor... interceptors) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        for (final Interceptor interceptor : interceptors) {
            if (interceptor != null) {
                httpClient.addInterceptor(interceptor);
            }
        }

        return httpClient
                .connectTimeout(ServerConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(ServerConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    public static Interceptor getLogInterceptor() {
        if (BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return null;
    }

    public static Interceptor headerHandlerInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final String token = BaseApplication.getInstance().getToken();
                Log.d("AAA-API: oldToken", token);
                Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
                Response response = chain.proceed(request);
                Log.d("AAA-API: URL :", chain.request().url().toString());

                Headers headers = response.headers();
                if (response.headers().get("Authorization") != null) {
                    String newToken = headers.get("Authorization");
                    Log.d("AAA-API: newToken", newToken);
                    BaseApplication.getInstance().setTokenFromHeader(headers);
                }
                return response;
            }
        };
    }
}