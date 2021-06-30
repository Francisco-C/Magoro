package com.example.magoro.DBobjects;

public class RunningInfo {

    public float distance, speed, calories;
    public String route, time;

    public RunningInfo(){

    }

    public RunningInfo(float distance, String time, float speed, float calories, String route){

        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.calories = calories;
        this.route = route;
    }
}
