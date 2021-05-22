package com.example.headstart.MaintenanceSchedule;

public class Schedules {

    public String taskName, taskDate, taskDriverName;


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
    }

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
}
