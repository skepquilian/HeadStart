package com.example.headstart.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.Drivers.Drivers;
import com.example.headstart.R;

import java.util.ArrayList;

public class RoadTripAdapter extends RecyclerView.Adapter<RoadTripHolder> {

    ArrayList<Drivers> tripList;
    Context context;

    public RoadTripAdapter(Context context, ArrayList<Drivers> tripList) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoadTripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_rows, parent, false);
        return new RoadTripHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoadTripHolder holder, int position) {
        final Drivers drivers = tripList.get(position);

        holder.driverName.setText(drivers.getIsActiveDriver());
        holder.active.setText(drivers.getIsActiveDriver());
        holder.status.setText(drivers.getDriveStatus());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
