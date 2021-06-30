package com.example.magoro.Activities.Game2048;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GamePreferences {


    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private static String userID = user.getUid();

    public static void saveBestScore(int score) {

        reference.child(userID).child("gamesStatus").child("Score2048").setValue(score);
    }
}
