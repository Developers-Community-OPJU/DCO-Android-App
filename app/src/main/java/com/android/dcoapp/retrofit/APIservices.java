package com.android.dcoapp.retrofit;

import com.android.dcoapp.model.EventsModel;
import com.android.dcoapp.model.LoginRequest;
import com.android.dcoapp.model.LoginResponse;
import com.android.dcoapp.model.SignupRequest;
import com.android.dcoapp.model.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIservices {

    @GET(APIClient.append+"/events/find")
    Call<EventsModel> getAllEvent();

    @POST(APIClient.append+"/signUp")
    Call<SignupResponse> getSignupResponse(@Body SignupRequest signuprequest);

    @POST(APIClient.append+"/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

}
