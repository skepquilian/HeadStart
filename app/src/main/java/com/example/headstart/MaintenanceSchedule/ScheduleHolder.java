package com.example.headstart.MaintenanceSchedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class ScheduleHolder extends RecyclerView.ViewHolder {

    TextView taskName, taskDate, taskDriverName;

    public ScheduleHolder(@NonNull View itemView) {
        super(itemView);
        this.taskName = itemView.findViewById(R.id.d_FirstName);
        this.taskDate = itemView.findViewById(R.id.d_LastName);
        this.taskDriverName = itemView.findViewById(R.id.d_PhoneNumber);
    }
}
