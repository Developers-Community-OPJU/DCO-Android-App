package com.android.dcoapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL = "https://dco-event-api.herokuapp.com";
    public static final String append = "/api/events";

    static APIservices apIservices = null;

    public static APIservices getInterface(){
        if (apIservices == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apIservices = retrofit.create(APIservices.class);
        }
        return apIservices;
    }
}
