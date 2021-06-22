package com.example.headstart.Drivers;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DriverAdapter extends FirebaseRecyclerAdapter<Drivers, DriverHolder> {

    private static final String TAG = "DriverAdapter";
    FirebaseAuth auth = FirebaseAuth.getInstance();


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DriverAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }


    @NonNull
    @Override
    public DriverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_row_list, parent, false);
        return new DriverHolder(view) {
        };
    }

    @Override
    protected void onBindViewHolder(@NonNull final DriverHolder holder, final int position, @NonNull  final Drivers drivers) {
        holder.firstName.setText(drivers.getFirstName());
        holder.lastName.setText(drivers.getLastName());
        holder.phone.setText(drivers.getPhone());
        holder.email.setText(drivers.getEmail());
        holder.driverID.setText(drivers.getDriverID());
        holder.vehicleID.setText(drivers.getVehicleID());


        holder.editBar.setOnClickListener(v -> {
            Log.i(TAG, "onClick: show bottom dialog method.Option Bar pressed");
            //
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());
            //bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_driver_edit);

            //
            final EditText firstName = bottomSheetDialog.findViewById(R.id.edit_firstname);
            final EditText lastName = bottomSheetDialog.findViewById(R.id.edit_lastname);
            final EditText driverID = bottomSheetDialog.findViewById(R.id.edit_driverID);
            final EditText vehicleID = bottomSheetDialog.findViewById(R.id.edit_vehicleID);
            final EditText phone = bottomSheetDialog.findViewById(R.id.edit_phone);
            final EditText email = bottomSheetDialog.findViewById(R.id.edit_email);
            Button updateBTN = bottomSheetDialog.findViewById(R.id.updateBTN);

            firstName.setText(drivers.getFirstName());
            lastName.setText(drivers.getLastName());
            phone.setText(drivers.getPhone());
            email.setText(drivers.getEmail());
            driverID.setText(drivers.getDriverID());
            vehicleID.setText(drivers.getVehicleID());

            updateBTN.setOnClickListener(v1 -> {
                Map<String, Object> map = new HashMap<>();
                map.put("firstName", firstName.getText().toString());
                map.put("lastName", lastName.getText().toString());
                map.put("phone", phone.getText().toString());
                map.put("email", email.getText().toString());
                map.put("driverID", driverID.getText().toString());
                map.put("vehicleID", vehicleID.getText().toString());

                FirebaseDatabase.getInstance().getReference("User Drivers")
                        .child(auth.getCurrentUser().getUid())
                        .child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(v1.getContext(),"Updated Successfully", Toast.LENGTH_LONG).show();
                            bottomSheetDialog.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(v1.getContext(), "Error occurred", Toast.LENGTH_LONG).show();
                            bottomSheetDialog.dismiss();
                        });
            });
            bottomSheetDialog.show();

        });

        holder.deleteBar.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Remove Driver")
                    .setMessage("Delete ?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseDatabase.getInstance().getReference("User Drivers")
                                .child(auth.getCurrentUser().getUid())
                                .child(getRef(position).getKey()).removeValue()
                                .addOnFailureListener(e -> {
                                    Toast.makeText(v.getContext(), "Error occurred", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                });
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        holder.driverViewParent.setOnLongClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

            final TextView firstName = bottomSheetDialog.findViewById(R.id.d_FirstName);
            final TextView lastName = bottomSheetDialog.findViewById(R.id.d_LastName);

            firstName.setText(drivers.getFirstName());
            lastName.setText(drivers.getLastName());

            bottomSheetDialog.show();
            return false;
        });
    }

}