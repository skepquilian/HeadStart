package com.example.headstart.MaintenanceSchedule;

import android.content.Context;
import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.R;

import java.util.ArrayList;
import java.util.Random;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleHolder> {

    Context context;
    ArrayList<Schedules> scheduleList;

    public ScheduleAdapter(Context context, ArrayList<Schedules> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        final Schedules schedules = scheduleList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        holder.cardViewParent.setBackgroundColor(currentColor);
        holder.taskName.setText(schedules.getTaskName());
        holder.taskDate.setText(schedules.getTaskDate());
        holder.taskDriverName.setText(schedules.getTaskDriverName());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
