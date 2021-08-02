package com.example.headstart.Home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class RoadTripHolder extends RecyclerView.ViewHolder {

    TextView driverName, active, status;
    public RoadTripHolder(@NonNull View itemView) {
        super(itemView);
        driverName = itemView.findViewById(R.id.driverName);
        active = itemView.findViewById(R.id.active_response);
        status = itemView.findViewById(R.id.status_response);
    }
}
