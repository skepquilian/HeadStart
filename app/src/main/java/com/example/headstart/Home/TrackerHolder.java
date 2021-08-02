package com.example.headstart.Home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class TrackerHolder extends RecyclerView.ViewHolder {

    TextView id, driverName;

    public TrackerHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.vehicleID_txt);
        driverName = itemView.findViewById(R.id.driverName_txt);
    }
}
