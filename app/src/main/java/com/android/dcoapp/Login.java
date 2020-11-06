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
import com.android.dcoapp.retrofit.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializationView();

    }

    private void initializationView() {
        toRegister = findViewById(R.id.open_register_activity);
    }

    public void login_to_register(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }
}