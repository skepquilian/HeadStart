package com.example.headstart.Drivers;

public class Drivers {

    public String firstName, lastName, phone, email, driverID, vehicleID;
    public String isActiveDriver;
    public int driveStatus;

    /**
     * Here we create a driver object ...to be saved in firebase
     * we create two constructors .....1st constructor returns & accepts nothing, the 2nd constructor accepts arguments
     **/

    public Drivers() {
    }


    //for driver profile
    public Drivers(String firstName, String lastName, String phone, String email, String driverID, String vehicleID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.driverID = driverID;
        this.vehicleID = vehicleID;
    }

    //for driver trip information
    public Drivers(String isActiveDriver, int driveStatus) {
        this.isActiveDriver = isActiveDriver;
        this.driveStatus = driveStatus;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverId) {
        this.driverID = driverId;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleId) {
        this.vehicleID = vehicleId;
    }

    public String getIsActiveDriver() {
        return isActiveDriver;
    }

    public void setIsActiveDriver(String isActiveDriver) {
        this.isActiveDriver = isActiveDriver;
    }

    public int getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(int driveStatus) {
        this.driveStatus = driveStatus;
    }
}
