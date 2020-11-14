package com.android.dcoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.dcoapp.fragments.Fragment_home;
import com.android.dcoapp.fragments.Fragment_notification;
import com.android.dcoapp.fragments.Fragment_settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        initializationView();

        //set custom toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setCustomView(R.layout.home_custom_toolbar);
        getSupportActionBar().setTitle("");

        //set navigation item click event
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //set default home fragment view
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment_home()).commit();
    }

    private void initializationView() {
        toolbar = findViewById(R.id.home_toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.profile);
        View view = MenuItemCompat.getActionView(menuItem);

        CircleImageView circleImageView = view.findViewById(R.id.circuler_profile_view);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastMassage("view profile");
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_id:
                ToastMassage("clicked search");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.bottom_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment_home()).commit();
                break;
            case R.id.bottom_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment_notification()).commit();
                break;
            case R.id.bottom_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment_settings()).commit();
                break;
        }
        return true;
    }
}