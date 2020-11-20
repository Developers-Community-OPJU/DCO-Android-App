package com.android.dcoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Past_event_verticalview_adapter extends RecyclerView.Adapter<Past_event_verticalview_adapter.Past_event_vertical_viewHolder> {
    
    private Context context;
    private List<Event> eventList;
    
    public Past_event_verticalview_adapter(Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }
    
    @NonNull
    @Override
    public Past_event_vertical_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_cards, parent, false);
        return new Past_event_vertical_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Past_event_vertical_viewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.titletxt.setText(event.getTitle());
        holder.organizertxt.setText(event.getOrganiser());
        holder.venuetxt.setText(event.getVenue());
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
        return eventList.size();
    }

    public class Past_event_vertical_viewHolder extends RecyclerView.ViewHolder {
        
        TextView titletxt, organizertxt, venuetxt, datetxt, timetxt;

        public Past_event_vertical_viewHolder(@NonNull View itemView) {
            super(itemView);
            
            titletxt = itemView.findViewById(R.id.title_textview);
            organizertxt = itemView.findViewById(R.id.organizer_name_textview);
            venuetxt = itemView.findViewById(R.id.venue_textview);
            datetxt = itemView.findViewById(R.id.date_textview);
            timetxt = itemView.findViewById(R.id.time_textview);
            
        }
    }
}
