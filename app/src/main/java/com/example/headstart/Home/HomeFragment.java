package com.example.headstart.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.Drivers.Drivers;
import com.example.headstart.MaintenanceSchedule.ScheduleAdapter;
import com.example.headstart.MaintenanceSchedule.Schedules;
import com.example.headstart.R;
import com.example.headstart.Utility.SnapHelperOneByOne;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment" ;
    //DriverAdapter driverAdapter;
    DatabaseReference tripDatabaseRef;
    private RoadTripAdapter roadTripAdapter;
    private ArrayList<Drivers> driverTripList;
    RecyclerView recyclerView;
    ImageView imageViewLoader;
    TextView textViewLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        tripDatabaseRef = FirebaseDatabase.getInstance().getReference("Drivers")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());

//        FirebaseRecyclerOptions<DriDriversvers> options
//                = new FirebaseRecyclerOptions.Builder<Drivers>()
//                .setQuery(driverDatabaseRef, Drivers.class)
//                .build();

        textViewLoader = view.findViewById(R.id.text_progress);
        imageViewLoader = view.findViewById(R.id.image_progressBar);
        driverTripList = new ArrayList<>();
        roadTripAdapter = new RoadTripAdapter(getContext(), driverTripList);
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));

        //show recycler on at a time in page
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerView);


        tripDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driverTripList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Drivers drivers = dataSnapshot.getValue(Drivers.class);
                    driverTripList.add(drivers);
                }
                recyclerView.setAdapter(roadTripAdapter);
                imageViewLoader.setVisibility(View.GONE);
                textViewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
////        driverAdapter.stopListening();
        return view;
   }

    @Override
    public void onStart() {
        super.onStart();
    }
}
