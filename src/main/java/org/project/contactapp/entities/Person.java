package org.project.contactapp.entities;

import java.time.LocalDate;

public class Person {
    private int id;
    private String lastname; // Use lastname
    private String firstname; // Use firstname
    private String nickname;
    private String phone_number;
    private String address;
    private String email_address;
    private LocalDate birth_date;

    public Person(){}

    public Person(String firstname, String lastname, String phone_number) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone_number = phone_number;
    }

    // Constructor
    public Person(int id, String lastname, String firstname, String nickname, String phone_number, String address, String email_address, LocalDate birth_date) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.nickname = nickname;
        this.phone_number = phone_number;
        this.address = address;
        this.email_address = email_address;
        this.birth_date = birth_date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", address='" + address + '\'' +
                ", email_address='" + email_address + '\'' +
                ", birth_date=" + birth_date +
                '}';
    }
}