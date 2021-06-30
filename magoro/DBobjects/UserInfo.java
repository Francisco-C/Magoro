package com.example.magoro.DBobjects;

public class UserInfo {

    public String email, username;
    public int age, weight, height;

    public UserInfo(){

    }

    public UserInfo(String email, String username, int age, int weight, int height){

        this.email = email;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }
}
