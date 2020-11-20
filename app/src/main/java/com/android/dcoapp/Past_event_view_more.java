package com.android.dcoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.dcoapp.adapter.Past_event_verticalview_adapter;
import com.android.dcoapp.model.Event;
import com.android.dcoapp.model.EventsModel;
import com.android.dcoapp.retrofit.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Past_event_view_more extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_event_view_more);

        initializeView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Past Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        fatchingData();

    }

    private void fatchingData() {
        Call<EventsModel> eventsModelCall = APIClient.getInterface().getAllEvent();
        eventsModelCall.enqueue(new Callback<EventsModel>() {
            @Override
            public void onResponse(Call<EventsModel> call, Response<EventsModel> response) {
                if (response.isSuccessful()){
                    EventsModel eventsModel = response.body();
                    List<Event> eventList = new ArrayList<>();

                    for (Event event : eventsModel.getEvents()){
                        if(event.getEnded()){
                            eventList.add(event);
                        }
                    }

                    Past_event_verticalview_adapter verticalviewAdapter = new Past_event_verticalview_adapter(getApplicationContext(), eventList);
                    recyclerView.setAdapter(verticalviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<EventsModel> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void initializeView() {
        toolbar = findViewById(R.id.past_event_toolbar);
        recyclerView = findViewById(R.id.past_event_recyclerView);
    }

    private void ToastMassage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}