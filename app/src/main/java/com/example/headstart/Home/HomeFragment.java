package com.example.headstart.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.Drivers.DriverAdapter;
import com.example.headstart.Drivers.Drivers;
import com.example.headstart.MaintenanceSchedule.ScheduleAdapter;
import com.example.headstart.MaintenanceSchedule.Schedules;
import com.example.headstart.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnTouchListener {

    //DriverAdapter driverAdapter;
    DatabaseReference scheduleDatabaseRef;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedules> scheduleList;
    RecyclerView recyclerView;
    ImageView imageViewLoader;
    TextView textViewLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        scheduleDatabaseRef = FirebaseDatabase.getInstance().getReference("Schedules")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());

//        FirebaseRecyclerOptions<Drivers> options
//                = new FirebaseRecyclerOptions.Builder<Drivers>()
//                .setQuery(driverDatabaseRef, Drivers.class)
//                .build();

        textViewLoader = view.findViewById(R.id.text_progress);
        imageViewLoader = view.findViewById(R.id.image_progressBar);
        scheduleList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(this.getContext(), scheduleList);
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        scheduleDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Schedules schedules = dataSnapshot.getValue(Schedules.class);
                    scheduleList.add(schedules);
                }
                recyclerView.setAdapter(scheduleAdapter);
                imageViewLoader.setVisibility(View.GONE);
                textViewLoader.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        driverAdapter.stopListening();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                // Allow ScrollView to intercept touch events.
                v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        // Handle MapView's touch events.
        return true;
    }
}
