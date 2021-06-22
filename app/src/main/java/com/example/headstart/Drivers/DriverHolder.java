package com.example.headstart.Drivers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class DriverHolder extends RecyclerView.ViewHolder {

    TextView firstName, lastName, email, phone, driverID, vehicleID;
    ImageView profileImage, editBar, deleteBar;
    CardView driverViewParent;

    public DriverHolder(@NonNull View itemView) {
        super(itemView);

        this.firstName = itemView.findViewById(R.id.d_FirstName);
        this.lastName = itemView.findViewById(R.id.d_LastName);
        this.phone = itemView.findViewById(R.id.d_PhoneNumber);
        this.email = itemView.findViewById(R.id.d_Email);
        this.driverID = itemView.findViewById(R.id.driverID);
        this.vehicleID = itemView.findViewById(R.id.vehicleID);
        this.profileImage = itemView.findViewById(R.id.driver_img);
        this.editBar = itemView.findViewById(R.id.edit_Bar);
        this.deleteBar = itemView.findViewById(R.id.delete_Bar);
        this.driverViewParent = itemView.findViewById(R.id.rows1);

    }
}
