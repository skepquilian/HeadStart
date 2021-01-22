package com.example.headstart.Drivers;

public class Drivers {

    public String firstName, lastName, phone, email, driverId, vehicleId;


    /**
     * Here we create a driver object ...to be saved in firebase
     * we create two constructors .....1st constructor returns & accepts nothing, the 2nd constructor accepts arguments
     **/

    public Drivers() {
    }


    public Drivers(String firstName, String lastName, String phone, String email, String driverId, String vehicleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
    }
}
