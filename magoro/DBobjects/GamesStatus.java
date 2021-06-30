package com.example.magoro.DBobjects;

public class GamesStatus {

    public int ScoreJumpy, GamesJumpy, ScoreFlappy, Score2048;

    public GamesStatus(){

    }

    public GamesStatus(int ScoreJumpy, int GamesJumpy, int ScoreFlappy, int Score2048){

        this.ScoreJumpy = ScoreJumpy;
        this.GamesJumpy = GamesJumpy;
        this.ScoreFlappy = ScoreFlappy;
        this.Score2048 = Score2048;
    }
}
