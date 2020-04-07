package com.assettagging.test.model.WebServer;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;
//    Testing URL
    private static String BaseUrl = "http://219.90.65.215:9003/service1.svc/";
//    123456   123456

//    Live URL
//    private static String BaseUrl = "http://192.168.200.182:8082/service1.svc/";

   // private static String BaseUrl = "http://192.168.1.246:804/service1.svc/";
    private static OkHttpClient client;

    public static Retrofit getClient() {
        Log.d("BaseUrl", BaseUrl);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (client != null) {
            client = null;
        }
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        client = okHttpClient.addInterceptor(interceptor).readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES).build();
        if (retrofit != null) {
            retrofit = null;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
}