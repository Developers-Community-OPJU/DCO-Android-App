package com.android.dcoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.dcoapp.model.SignupRequest;
import com.android.dcoapp.model.SignupResponse;
import com.android.dcoapp.retrofit.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText firstnameedt, lastnameedt, branchedt, usernameedt, emailedt, passwordedt;
    CardView registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initilizationView();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequestToServer();
            }
        });
    }

    private void postRequestToServer() {
        String first_name = firstnameedt.getText().toString().trim();
        String last_name = lastnameedt.getText().toString().trim();
        String branch = branchedt.getText().toString().trim();
        String username = usernameedt.getText().toString().trim();
        String email = emailedt.getText().toString().trim();
        String password = passwordedt.getText().toString().trim();

        SignupRequest signupRequest = new SignupRequest(first_name, last_name, branch, username, password, email);

        Call<SignupResponse> signupResponseCall = APIClient.getInterface().getSignupResponse(signupRequest);
        signupResponseCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()){
                    SignupResponse signupResponse = response.body();
                    Log.d("server", String.valueOf(signupResponse));
                    ToastMassage(signupResponse.getMsg());
                    Log.d("server", signupResponse.getMsg());
                    Intent intent = new Intent(Register.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                ToastMassage("failed"+t);
            }
        });
    }

    private void initilizationView() {
        toolbar = findViewById(R.id.toolbar);

        firstnameedt = findViewById(R.id.firstname_edittext);
        lastnameedt = findViewById(R.id.lastname_edittext);
        branchedt = findViewById(R.id.branch_edittext);
        usernameedt = findViewById(R.id.username_edittext);
        emailedt = findViewById(R.id.email_edittext);
        passwordedt = findViewById(R.id.password_edittext);

        registerbtn = findViewById(R.id.register_button);
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}