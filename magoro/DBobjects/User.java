package com.example.magoro.DBobjects;

public class User {

    public UserInfo userInfo;
    public DefaultSettings settings;
    public RunningInfo runningInfo;
    public GamesStatus gamesStatus;
    public Items items;
    public Achievements achievements;

    public User(){

    }

    public User(UserInfo userInfo, DefaultSettings settings, RunningInfo runningInfo, GamesStatus gamesStatus, Items items, Achievements achievements){
        this.userInfo = userInfo;
        this.settings = settings;
        this.runningInfo = runningInfo;
        this.gamesStatus = gamesStatus;
        this.items = items;
        this.achievements = achievements;
    }
}
