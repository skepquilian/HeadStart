package com.example.headstart.MaintenanceSchedule;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

public class ScheduleHolder extends RecyclerView.ViewHolder {

    TextView taskName, taskDate, taskDriverName;
    CardView cardViewParent;
    LinearLayout linearLayout;
    RelativeLayout expandableLayout;

    public ScheduleHolder(@NonNull View itemView) {
        super(itemView);
        this.taskName = itemView.findViewById(R.id.schedule_name);
        this.taskDate = itemView.findViewById(R.id.schedule_date);
        this.taskDriverName = itemView.findViewById(R.id.schedule_drv_name);
        this.cardViewParent = itemView.findViewById(R.id.schedule_row);
        this.linearLayout = itemView.findViewById(R.id.linear_layout_1);
        this.expandableLayout = itemView.findViewById(R.id.expandable_layout);



    }
}
