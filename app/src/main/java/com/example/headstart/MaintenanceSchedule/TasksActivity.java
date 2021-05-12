package com.example.headstart.MaintenanceSchedule;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.R;

public class TasksActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_layout_activity);

        ImageButton closeBTN = findViewById(R.id.close_tasks_activityBTN);
        closeBTN.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.close_tasks_activityBTN){
            onBackPressed();
        }
    }

    public void numPick(){

    }
}
