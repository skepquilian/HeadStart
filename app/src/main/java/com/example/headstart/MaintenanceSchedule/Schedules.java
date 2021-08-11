package com.example.headstart.MaintenanceSchedule;

import java.util.Comparator;

public class Schedules {

    public String taskName, taskDate, taskDriverName;
    public boolean isExpandable;


    /**
     * Here we create a driver object ...to be saved in firebase
     * we create two constructors .....1st constructor returns & accepts nothing, the 2nd constructor accepts arguments
     **/

    public Schedules() {
    }

    public Schedules(String taskName, String taskDate, String taskDriverName) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskDriverName = taskDriverName;
        this.isExpandable = false;
    }

    public static Comparator<Schedules> SchedulesA_ZAscending = new Comparator<Schedules>() {
        @Override
        public int compare(Schedules s1, Schedules s2) {
            return s1.getTaskName().compareTo(s2.getTaskName()) ;
        }
    };
    public static Comparator<Schedules> SchedulesZ_ADescending = new Comparator<Schedules>() {
        @Override
        public int compare(Schedules s1, Schedules s2) {
            return s2.getTaskName().compareTo(s1.getTaskName());
        }
    };

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskDriverName() {
        return taskDriverName;
    }

    public void setTaskDriverName(String taskDriverName) {
        this.taskDriverName = taskDriverName;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
