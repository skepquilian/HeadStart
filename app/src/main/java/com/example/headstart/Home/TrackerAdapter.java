package com.example.headstart.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.PhoneTracker.TrackerObject;
import com.example.headstart.R;

import java.util.ArrayList;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerHolder> {

    Context context;
    ArrayList<TrackerObject> trackerList;

    public TrackerAdapter(Context context, ArrayList<TrackerObject> trackerList) {
        this.context = context;
        this.trackerList = trackerList;
    }

    @NonNull
    @Override
    public TrackerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracker_row, parent, false);
        return new TrackerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerHolder holder, int position) {
        final TrackerObject trackerObject = trackerList.get(position);

        holder.id.setText(trackerObject.getVehicleID());
        holder.driverName.setText(trackerObject.getAssociateDriverName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return trackerList.size();
    }
}
