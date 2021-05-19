package com.example.headstart.Drivers;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class Holder extends RecyclerView.ViewHolder {

    TextView firstName, lastName, email, phone, driverID, vehicleID;
    ImageView profileImage, optionBar;
    CardView driverViewParent;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.firstName = itemView.findViewById(R.id.d_FirstName);
        this.lastName = itemView.findViewById(R.id.d_LastName);
        this.phone = itemView.findViewById(R.id.d_PhoneNumber);
        this.email = itemView.findViewById(R.id.d_Email);
        this.driverID = itemView.findViewById(R.id.driverID);
        this.vehicleID = itemView.findViewById(R.id.vehicleID);
        this.profileImage = itemView.findViewById(R.id.driver_img);
        this.optionBar = itemView.findViewById(R.id.option_Bar);
        this.driverViewParent = itemView.findViewById(R.id.rows1);

    }
}
