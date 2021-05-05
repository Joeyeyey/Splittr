package com.example.splittr;

// User class to store information prior to being sent to database
public class UserActivity {

    // initialize variables
    public String fullName, email;

    // empty constructor
    public UserActivity() {}

    // constructor
    public UserActivity(String fullName, String email){
        this.fullName= fullName;
        this.email= email;
    }

}
