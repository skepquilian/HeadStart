package com.example.headstart.MaintenanceSchedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.headstart.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row_list, parent, false);
//        Random rnd = new Random();
//        int currentColor = Color.argb(18, rnd.nextInt(100), rnd.nextInt(255), rnd.nextInt(100));
//        view.setBackgroundColor(currentColor);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        final Schedules schedules = scheduleList.get(position);

        holder.taskName.setText(schedules.getTaskName().toUpperCase());
        holder.taskDate.setText(schedules.getTaskDate());
        holder.taskDriverName.setText(schedules.getTaskDriverName().toUpperCase());

        boolean isExpandable = schedules.isExpandable();

        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        holder.linearLayout.setOnClickListener(v -> {
            schedules.setExpandable(!isExpandable);
            notifyItemChanged(holder.getBindingAdapterPosition());

        });

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
