package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.DBobjects.Achievements;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AchievementsFragment extends Fragment {

    private RelativeLayout NoAchievement;
    private RelativeLayout noNextAchievement;
    private int NumberMainAchievement, coins, fitCoins;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    String userID = user.getUid();
    CardView loading;

    public AchievementsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);

        MainActivity.menu.setVisibility(View.GONE);

        loading = view.findViewById(R.id.loadingAchievements);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                NumberMainAchievement = snapshot.child("achievements").child("Main").getValue(int.class);
                coins = snapshot.child("settings").child("coins").getValue(int.class);
                fitCoins = snapshot.child("settings").child("fitCoins").getValue(int.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageView close = view.findViewById(R.id.closeAchieve);

        close.setOnClickListener(v -> {
            SettingsFragment fragment1 = new SettingsFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        //On Going Achievements

        //Walk2km
        RelativeLayout walk2 = view.findViewById(R.id.walk2);
        TextView txtWalk1 = view.findViewById(R.id.pgwalk1N);
        ProgressBar pgWalk1= view.findViewById(R.id.pgwalk1);
        Button btnWalk1 = view.findViewById(R.id.btnWalk1);

        //Walk20KM
        RelativeLayout walk20 = view.findViewById(R.id.walk20);
        TextView txtWalk2= view.findViewById(R.id.pgwalk2N);
        ProgressBar pgWalk2 = view.findViewById(R.id.pgwalk2);
        Button btnWalk2 = view.findViewById(R.id.btnWalk2);

        //Walk100km
        RelativeLayout walk100 = view.findViewById(R.id.walk100);
        TextView txtWalk3 = view.findViewById(R.id.pgwalk3N);
        ProgressBar pgWalk3 = view.findViewById(R.id.pgwalk3);
        Button btnWalk3 = view.findViewById(R.id.btnWalk3);

        //walk1000km
        RelativeLayout walk1000 = view.findViewById(R.id.walk1000);
        TextView txtWalk4= view.findViewById(R.id.pgwalk4N);
        ProgressBar pgWalk4= view.findViewById(R.id.pgwalk4);
        Button btnWalk4 = view.findViewById(R.id.btnWalk4);

        //walk2horas
        RelativeLayout walk2Hour = view.findViewById(R.id.walk2hour);
        TextView txtHWalk1 = view.findViewById(R.id.pgwh1N);
        ProgressBar pgWh1= view.findViewById(R.id.pgwh1);
        Button btnWH1= view.findViewById(R.id.btnWH1);

        //walk10horas
        RelativeLayout walk10Hour = view.findViewById(R.id.walk10Hour);
        TextView txtHWalk2 = view.findViewById(R.id.pgwh2N);
        ProgressBar pgWh2 = view.findViewById(R.id.pgwh2);
        Button btnWH2 = view.findViewById(R.id.btnWH2);

        //walk50horas
        RelativeLayout walk50Hour = view.findViewById(R.id.walk50Hours);
        TextView txtHWalk3 = view.findViewById(R.id.pgwh3N);
        ProgressBar pgWh3 = view.findViewById(R.id.pgwh3);
        Button btnWH3 = view.findViewById(R.id.btnWH3);

        //walk500horas
        RelativeLayout walk500Hour = view.findViewById(R.id.walk500Hours);
        TextView txtHWalk4 = view.findViewById(R.id.pgwh4N);
        ProgressBar pgWh4 = view.findViewById(R.id.pgwh4);
        Button btnWH4 = view.findViewById(R.id.btnWH4);

        //sleep2days
        RelativeLayout Sleep2 = view.findViewById(R.id.Sleep2);
        TextView txtSleep1 = view.findViewById(R.id.pgs1N);
        ProgressBar pgS1 = view.findViewById(R.id.pgs1);
        Button btnSleep1= view.findViewById(R.id.btnSleep1);

        //sleep10days
        RelativeLayout Sleep10 = view.findViewById(R.id.Sleep10);
        TextView txtSleep2 = view.findViewById(R.id.pgs2N);
        ProgressBar pgS2 = view.findViewById(R.id.pgs2);
        Button btnSleep2 = view.findViewById(R.id.btnSleep2);

        //sleep30days
        RelativeLayout Sleep30 = view.findViewById(R.id.Sleep30);
        TextView txtSleep3 = view.findViewById(R.id.pgs3N);
        ProgressBar pgS3 = view.findViewById(R.id.pgs3);
        Button btnSleep3 = view.findViewById(R.id.btnSleep3);

        //sleep100days
        RelativeLayout Sleep100 = view.findViewById(R.id.Sleep100);
        TextView txtSleep4 = view.findViewById(R.id.pgs4N);
        ProgressBar pgS4 = view.findViewById(R.id.pgs4);
        Button btnSleep4 = view.findViewById(R.id.btnSleep4);

        //give30food
        RelativeLayout Food30 = view.findViewById(R.id.Food30);
        TextView txtFood1 = view.findViewById(R.id.pgf1N);
        ProgressBar pgF1 = view.findViewById(R.id.pgf1);
        Button btnFood1= view.findViewById(R.id.btnFood1);

        //give250food
        RelativeLayout Food250 = view.findViewById(R.id.Food250);
        TextView txtFood2 = view.findViewById(R.id.pgf2N);
        ProgressBar pgF2 = view.findViewById(R.id.pgf2);
        Button btnFood2 = view.findViewById(R.id.btnFood2);

        //give500food
        RelativeLayout Food500 = view.findViewById(R.id.Food500);
        TextView txtFood3 = view.findViewById(R.id.pgf3N);
        ProgressBar pgF3 = view.findViewById(R.id.pgf3);
        Button btnFood3 = view.findViewById(R.id.btnFood3);

        //give1000food
        RelativeLayout Food1000 = view.findViewById(R.id.Food1000);
        TextView txtFood4 = view.findViewById(R.id.pgf4N);
        ProgressBar pgF4 = view.findViewById(R.id.pgf4);
        Button btnFood4 = view.findViewById(R.id.btnFood4);

        //TodosAchievements
        RelativeLayout MainAchievements = view.findViewById(R.id.MainAchievements);
        TextView txtMainAchievement= view.findViewById(R.id.pgm1N);
        ProgressBar pgMain  = view.findViewById(R.id.pgm1);
        Button btnMain= view.findViewById(R.id.btnMain);

        //TodosMagoros
        RelativeLayout achievementMagoro = view.findViewById(R.id.achievementMagoro);
        TextView txtNMagoro = view.findViewById(R.id.pgm2N);
        ProgressBar pgNMagoro = view.findViewById(R.id.pgm2);
        Button btnNMagoro = view.findViewById(R.id.btnNMagoro);

        //Separadores
        RelativeLayout runSep = view.findViewById(R.id.runSep);
        RelativeLayout sleepSep = view.findViewById(R.id.SleepSep);
        RelativeLayout eatSep = view.findViewById(R.id.EatSep);
        RelativeLayout extraSep = view.findViewById(R.id.ExtraSep);

        //Done Achievements
        NoAchievement= view.findViewById(R.id.NoAchievements);
        RelativeLayout ADone1 = view.findViewById(R.id.ADone1);
        RelativeLayout ADone2 = view.findViewById(R.id.ADone2);
        RelativeLayout ADone3 = view.findViewById(R.id.ADone3);
        RelativeLayout ADone4 = view.findViewById(R.id.ADone4);
        RelativeLayout ADone5 = view.findViewById(R.id.ADone5);
        RelativeLayout ADone6 = view.findViewById(R.id.ADone6);
        RelativeLayout ADone7 = view.findViewById(R.id.ADone7);
        RelativeLayout ADone8 = view.findViewById(R.id.ADone8);
        RelativeLayout ADone9 = view.findViewById(R.id.ADone9);
        RelativeLayout ADone10 = view.findViewById(R.id.ADone10);
        RelativeLayout ADone11 = view.findViewById(R.id.ADone11);
        RelativeLayout ADone12 = view.findViewById(R.id.ADone12);
        RelativeLayout ADone13 = view.findViewById(R.id.ADone13);
        RelativeLayout ADone14 = view.findViewById(R.id.ADone14);
        RelativeLayout ADone15 = view.findViewById(R.id.ADone15);
        RelativeLayout ADone16 = view.findViewById(R.id.ADone16);
        RelativeLayout ADone17 = view.findViewById(R.id.ADone17);
        RelativeLayout ADone18 = view.findViewById(R.id.ADone18);

        //NextAchievements
        RelativeLayout achievementBlocked1 = view.findViewById(R.id.achievementBlocked1);
        RelativeLayout achievementBlocked2 = view.findViewById(R.id.achievementBlocked2);
        RelativeLayout achievementBlocked3 = view.findViewById(R.id.achievementBlocked3);
        RelativeLayout achievementBlocked4 = view.findViewById(R.id.achievementBlocked4);
        RelativeLayout achievementBlocked5 = view.findViewById(R.id.achievementBlocked5);
        RelativeLayout achievementBlocked6 = view.findViewById(R.id.achievementBlocked6);
        RelativeLayout achievementBlocked7 = view.findViewById(R.id.achievementBlocked7);
        RelativeLayout achievementBlocked8 = view.findViewById(R.id.achievementBlocked8);
        RelativeLayout achievementBlocked9 = view.findViewById(R.id.achievementBlocked9);
        RelativeLayout achievementBlocked10 = view.findViewById(R.id.achievementBlocked10);
        RelativeLayout achievementBlocked11 = view.findViewById(R.id.achievementBlocked11);
        RelativeLayout achievementBlocked12 = view.findViewById(R.id.achievementBlocked12);
        noNextAchievement= view.findViewById(R.id.NoNextAchievement);


        btnWalk1.setOnClickListener(v-> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk2KmCompleted").setValue(true);
            showPopupWindow(v, 1);
        });

        btnWalk2.setOnClickListener(v-> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk20KmCompleted").setValue(true);
            showPopupWindow(v, 2);
        });

        btnWalk3.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk100KmCompleted").setValue(true);
            showPopupWindow(v, 3);
        });

        btnWalk4.setOnClickListener(v ->{
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk1000KmCompleted").setValue(true);
            showPopupWindow(v, 4);
        });

        btnWH1.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk2HCompleted").setValue(true);
            showPopupWindow(v, 5);
        });

        btnWH2.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk10HCompleted").setValue(true);
            showPopupWindow(v, 6);
        });

        btnWH3.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk50HCompleted").setValue(true);
            showPopupWindow(v, 7);
        });

        btnWH4.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("walk500HCompleted").setValue(true);
            showPopupWindow(v, 8);
        });

        btnSleep1.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("sleep2Completed").setValue(true);
            showPopupWindow(v, 9);
        });

        btnSleep2.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("sleep10Completed").setValue(true);
            showPopupWindow(v, 10);
        });

        btnSleep3.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("sleep30Completed").setValue(true);
            showPopupWindow(v, 11);
        });

        btnSleep4.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("sleep100Completed").setValue(true);
            showPopupWindow(v, 12);
        });

        btnFood1.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("food30Completed").setValue(true);
            showPopupWindow(v, 13);
        });

        btnFood2.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("food250Completed").setValue(true);
            showPopupWindow(v, 14);
        });

        btnFood3.setOnClickListener(v -> {

            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("food500Completed").setValue(true);
            showPopupWindow(v, 15);
        });

        btnFood4.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("food1000Completed").setValue(true);
            showPopupWindow(v, 16);
        });

        btnMain.setOnClickListener(v -> {

            reference.child(userID).child("achievements").child("MainCompleted").setValue(true);
            showPopupWindow(v, 17);
        });

        btnNMagoro.setOnClickListener(v -> {
            NumberMainAchievement += 1;
            reference.child(userID).child("achievements").child("Main").setValue(NumberMainAchievement);
            reference.child(userID).child("achievements").child("NumberMagorosCompleted").setValue(true);
            showPopupWindow(v, 18);
        });

        reference.child(userID).child("achievements").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Achievements achievements = snapshot.getValue(Achievements.class);
                if(achievements != null){
                    float walk2Km = achievements.walk2Km;
                    float walk20Km = achievements.walk20Km;
                    float walk100Km = achievements.walk100Km;
                    float walk1000Km = achievements.walk1000Km;
                    float walk2H = achievements.walk2H;
                    float walk10H = achievements.walk10H;
                    float walk50H = achievements.walk50H;
                    float walk500H = achievements.walk500H;
                    int food30 = achievements.food30;
                    int food250 = achievements.food250;
                    int food500 = achievements.food500;
                    int food1000 = achievements.food1000;
                    int sleep2 = achievements.sleep2;
                    int sleep10 = achievements.sleep10;
                    int sleep30 = achievements.sleep30;
                    int sleep100 = achievements.sleep100;
                    int NumberMagoros = achievements.NumberMagoros;
                    int main = achievements.Main;

                    boolean walk2KmCompleted = achievements.walk2KmCompleted;
                    boolean walk20KmCompleted = achievements.walk20KmCompleted;
                    boolean walk100KmCompleted = achievements.walk100KmCompleted;
                    boolean walk1000KmCompleted = achievements.walk1000KmCompleted;
                    boolean walk2HCompleted = achievements.walk2HCompleted;
                    boolean walk10HCompleted = achievements.walk10HCompleted;
                    boolean walk50HCompleted = achievements.walk50HCompleted;
                    boolean walk500HCompleted = achievements.walk500HCompleted;
                    boolean food30Completed = achievements.food30Completed;
                    boolean food250Completed = achievements.food250Completed;
                    boolean food500Completed = achievements.food500Completed;
                    boolean food1000Completed = achievements.food1000Completed;
                    boolean sleep2Completed = achievements.sleep2Completed;
                    boolean sleep10Completed = achievements.sleep10Completed;
                    boolean sleep30Completed = achievements.sleep30Completed;
                    boolean sleep100Completed = achievements.sleep100Completed;
                    boolean NumberMagorosCompleted = achievements.NumberMagorosCompleted;
                    boolean MainCompleted = achievements.MainCompleted;

                    int progressWalk1 = (int) (walk2Km * 100 / 2);
                    int progressWalk2 = (int) (walk20Km * 100 / 20);
                    int progressWalk3 = (int) (walk100Km * 100 / 100);
                    int progressWalk4 = (int) (walk1000Km * 100 / 1000);
                    int progressWH1 = (int) (walk2H * 100 / 2);
                    int progressWH2 = (int) (walk10H * 100 / 10);
                    int progressWH3 = (int) (walk50H * 100 / 50);
                    int progressWH4 = (int) (walk500H * 100 / 500);
                    int progressSleep1 = sleep2 * 100 / 2;
                    int progressSleep2 = sleep10 * 100 / 10;
                    int progressSleep3 = sleep30 * 100 / 30;
                    int progressSleep4 = sleep100 * 100 / 100;
                    int progressFood1 = food30 * 100 / 30;
                    int progressFood2 = food250 * 100 / 250;
                    int progressFood3 = food500 * 100 / 500;
                    int progressFood4 = food1000 * 100 / 100;
                    int progressNMagoro = NumberMagoros * 100 / 6;
                    int progressMain = main * 100 / 17;

                    pgWalk1.setProgress(progressWalk1);
                    pgWalk2.setProgress(progressWalk2);
                    pgWalk3.setProgress(progressWalk3);
                    pgWalk4.setProgress(progressWalk4);
                    pgWh1.setProgress(progressWH1);
                    pgWh2.setProgress(progressWH2);
                    pgWh3.setProgress(progressWH3);
                    pgWh4.setProgress(progressWH4);
                    pgS1.setProgress(progressSleep1);
                    pgS2.setProgress(progressSleep2);
                    pgS3.setProgress(progressSleep3);
                    pgS4.setProgress(progressSleep4);
                    pgF1.setProgress(progressFood1);
                    pgF2.setProgress(progressFood2);
                    pgF3.setProgress(progressFood3);
                    pgF4.setProgress(progressFood4);
                    pgNMagoro.setProgress(progressNMagoro);
                    pgMain.setProgress(progressMain);

                    txtWalk1.setText(walk2Km + " / 2 Km");
                    txtWalk2.setText(walk20Km + " / 20 Km");
                    txtWalk3.setText(walk100Km + " / 100 Km");
                    txtWalk4.setText(walk1000Km + " / 1000 Km");
                    txtHWalk1.setText(walk2H + " / 2 H");
                    txtHWalk2.setText(walk10H + " / 10 H");
                    txtHWalk3.setText(walk50H + " / 50 H");
                    txtHWalk4.setText(walk500H + " / 500 H");
                    txtFood1.setText(food30 + " / 30 times");
                    txtFood2.setText(food250 + " / 250 times");
                    txtFood3.setText(food500 + " / 500 times");
                    txtFood4.setText(food1000 + " / 1000 times");
                    txtSleep1.setText(sleep2 + " / 2 D");
                    txtSleep2.setText(sleep10 + " / 10 D");
                    txtSleep3.setText(sleep30 + " / 30 D");
                    txtSleep4.setText(sleep100 + " / 100 D");
                    txtNMagoro.setText(NumberMagoros + " / 6 Magoros");
                    txtMainAchievement.setText(main + " / 17");

                    if(progressWalk1 >= 100){
                        btnWalk1.setVisibility(View.VISIBLE);
                    }
                    if(progressWalk2 >= 100){
                        btnWalk2.setVisibility(View.VISIBLE);
                    }
                    if(progressWalk3 >= 100){
                        btnWalk3.setVisibility(View.VISIBLE);
                    }
                    if(progressWalk4 >= 100){
                        btnWalk4.setVisibility(View.VISIBLE);
                    }
                    if(progressWH1 >= 100) {
                        btnWH1.setVisibility(View.VISIBLE);
                    }
                    if(progressWH2 >= 100) {
                        btnWH2.setVisibility(View.VISIBLE);
                    }
                    if(progressWH3 >= 100) {
                        btnWH3.setVisibility(View.VISIBLE);
                    }
                    if(progressWH4 >= 100) {
                        btnWH4.setVisibility(View.VISIBLE);
                    }
                    if(progressFood1 >= 100){
                        btnFood1.setVisibility(View.VISIBLE);
                    }
                    if(progressFood2 >= 100){
                        btnFood2.setVisibility(View.VISIBLE);
                    }
                    if(progressFood3 >= 100){
                        btnFood3.setVisibility(View.VISIBLE);
                    }
                    if(progressFood4 >= 100){
                        btnFood4.setVisibility(View.VISIBLE);
                    }
                    if(progressSleep1 >= 100){
                        btnSleep1.setVisibility(View.VISIBLE);
                    }
                    if(progressSleep2 >= 100){
                        btnSleep2.setVisibility(View.VISIBLE);
                    }
                    if(progressSleep3 >= 100){
                        btnSleep3.setVisibility(View.VISIBLE);
                    }
                    if(progressSleep4 >= 100){
                        btnSleep4.setVisibility(View.VISIBLE);
                    }
                    if(progressNMagoro >= 100){
                        btnNMagoro.setVisibility(View.VISIBLE);
                    }
                    if(progressMain >= 100){
                        btnMain.setVisibility(View.VISIBLE);
                    }

                    if(walk2KmCompleted && !walk20KmCompleted && !walk100KmCompleted && !walk1000KmCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2.setVisibility(View.GONE);
                        walk20.setVisibility(View.VISIBLE);
                        ADone1.setVisibility(View.VISIBLE);
                        achievementBlocked1.setVisibility(View.GONE);
                    }
                    else if(walk2KmCompleted && walk20KmCompleted && !walk100KmCompleted && !walk1000KmCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2.setVisibility(View.GONE);
                        walk20.setVisibility(View.GONE);
                        walk100.setVisibility(View.VISIBLE);
                        ADone1.setVisibility(View.VISIBLE);
                        ADone2.setVisibility(View.VISIBLE);
                        achievementBlocked1.setVisibility(View.GONE);
                        achievementBlocked2.setVisibility(View.GONE);
                    }
                    else if(walk2KmCompleted && walk20KmCompleted && walk100KmCompleted && !walk1000KmCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2.setVisibility(View.GONE);
                        walk20.setVisibility(View.GONE);
                        walk100.setVisibility(View.GONE);
                        walk1000.setVisibility(View.VISIBLE);
                        ADone1.setVisibility(View.VISIBLE);
                        ADone2.setVisibility(View.VISIBLE);
                        ADone3.setVisibility(View.VISIBLE);
                        achievementBlocked1.setVisibility(View.GONE);
                        achievementBlocked2.setVisibility(View.GONE);
                        achievementBlocked3.setVisibility(View.GONE);
                    }
                    else if(walk2KmCompleted && walk20KmCompleted && walk100KmCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2.setVisibility(View.GONE);
                        walk20.setVisibility(View.GONE);
                        walk100.setVisibility(View.GONE);
                        walk1000.setVisibility(View.GONE);
                        ADone1.setVisibility(View.VISIBLE);
                        ADone2.setVisibility(View.VISIBLE);
                        ADone3.setVisibility(View.VISIBLE);
                        ADone4.setVisibility(View.VISIBLE);
                        achievementBlocked1.setVisibility(View.GONE);
                        achievementBlocked2.setVisibility(View.GONE);
                        achievementBlocked3.setVisibility(View.GONE);
                    }

                    if(walk2HCompleted && !walk10HCompleted && !walk50HCompleted && !walk500HCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2Hour.setVisibility(View.GONE);
                        walk10Hour.setVisibility(View.VISIBLE);
                        ADone5.setVisibility(View.VISIBLE);
                        achievementBlocked4.setVisibility(View.GONE);
                    }
                    else if(walk2HCompleted && walk10HCompleted && !walk50HCompleted && !walk500HCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2Hour.setVisibility(View.GONE);
                        walk10Hour.setVisibility(View.GONE);
                        walk50Hour.setVisibility(View.VISIBLE);
                        ADone5.setVisibility(View.VISIBLE);
                        ADone6.setVisibility(View.VISIBLE);
                        achievementBlocked4.setVisibility(View.GONE);
                        achievementBlocked5.setVisibility(View.GONE);
                    }
                    else if(walk2HCompleted && walk10HCompleted && walk50HCompleted && !walk500HCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2Hour.setVisibility(View.GONE);
                        walk10Hour.setVisibility(View.GONE);
                        walk50Hour.setVisibility(View.GONE);
                        walk500Hour.setVisibility(View.VISIBLE);
                        ADone5.setVisibility(View.VISIBLE);
                        ADone6.setVisibility(View.VISIBLE);
                        ADone7.setVisibility(View.VISIBLE);
                        achievementBlocked4.setVisibility(View.GONE);
                        achievementBlocked5.setVisibility(View.GONE);
                        achievementBlocked6.setVisibility(View.GONE);
                    }
                    else if(walk2HCompleted && walk10HCompleted && walk50HCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        walk2Hour.setVisibility(View.GONE);
                        walk10Hour.setVisibility(View.GONE);
                        walk50Hour.setVisibility(View.GONE);
                        walk500Hour.setVisibility(View.GONE);
                        ADone5.setVisibility(View.VISIBLE);
                        ADone6.setVisibility(View.VISIBLE);
                        ADone7.setVisibility(View.VISIBLE);
                        ADone8.setVisibility(View.VISIBLE);
                        achievementBlocked4.setVisibility(View.GONE);
                        achievementBlocked5.setVisibility(View.GONE);
                        achievementBlocked6.setVisibility(View.GONE);
                    }
                    if(walk100KmCompleted && walk500HCompleted){
                        runSep.setVisibility(View.GONE);
                    }

                    if(sleep2Completed && !sleep10Completed && !sleep30Completed && !sleep100Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Sleep2.setVisibility(View.GONE);
                        Sleep10.setVisibility(View.VISIBLE);
                        ADone9.setVisibility(View.VISIBLE);
                        achievementBlocked7.setVisibility(View.GONE);
                    }
                    else if(sleep2Completed && sleep10Completed && !sleep30Completed && !sleep100Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Sleep2.setVisibility(View.GONE);
                        Sleep10.setVisibility(View.GONE);
                        Sleep30.setVisibility(View.VISIBLE);
                        ADone9.setVisibility(View.VISIBLE);
                        ADone10.setVisibility(View.VISIBLE);
                        achievementBlocked7.setVisibility(View.GONE);
                        achievementBlocked8.setVisibility(View.GONE);
                    }
                    else if(sleep2Completed && sleep10Completed && sleep30Completed && !sleep100Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Sleep2.setVisibility(View.GONE);
                        Sleep10.setVisibility(View.GONE);
                        Sleep30.setVisibility(View.GONE);
                        Sleep100.setVisibility(View.VISIBLE);
                        ADone9.setVisibility(View.VISIBLE);
                        ADone10.setVisibility(View.VISIBLE);
                        ADone11.setVisibility(View.VISIBLE);
                        achievementBlocked7.setVisibility(View.GONE);
                        achievementBlocked8.setVisibility(View.GONE);
                        achievementBlocked9.setVisibility(View.GONE);
                    }
                    else if(sleep2Completed && sleep10Completed && sleep30Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Sleep2.setVisibility(View.GONE);
                        Sleep10.setVisibility(View.GONE);
                        Sleep30.setVisibility(View.GONE);
                        Sleep100.setVisibility(View.GONE);
                        ADone9.setVisibility(View.VISIBLE);
                        ADone10.setVisibility(View.VISIBLE);
                        ADone11.setVisibility(View.VISIBLE);
                        ADone12.setVisibility(View.VISIBLE);
                        achievementBlocked7.setVisibility(View.GONE);
                        achievementBlocked8.setVisibility(View.GONE);
                        achievementBlocked9.setVisibility(View.GONE);
                        sleepSep.setVisibility(View.GONE);
                    }

                    if(food30Completed && !food250Completed && !food500Completed && !food1000Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Food30.setVisibility(View.GONE);
                        Food250.setVisibility(View.VISIBLE);
                        ADone13.setVisibility(View.VISIBLE);
                        achievementBlocked10.setVisibility(View.GONE);
                    }
                    else if(food30Completed && food250Completed && !food500Completed && !food1000Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Food30.setVisibility(View.GONE);
                        Food250.setVisibility(View.GONE);
                        Food500.setVisibility(View.VISIBLE);
                        ADone13.setVisibility(View.VISIBLE);
                        ADone14.setVisibility(View.VISIBLE);
                        achievementBlocked10.setVisibility(View.GONE);
                        achievementBlocked11.setVisibility(View.GONE);
                    }
                    else if(food30Completed && food250Completed && food500Completed && !food1000Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Food30.setVisibility(View.GONE);
                        Food250.setVisibility(View.GONE);
                        Food500.setVisibility(View.GONE);
                        Food1000.setVisibility(View.VISIBLE);
                        ADone13.setVisibility(View.VISIBLE);
                        ADone14.setVisibility(View.VISIBLE);
                        ADone15.setVisibility(View.VISIBLE);
                        achievementBlocked10.setVisibility(View.GONE);
                        achievementBlocked11.setVisibility(View.GONE);
                        achievementBlocked12.setVisibility(View.GONE);
                    }
                    else if(food30Completed && food250Completed && food500Completed){
                        NoAchievement.setVisibility(View.GONE);
                        Food30.setVisibility(View.GONE);
                        Food250.setVisibility(View.GONE);
                        Food500.setVisibility(View.GONE);
                        Food1000.setVisibility(View.GONE);
                        ADone13.setVisibility(View.VISIBLE);
                        ADone14.setVisibility(View.VISIBLE);
                        ADone15.setVisibility(View.VISIBLE);
                        ADone16.setVisibility(View.VISIBLE);
                        achievementBlocked10.setVisibility(View.GONE);
                        achievementBlocked11.setVisibility(View.GONE);
                        achievementBlocked12.setVisibility(View.GONE);
                        eatSep.setVisibility(View.GONE);
                    }

                    if(MainCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        MainAchievements.setVisibility(View.GONE);
                        ADone17.setVisibility(View.VISIBLE);
                    }
                    if(NumberMagorosCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        achievementMagoro.setVisibility(View.GONE);
                        ADone18.setVisibility(View.VISIBLE);
                    }
                    if(MainCompleted && NumberMagorosCompleted){
                        NoAchievement.setVisibility(View.GONE);
                        MainAchievements.setVisibility(View.GONE);
                        ADone17.setVisibility(View.VISIBLE);
                        achievementMagoro.setVisibility(View.GONE);
                        ADone18.setVisibility(View.VISIBLE);
                        extraSep.setVisibility(View.GONE);
                    }

                    if(walk100KmCompleted && walk50HCompleted && food500Completed && sleep30Completed){
                        noNextAchievement.setVisibility(View.VISIBLE);
                    }

                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void showPopupWindow(final View view, int btnClicked) {

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_achievement_prize, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button collect = popupView.findViewById(R.id.collectPrize);
        TextView collectText = popupView.findViewById(R.id.collectPrizeText);

        switch(btnClicked){
            case 1:
                fitCoins += 4;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 4 fit coins!");
                break;
            case 2:
                fitCoins += 20;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 20 fit coins!");
                break;
            case 3:
                fitCoins += 65;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 65 fit coins!");
                break;
            case 4:
                reference.child(userID).child("items").child("magoroGreen").setValue(true);
                collectText.setText("Congratulations! You won the Green Magoro!");
                break;
            case 5:
                fitCoins += 5;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 5 fit coins!");
                break;
            case 6:
                fitCoins += 30;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 30 fit coins!");
                break;
            case 7:
                fitCoins += 70;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 70 fit coins!");
                break;
            case 8:
                reference.child(userID).child("items").child("livingRoom2").setValue(true);
                collectText.setText("Congratulations! You won a new LivingRoom!");
                break;
            case 9:
                fitCoins += 3;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 3 fit coins!");
                break;
            case 10:
                fitCoins += 15;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 15 fit coins!");
                break;
            case 11:
                fitCoins += 32;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                collectText.setText("Congratulations! You won 32 fit coins!");
                break;
            case 12:
                reference.child(userID).child("items").child("bedRoom2").setValue(true);
                collectText.setText("Congratulations! You won a new BedRoom!");
                break;
            case 13:
                coins += 15;
                reference.child(userID).child("settings").child("coins").setValue(coins);
                collectText.setText("Congratulations! You won 15 game coins!");
                break;
            case 14:
                coins += 40;
                reference.child(userID).child("settings").child("coins").setValue(coins);
                collectText.setText("Congratulations! You won 40 game coins!");
                break;
            case 15:
                coins += 90;
                reference.child(userID).child("settings").child("coins").setValue(coins);
                collectText.setText("Congratulations! You won 90 game coins!");
                break;
            case 16:
                reference.child(userID).child("items").child("kitchen2").setValue(true);
                collectText.setText("Congratulations! You won a new Kitchen!");
                break;
            case 17:
                reference.child(userID).child("items").child("livingRoom3").setValue(true);
                collectText.setText("Congratulations! You won a new LivingRoom!");
                break;
            case 18:
                reference.child(userID).child("items").child("magoroRainbow").setValue(true);
                collectText.setText("Congratulations! You won the special Rainbow Magoro!");
                break;
        }

        collect.setOnClickListener(v -> {
            popupWindow.dismiss();
            AchievementsFragment fragment1 = new AchievementsFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        dimBehind(popupWindow);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
}
