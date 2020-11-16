package com.android.dcoapp.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL = "https://dco-server-api.herokuapp.com";
    public static final String append = "/api";

    static APIservices apIservices = null;

    public static APIservices getInterface(){
        if (apIservices == null){

            HttpLoggingInterceptor loggingInterceptor  = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            apIservices = retrofit.create(APIservices.class);
        }
        return apIservices;
    }
}
