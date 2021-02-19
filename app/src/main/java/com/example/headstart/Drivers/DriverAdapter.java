package com.example.headstart.Drivers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

import java.util.ArrayList;

public class DriverAdapter extends RecyclerView.Adapter<Holder> {

    Context context;
    ArrayList<Drivers> driverList;

    public DriverAdapter(Context context, ArrayList<Drivers> driverList) {
        super();
        this.context = context;
        this.driverList = driverList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Drivers drivers = driverList.get(position);

        holder.firstName.setText(drivers.getFirstName());
        holder.lastName.setText(drivers.getLastName());
        holder.phone.setText(drivers.getPhone());
        holder.email.setText(drivers.getEmail());
        holder.driverID.setText(drivers.getDriverId());
        holder.vehicleID.setText(drivers.getVehicleId());
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }
}
