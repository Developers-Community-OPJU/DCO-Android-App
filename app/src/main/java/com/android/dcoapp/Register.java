package com.android.dcoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.dcoapp.model.SignupRequest;
import com.android.dcoapp.model.SignupResponse;
import com.android.dcoapp.retrofit.APIClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements TextWatcher {

    Toolbar toolbar;
    TextInputEditText firstnameedt, lastnameedt, branchedt, usernameedt, emailedt, passwordedt, confirmedt;
    TextInputLayout first_in_lay, branch_in_lay, username_in_lay, email_in_lay, password_in_lay, confirm_in_lay;
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
                if (Validation()) {
                    postRequestToServer();
                }
            }
        });
    }

    private boolean Validation() {
        String first_name = firstnameedt.getText().toString().trim();
        String last_name = lastnameedt.getText().toString().trim();
        String branch = branchedt.getText().toString().trim();
        String username = usernameedt.getText().toString().trim();
        String email = emailedt.getText().toString().trim();
        String password = passwordedt.getText().toString().trim();
        String confirm_password = confirmedt.getText().toString().trim();

        if (TextUtils.isEmpty(first_name)) {
            first_in_lay.setErrorEnabled(true);
            first_in_lay.setError("first name is required!!");
            firstnameedt.setFocusableInTouchMode(true);
            firstnameedt.requestFocus();
            firstnameedt.addTextChangedListener(this);
            return false;
        }

        if (TextUtils.isEmpty(branch)) {
            branch_in_lay.setErrorEnabled(true);
            branch_in_lay.setError("branch is required!!");
            branchedt.setFocusableInTouchMode(true);
            branchedt.requestFocus();
            branchedt.addTextChangedListener(this);
            return false;
        }

        if (TextUtils.isEmpty(username)) {
            username_in_lay.setErrorEnabled(true);
            username_in_lay.setError("username is required!!");
            usernameedt.setFocusableInTouchMode(true);
            usernameedt.requestFocus();
            usernameedt.addTextChangedListener(this);
            return false;
        }else if (username.length() < 6){
            username_in_lay.setErrorEnabled(true);
            username_in_lay.setError("length must be atleast 6 characters");
            usernameedt.setFocusableInTouchMode(true);
            usernameedt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            email_in_lay.setErrorEnabled(true);
            email_in_lay.setError("email is required!!");
            emailedt.setFocusableInTouchMode(true);
            emailedt.requestFocus();
            emailedt.addTextChangedListener(this);
            return false;
        }else if (!isEmailValid(email)){
            email_in_lay.setErrorEnabled(true);
            email_in_lay.setError("enter valid email address");
            emailedt.setFocusableInTouchMode(true);
            emailedt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            password_in_lay.setErrorEnabled(true);
            password_in_lay.setError("password is required!!");
            passwordedt.setFocusableInTouchMode(true);
            passwordedt.requestFocus();
            passwordedt.addTextChangedListener(this);
            return false;
        }else if (password.length() < 6){
            password_in_lay.setErrorEnabled(true);
            password_in_lay.setError("length must be atleast 6 characters");
            passwordedt.setFocusableInTouchMode(true);
            passwordedt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confirm_password)){
            confirm_in_lay.setErrorEnabled(true);
            confirm_in_lay.setError("please re-enter the password");
            confirmedt.setFocusableInTouchMode(true);
            confirmedt.requestFocus();
            confirmedt.addTextChangedListener(this);
            return false;
        }else if (!password.matches(confirm_password)){
            confirm_in_lay.setErrorEnabled(true);
            confirm_in_lay.setError("password does't match");
            confirmedt.setFocusableInTouchMode(true);
            confirmedt.requestFocus();
            return false;
        }
        return true;
    }

    //check email pattern valid or not
    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                if (response.isSuccessful()) {
                    SignupResponse signupResponse = response.body();
                    if (signupResponse.getRegistered()) {
                        ToastMassage(signupResponse.getMsg());
                        Intent intent = new Intent(Register.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        username_in_lay.setErrorEnabled(true);
                        username_in_lay.setError("username already exists");
                        usernameedt.setFocusableInTouchMode(true);
                        usernameedt.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                ToastMassage("failed" + t);
            }
        });
    }

    private void initilizationView() {

        //initialized toolbar
        toolbar = findViewById(R.id.toolbar);

        //initialized all textInputEditText
        firstnameedt = findViewById(R.id.firstname_edittext);
        lastnameedt = findViewById(R.id.lastname_edittext);
        branchedt = findViewById(R.id.branch_edittext);
        usernameedt = findViewById(R.id.username_edittext);
        emailedt = findViewById(R.id.email_edittext);
        passwordedt = findViewById(R.id.password_edittext);
        confirmedt = findViewById(R.id.confirm_edittext);

        //initialized all textInputLayout
        first_in_lay = findViewById(R.id.firstname_textInputLayout);
        branch_in_lay = findViewById(R.id.branch_textInputLayout);
        username_in_lay = findViewById(R.id.username_textInputLayout);
        email_in_lay = findViewById(R.id.email_textInputLayout);
        password_in_lay = findViewById(R.id.password_textInputLayout);
        confirm_in_lay = findViewById(R.id.confirm_textInputLayout);

        //initialized Button
        registerbtn = findViewById(R.id.register_button);
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (first_in_lay.isErrorEnabled())
            first_in_lay.setErrorEnabled(false);

        if (branch_in_lay.isErrorEnabled())
            branch_in_lay.setErrorEnabled(false);

        if (username_in_lay.isErrorEnabled())
            username_in_lay.setErrorEnabled(false);

        if (email_in_lay.isErrorEnabled())
            email_in_lay.setErrorEnabled(false);

        if (password_in_lay.isErrorEnabled())
            password_in_lay.setErrorEnabled(false);

        if (confirm_in_lay.isErrorEnabled()){
            confirm_in_lay.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}