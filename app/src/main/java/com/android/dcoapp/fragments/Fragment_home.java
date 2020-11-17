package com.android.dcoapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.dcoapp.Home;
import com.android.dcoapp.Login;
import com.android.dcoapp.R;
import com.android.dcoapp.adapter.Past_event_adapter;
import com.android.dcoapp.adapter.Upcoming_event_adapter;
import com.android.dcoapp.model.Event;
import com.android.dcoapp.model.EventsModel;
import com.android.dcoapp.retrofit.APIClient;
import com.android.dcoapp.retrofit.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_home extends Fragment {

    RecyclerView upcoming_recyclerView, past_event_recyclerView;
    Button logoutbtn;
    SessionManager manager;

    public Fragment_home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializationView(view);

        upcoming_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        upcoming_recyclerView.setHasFixedSize(true);

        past_event_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        past_event_recyclerView.setHasFixedSize(true);

        extractData(view);

        //logging out test
        manager = new SessionManager(getActivity());
        manager.CreatePreferences();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.removeToken();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void extractData(final View view) {
        Call<EventsModel> eventsModelCall = APIClient.getInterface().getAllEvent();
        eventsModelCall.enqueue(new Callback<EventsModel>() {
            @Override
            public void onResponse(Call<EventsModel> call, Response<EventsModel> response) {
                EventsModel eventsModel = response.body();
                List<Event> eventList = eventsModel.getEvents();
                List<Event> upcoming = new ArrayList<>();
                List<Event> past = new ArrayList<>();

                for (Event event : eventList){
                    if (event.getEnded()){
                        past.add(event);
                    }else upcoming.add(event);
                }

                Upcoming_event_adapter upcoming_event_adapter = new Upcoming_event_adapter(view.getContext(), upcoming);
                upcoming_event_adapter.notifyDataSetChanged();
                upcoming_recyclerView.setAdapter(upcoming_event_adapter);

                Past_event_adapter past_event_adapter = new Past_event_adapter(view.getContext(), past);
                past_event_adapter.notifyDataSetChanged();
                past_event_recyclerView.setAdapter(past_event_adapter);
            }

            @Override
            public void onFailure(Call<EventsModel> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void initializationView(View view) {
        upcoming_recyclerView = view.findViewById(R.id.upcoming_recyclerView);
        past_event_recyclerView = view.findViewById(R.id.pastevents_recyclerView);
        logoutbtn = view.findViewById(R.id.logout_button);
    }
}