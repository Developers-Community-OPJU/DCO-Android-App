package com.android.dcoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dcoapp.model.EventsModel;
import com.android.dcoapp.model.LoginRequest;
import com.android.dcoapp.model.LoginResponse;
import com.android.dcoapp.retrofit.APIClient;
import com.android.dcoapp.retrofit.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView toRegister;
    TextInputEditText usernameedx, passwordedx;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializationView();

        sessionManager = new SessionManager(this);
        sessionManager.CreatePreferences();

    }

    private void initializationView() {
        toRegister = findViewById(R.id.open_register_activity);
        usernameedx = findViewById(R.id.username_edittext);
        passwordedx = findViewById(R.id.password_edittext);
    }

    public void login_to_register(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

    public void loginButton(View view) {
        String username = usernameedx.getText().toString().trim();
        String password = passwordedx.getText().toString().trim();

        final LoginRequest loginRequest = new LoginRequest(username, password);

        final Call<LoginResponse> loginResponseCall = APIClient.getInterface().getLoginResponse(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null){
                    if (loginResponse.getAllowed()) {
                        ToastMassage(loginResponse.getMsg());
                        sessionManager.saveAuthToken(loginResponse.getToken());

                        Intent intent = new Intent(Login.this, Home.class);
                        finish();
                        startActivity(intent);
                    }else {
                        ToastMassage(loginResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sessionManager.getAuthToken() != null){
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }
    }
}