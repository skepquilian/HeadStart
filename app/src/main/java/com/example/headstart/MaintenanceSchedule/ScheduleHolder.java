package com.example.headstart.MaintenanceSchedule;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class ScheduleHolder extends RecyclerView.ViewHolder {

    TextView taskName, taskDate, taskDriverName;
    CardView cardViewParent;

    public ScheduleHolder(@NonNull View itemView) {
        super(itemView);
        this.taskName = itemView.findViewById(R.id.d_FirstName);
        this.taskDate = itemView.findViewById(R.id.d_LastName);
        this.taskDriverName = itemView.findViewById(R.id.d_PhoneNumber);
        this.cardViewParent = itemView.findViewById(R.id.rows1);
    }
}
