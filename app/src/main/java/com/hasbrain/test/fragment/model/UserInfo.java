package com.hasbrain.test.fragment.model;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Created by Khang on 22/11/2015.
 */
public class UserInfo {
    public enum Sports {
        FOOTBALL, TENNIS,
        PING_PONG, SWIMMING,
        VOLLEYBALL, BASKETBALL;

        @Override
        public String toString() {
            return this.name();
        }
    }

    public String firstName, lastName, email, phoneNumber;
    public boolean isMale;
    public int salary;
    public EnumSet<Sports> sports;
    public String pathAvatar;

    public static UserInfo fromArrayList(ArrayList<String> arrayList) {
        UserInfo info = new UserInfo();
        info.setFirstName(arrayList.get(0))
                .setLastName(arrayList.get(1))
                .setEmail(arrayList.get(2))
                .setPhoneNumber(arrayList.get(3))
                .setSex(Boolean.valueOf(arrayList.get(4)))
                .setSalary(Integer.valueOf(arrayList.get(5)))
                .setPathAvatar(arrayList.get(6));
        EnumSet<Sports> sports = EnumSet.noneOf(UserInfo.Sports.class);
        for (int i = 7; i < arrayList.size(); i++)
            sports.add(Sports.valueOf(arrayList.get(i)));

        return info.setSports(sports);
    }

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

    public void setPathAvatar(String pathAvatar) {
        this.pathAvatar = pathAvatar;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(firstName);
        arrayList.add(lastName);
        arrayList.add(email);
        arrayList.add(phoneNumber);
        arrayList.add(Boolean.toString(isMale));
        arrayList.add(Integer.toString(salary));
        arrayList.add(pathAvatar);
        if (sports != null)
            for (Sports sport : sports) {
                arrayList.add(sport.toString());
            }
        return arrayList;
    }

    @Override
    public String toString() {
        return firstName + "\n" +
                lastName + "\n" +
                email + "\n" +
                phoneNumber + "\n" +
                salary + "\n" +
                sports;
    }
}
