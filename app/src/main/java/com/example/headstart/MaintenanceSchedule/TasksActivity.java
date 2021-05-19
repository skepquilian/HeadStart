package com.example.headstart.MaintenanceSchedule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.R;

import java.text.DateFormat;
import java.util.Calendar;

public class TasksActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText taskName;
    private EditText taskDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_layout_activity);

        ImageButton closeBTN = findViewById(R.id.close_tasks_activityBTN);
        closeBTN.setOnClickListener(this);

        taskDate = findViewById(R.id.task_date_id);
        taskDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.close_tasks_activityBTN){
            onBackPressed();
        }
        else if (itemId == R.id.task_date_id){
            addDate();
        }
    }

    private void addDate() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String datePickedString = DateFormat.getDateInstance().format(calendar.getTime());
        taskDate.setText(datePickedString);

    }
}
