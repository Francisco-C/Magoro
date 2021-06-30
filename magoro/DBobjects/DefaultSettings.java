package com.example.magoro.DBobjects;

public class DefaultSettings {

   public int sleepState, coins, fitCoins, hungerBar, happinessBar;
   public long sleepTime, hungerTimer, fitnessTimer, hungerUpdate, fitnessUpdate;

    public DefaultSettings(){

    }

    public DefaultSettings(int sleepState, int coins, int fitCoins, int hungerBar, int happinessBar,
                           long sleepTime, long hungerTimer, long hungerUpdate, long fitnessTimer, long fitnessUpdate){

       this.coins = coins;
       this.fitCoins = fitCoins;
       this.hungerBar = hungerBar;
       this.happinessBar = happinessBar;
       this.hungerTimer = hungerTimer;
       this.fitnessTimer = fitnessTimer;
       this.hungerUpdate = hungerUpdate;
       this.fitnessUpdate = fitnessUpdate;
       this.sleepState = sleepState;
       this.sleepTime = sleepTime;
    }
}
