package com.example.chatapp.models;

public class User {

    public String Uid;
    public String Name;
    public String Number;
    public String Password;
    public String Email;
    public boolean isChecked;


    public User(String uid, String name, String number, String email, String password){
        Uid = uid;
        Name = name;
        Number = number;
        Email = email;
        Password = password;
        isChecked = false;
    }

    public User(){

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
