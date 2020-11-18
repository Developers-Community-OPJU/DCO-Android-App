package com.android.dcoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements TextWatcher {

    TextView toRegister;
    TextInputEditText usernameedx, passwordedx;
    TextInputLayout username_in_lay, pass_in_lay;
    CardView loginbtn;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializationView();

        sessionManager = new SessionManager(this);
        sessionManager.CreatePreferences();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    loginRequestToServer();
                }
            }
        });
    }

    private boolean validation() {
        String username = usernameedx.getText().toString().trim();
        String password = passwordedx.getText().toString().trim();

        //implement textWatcher
        usernameedx.addTextChangedListener(this);
        passwordedx.addTextChangedListener(this);

        if (TextUtils.isEmpty(username)){
            username_in_lay.setErrorEnabled(true);
            username_in_lay.setError("please enter username");
            usernameedx.setFocusableInTouchMode(true);
            usernameedx.requestFocus();
            return false;
        }else if (username.length() < 6){
            username_in_lay.setErrorEnabled(true);
            username_in_lay.setError("length must be atleast 6 characters");
            usernameedx.setFocusableInTouchMode(true);
            usernameedx.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)){
            pass_in_lay.setErrorEnabled(true);
            pass_in_lay.setError("please enter password");
            passwordedx.setFocusableInTouchMode(true);
            passwordedx.requestFocus();
        }else if (password.length() < 6){
            pass_in_lay.setErrorEnabled(true);
            pass_in_lay.setError("length must be atleast 6 characters");
            passwordedx.setFocusableInTouchMode(true);
            passwordedx.requestFocus();
            return false;
        }
        return true;
    }

    private void loginRequestToServer() {
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
                        if (loginResponse.getMsg().equals("Invalid Credentials, Try again.")){
                            username_in_lay.setErrorEnabled(true);
                            username_in_lay.setError("invalid username");
                            usernameedx.setFocusableInTouchMode(true);
                            usernameedx.requestFocus();
                        }else if (loginResponse.getMsg().equals("Invalid Password")){
                            pass_in_lay.setErrorEnabled(true);
                            pass_in_lay.setError("enter correct password");
                            passwordedx.setFocusableInTouchMode(true);
                            passwordedx.requestFocus();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void initializationView() {

        //initialize button
        loginbtn = findViewById(R.id.login_btn);

        //initialized register text button
        toRegister = findViewById(R.id.open_register_activity);

        //initialize textInputEditText
        usernameedx = findViewById(R.id.username_edittext);
        passwordedx = findViewById(R.id.password_edittext);

        //initialize textInputLayout
        username_in_lay = findViewById(R.id.login_username_textInputLayout);
        pass_in_lay = findViewById(R.id.login_password_textInputLayout);
    }

    public void login_to_register(View view) {
        startActivity(new Intent(Login.this, Register.class));
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (username_in_lay.isErrorEnabled()){
            username_in_lay.setErrorEnabled(false);
        }

        if (pass_in_lay.isErrorEnabled()){
            pass_in_lay.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}