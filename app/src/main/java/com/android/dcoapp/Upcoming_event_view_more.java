package com.android.dcoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.dcoapp.adapter.Upcoming_event_verticalview_adapter;
import com.android.dcoapp.model.Event;
import com.android.dcoapp.model.EventsModel;
import com.android.dcoapp.retrofit.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upcoming_event_view_more extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_event_view_more);

        initializedView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upcoming Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        dataFetching();

    }

    private void dataFetching() {
        Call<EventsModel> eventsModelCall = APIClient.getInterface().getAllEvent();
        eventsModelCall.enqueue(new Callback<EventsModel>() {
            @Override
            public void onResponse(Call<EventsModel> call, Response<EventsModel> response) {
                if (response.isSuccessful()){
                    EventsModel eventsModel = response.body();
                    List<Event> eventList = new ArrayList<>();

                    for (Event event : eventsModel.getEvents()){
                        if(!event.getEnded()){
                            eventList.add(event);
                        }
                    }

                    Upcoming_event_verticalview_adapter verticalviewAdapter = new Upcoming_event_verticalview_adapter(getApplicationContext(), eventList);
                    recyclerView.setAdapter(verticalviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<EventsModel> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void initializedView() {
        toolbar = findViewById(R.id.upcoming_event_toolbar);
        recyclerView = findViewById(R.id.upcoming_event_recyclerView);
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}