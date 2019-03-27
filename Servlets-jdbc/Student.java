package com.gmail.khitirinikoloz.objects;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;

    public Student(int id, String firstName, String lastName, String email, String phoneNum) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Student(String firstName, String lastName, String email, String phoneNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
