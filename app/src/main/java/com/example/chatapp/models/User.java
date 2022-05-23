package com.example.chatapp.models;

public class User {

    public String Uid;
    public String Name;
    public String Number;
    public String Password;
    public String Email;


    public User(String uid, String name, String number, String email, String password){
        Uid = uid;
        Name = name;
        Number = number;
        Email = email;
        Password = password;

    }

    public User(){

    }

}
