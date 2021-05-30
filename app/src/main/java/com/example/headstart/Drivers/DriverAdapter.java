package com.example.headstart.Drivers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class DriverAdapter extends RecyclerView.Adapter<Holder> {

    private static final String TAG = "DriverAdapter";
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_row_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        final Drivers drivers = driverList.get(position);
        holder.firstName.setText(drivers.getFirstName());
        holder.lastName.setText(drivers.getLastName());
        holder.phone.setText(drivers.getPhone());
        holder.email.setText(drivers.getEmail());
        holder.driverID.setText(drivers.getDriverId());
        holder.vehicleID.setText(drivers.getVehicleId());


        holder.optionBar.setOnClickListener(v -> {
            Log.i(TAG, "onClick: show bottom dialog method.Option Bar pressed");
            bottomSheetDialog();
        });
        holder.driverViewParent.setOnLongClickListener(v -> {
            Log.i(TAG, "onClick: show bottom dialog method.Long press on cardView");
            bottomSheetDialog();
            return false;
        });
    }

    /**
     * Bottom sheet dialog
     */
    public void bottomSheetDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        bottomSheetDialog.show();
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }
}
