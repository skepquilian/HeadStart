package com.example.headstart.AuthenticateActivities;

public class User {

    public String organisationName, emailAddress, phoneNumber;

    /**
     * Here we create a user object ...to be saved in firebase
     * we create two constructors .....1st constructor returns & accepts nothing, the 2nd constructor accepts arguments
     **/

    //1st constructor,
    public User() {

    }

    //2nd constructor
    public User(String organisationName, String emailAddress, String phoneNumber) {
        //we initialize the objects....
        this.organisationName = organisationName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
