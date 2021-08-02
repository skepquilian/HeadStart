package com.example.headstart.PhoneTracker;

public class TrackerObject {

    public TrackerObject() {
    }

    String vehicleID, associateDriverName;

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getAssociateDriverName() {
        return associateDriverName;
    }

    public void setAssociateDriverName(String associateDriverName) {
        this.associateDriverName = associateDriverName;
    }

    public TrackerObject(String vehicleID, String associateDriverName) {
        this.vehicleID = vehicleID;
        this.associateDriverName = associateDriverName;
    }
}
