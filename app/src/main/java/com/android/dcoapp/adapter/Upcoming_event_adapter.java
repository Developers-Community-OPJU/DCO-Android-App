package com.android.dcoapp.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dcoapp.R;
import com.android.dcoapp.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Upcoming_event_adapter extends RecyclerView.Adapter<Upcoming_event_adapter.Upcoming_event_viewholder> {

    private Context context;
    private List<Event> eventList;
    private int MAX_CARD_LIMIT = 4;

    public Upcoming_event_adapter(Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public Upcoming_event_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_cards, parent, false);

        if (eventList.size() > 1){
            view.getLayoutParams().width = (int) (getScreenwidth() / 1.3);
        }
        return new Upcoming_event_viewholder(view);
    }

    private int getScreenwidth() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public void onBindViewHolder(@NonNull Upcoming_event_viewholder holder, int position) {
        Event event = eventList.get(position);

        holder.title.setText(event.getTitle());
        holder.organizer.setText(event.getOrganiser());
        holder.venue.setText(event.getVenue());
        holder.datetxt.setText(dateTimeFormat(event.getScheduledDate(), 1));
        holder.timetxt.setText(dateTimeFormat(event.getScheduledDate(), 2));
    }

    private String dateTimeFormat(String scheduledDate, int i) {
        Date date = null;
        String dateFormat;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(scheduledDate);
            if (i == 1){
                dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date);
                return dateFormat;
            }
            if (i == 2){
                dateFormat = new SimpleDateFormat("HH:mm a", Locale.US).format(date);
                return dateFormat;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return scheduledDate;
    }

    @Override
    public int getItemCount() {
        if (eventList.size() > MAX_CARD_LIMIT)
            return MAX_CARD_LIMIT;
        else return eventList.size();
    }

    public class Upcoming_event_viewholder extends RecyclerView.ViewHolder {

        TextView title, venue, organizer, datetxt, timetxt;

        public Upcoming_event_viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textview);
            organizer = itemView.findViewById(R.id.organizer_name_textview);
            venue = itemView.findViewById(R.id.venue_textview);
            datetxt = itemView.findViewById(R.id.date_textview);
            timetxt = itemView.findViewById(R.id.time_textview);
        }
    }
}
