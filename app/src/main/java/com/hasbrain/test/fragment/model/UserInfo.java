package com.hasbrain.test.fragment.model;

import java.util.EnumSet;

/**
 * Created by Khang on 22/11/2015.
 */
public class UserInfo {
    public enum Sports {
        FOOTBALL, TENNIS,
        PING_PONG, SWIMMING,
        VOLLEYBALL, BASKETBALL
    }

    public String firstName, lastName, email, phoneNumber;
    public boolean isMale;
    public int salary;
    public EnumSet<Sports> sports;

    public UserInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserInfo setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserInfo setSex(boolean isMale) {
        this.isMale = isMale;
        return this;
    }

    public UserInfo setSalary(int salary) {
        this.salary = salary;
        return this;
    }

    public UserInfo setSports(EnumSet<Sports> sports) {
        this.sports = sports;
        return this;
    }
}
