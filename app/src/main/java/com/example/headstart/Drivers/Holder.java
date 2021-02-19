package com.example.headstart.Drivers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class Holder extends RecyclerView.ViewHolder {

    TextView firstName, lastName, email, phone, driverID, vehicleID;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.firstName = itemView.findViewById(R.id.driverFirstName);
        this.lastName = itemView.findViewById(R.id.driverLastName);
        this.phone = itemView.findViewById(R.id.driverPhoneNumber);
        this.email = itemView.findViewById(R.id.driverEmail);
        this.driverID = itemView.findViewById(R.id.driverID);
        this.vehicleID = itemView.findViewById(R.id.vehicleID);

    }
}
