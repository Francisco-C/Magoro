package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.magoro.DBobjects.DefaultSettings;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SleepFragment extends Fragment {

    private ImageButton sleep;
    private long time_asleep;
    private String userID;
    private DatabaseReference reference;
    private int sleepState;
    private CardView loading;
    private ImageView magoroWake, magoroSleep;
    private RelativeLayout layout;
    private AnimationDrawable sleeping, awake;

    public SleepFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View rootView = inflater.inflate(R.layout.fragment_sleep, container, false);

        sleep = rootView.findViewById(R.id.sleepBtn);
        loading = rootView.findViewById(R.id.loadingSleepPage);
        magoroWake = rootView.findViewById(R.id.notSleep);
        magoroSleep = rootView.findViewById(R.id.sleeping);
        layout = rootView.findViewById(R.id.bedroom);
        
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DefaultSettings defaultSettings = snapshot.child("settings").getValue(DefaultSettings.class);
                if(defaultSettings != null){
                    sleepState = defaultSettings.sleepState;
                    String defaultMagoro = snapshot.child("items").child("defaultMagoro").getValue(String.class);
                    
                    int defaultBedroom = snapshot.child("items").child("defaultBedroom").getValue(int.class);

                    if(defaultBedroom == 1){
                        layout.setBackgroundResource(R.drawable.bed31);
                    }else{
                        layout.setBackgroundResource(R.drawable.bed61);
                    }

                    if(sleepState == 1){
                        sleep.setBackgroundResource(R.drawable.moone);
                        magoroWake.setVisibility(View.GONE);
                        magoroSleep.setVisibility(View.VISIBLE);
                        
                        switch(defaultMagoro){
                            case "red":
                                magoroSleep.setImageResource(R.drawable.sleep1red);
                                magoroSleep.setImageResource(R.drawable.red_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                            case "orange":
                                magoroSleep.setImageResource(R.drawable.sleep1orange);
                                magoroSleep.setImageResource(R.drawable.orange_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                            case "blue":
                                magoroSleep.setImageResource(R.drawable.sleep1blue);
                                magoroSleep.setImageResource(R.drawable.blue_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                            case "purple":
                                magoroSleep.setImageResource(R.drawable.sleep1roxo);
                                magoroSleep.setImageResource(R.drawable.roxo_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                            case "cyan":
                                magoroSleep.setImageResource(R.drawable.sleep1cian);
                                magoroSleep.setImageResource(R.drawable.cian_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                            case "green":
                                magoroSleep.setImageResource(R.drawable.sleep1green);
                                magoroSleep.setImageResource(R.drawable.green_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                            case "color":
                                magoroSleep.setImageResource(R.drawable.sleep1color);
                                magoroSleep.setImageResource(R.drawable.color_sleep);
                                sleeping = (AnimationDrawable) magoroSleep.getDrawable();
                                sleeping.start();
                                break;
                        }
                    }
                    else{
                        sleep.setBackgroundResource(R.drawable.sunny);
                        magoroWake.setVisibility(View.GONE);
                        magoroWake.setVisibility(View.VISIBLE);

                        switch(defaultMagoro){
                            case "red":
                                magoroWake.setImageResource(R.drawable.eye1red);
                                magoroWake.setImageResource(R.drawable.red_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                            case "orange":
                                magoroWake.setImageResource(R.drawable.eye1orange);
                                magoroWake.setImageResource(R.drawable.orange_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                            case "blue":
                                magoroWake.setImageResource(R.drawable.eye1blue);
                                magoroWake.setImageResource(R.drawable.blue_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                            case "purple":
                                magoroWake.setImageResource(R.drawable.eye1roxo);
                                magoroWake.setImageResource(R.drawable.roxo_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                            case "cyan":
                                magoroWake.setImageResource(R.drawable.eye1cian);
                                magoroWake.setImageResource(R.drawable.cian_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                            case "green":
                                magoroWake.setImageResource(R.drawable.eye1green);
                                magoroWake.setImageResource(R.drawable.green_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                            case "color":
                                magoroWake.setImageResource(R.drawable.eye1color);
                                magoroWake.setImageResource(R.drawable.color_blink);
                                awake = (AnimationDrawable) magoroWake.getDrawable();
                                awake.start();
                                break;
                        }
                    }
                }
                
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sleep.setOnClickListener(v -> {
            if (sleepState == 0) {
                sleep.setBackgroundResource(R.drawable.moone);
                time_asleep = System.currentTimeMillis();
                int btnState = 1;
                reference.child(userID).child("settings").child("sleepTime").setValue(time_asleep);
                reference.child(userID).child("settings").child("sleepState").setValue(btnState);
                Fragment fragment = new SleepFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.sleep_fragment, fragment).commit();
            } else {
                sleep.setBackgroundResource(R.drawable.sunny);
                int btnState = 0;
                reference.child(userID).child("settings").child("sleepState").setValue(btnState);
                showPopupWindowSleep(v);
            }
        });

        return rootView;
    }

    public void showPopupWindowSleep(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_sleep, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button sleepOkay = popupView.findViewById(R.id.sleepOkay);

        TextView textBad1 = popupView.findViewById(R.id.TextBad1);
        TextView textBad2 = popupView.findViewById(R.id.TextBad2);
        TextView sleepTimeText = popupView.findViewById(R.id.sleepTime);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long sleepTime = snapshot.child("settings").child("sleepTime").getValue(long.class);
                int age = snapshot.child("userInfo").child("age").getValue(int.class);
                int fitCoins = snapshot.child("settings").child("fitCoins").getValue(int.class);
                int coins = 0;
                int sleep2 = snapshot.child("achievements").child("sleep2").getValue(int.class);
                int sleep10 = snapshot.child("achievements").child("sleep10").getValue(int.class);
                int sleep30 = snapshot.child("achievements").child("sleep30").getValue(int.class);
                int sleep100 = snapshot.child("achievements").child("sleep100").getValue(int.class);
                boolean sleptGood = false;

                long wake_up = System.currentTimeMillis();
                time_asleep = wake_up - sleepTime;
                long minute = (time_asleep / (1000 * 60)) % 60;
                long hour = (time_asleep / (1000 * 60 * 60)) % 24;
                @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", hour, minute);
                String message = "";
                String message2 = "";

                String howMuch = "Tonight you slept "+time+" hours";

                if(age > 0 && age <= 12 ){
                    if(hour >= 9 && hour <= 12){
                        coins = 3;
                        message = "You are making a healthy sleep routine";
                        message2 = "Congratulations you won "+coins+" coins";
                        sleptGood = true;
                    }
                    else{
                        coins = 0;
                        message = "You aren't making a healthy sleep routine";
                        message2 = "You should sleep between 9 to 12 hours";
                    }
                }
                else if(age > 12 && age <= 18){
                    if(hour >= 8 && hour <= 10){
                        coins = 3;
                        message = "You are making a healthy sleep routine";
                        message2 = "Congratulations you won "+coins+" coins";
                        sleptGood = true;
                    }
                    else{
                        coins = 0;
                        message = "You aren't making a healthy sleep routine";
                        message2 = "You should sleep between 8 to 10 hours";
                    }
                }
                else if(age > 18 && age <= 64){
                    if(hour >= 7 && hour <= 9){
                        coins = 3;
                        message = "You are making a healthy sleep routine";
                        message2 = "Congratulations you won "+coins+" coins";
                        sleptGood = true;
                    }
                    else{
                        coins = 0;
                        message = "You aren't making a healthy sleep routine";
                        message2 = "You should sleep between 7 to 9 hours";
                    }
                }
                else if(age > 64){
                    if(hour >= 7 && hour <= 8){
                        coins = 3;
                        message = "You are making a healthy sleep routine";
                        message2 = "Congratulations you won "+coins+" coins";
                        sleptGood = true;
                    }
                    else{
                        coins = 0;
                        message = "You aren't making a healthy sleep routine";
                        message2 = "You should sleep between 7 to 8 hours";
                    }
                }

                if(sleptGood){
                    sleep2 += 1;
                    sleep10 += 1;
                    sleep30 += 1;
                    sleep100 += 1;
                    reference.child(userID).child("achievements").child("sleep2").setValue(sleep2);
                    reference.child(userID).child("achievements").child("sleep10").setValue(sleep10);
                    reference.child(userID).child("achievements").child("sleep30").setValue(sleep30);
                    reference.child(userID).child("achievements").child("sleep100").setValue(sleep100);
                }

                fitCoins += coins;
                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                textBad1.setVisibility(View.VISIBLE);
                textBad2.setVisibility(View.VISIBLE);
                textBad1.setText(message);
                textBad2.setText(message2);
                sleepTimeText.setText(howMuch);

                assert getParentFragment() != null;
                ((HomeFragment) getParentFragment()).homeFitCoin.setText(String.valueOf(fitCoins));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sleepOkay.setOnClickListener(v -> {
            Fragment fragment = new SleepFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.sleep_fragment, fragment).commit();
            popupWindow.dismiss();
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