package com.android.dcoapp.retrofit;

import com.android.dcoapp.model.EventsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIservices {

    @GET(APIClient.append+"/find")
    Call<EventsModel> getAllEvent();

}
