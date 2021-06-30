package com.example.magoro.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.DBobjects.DefaultSettings;
import com.example.magoro.R;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    public int j;
    private CountDownTimer hungerIncrease, fitnessIncrease;
    public ProgressBar hungerBar;
    public ProgressBar fitnessBar;
    private int coin, fitCoin, HungerProgress, FitnessProgress;
    private long Hunger_lastU, Hunger_updateTimer, Fitness_lastU, Fitness_updateTimer;
    TextView homeCoins, homeFitCoin;
    private CardView loading;
    private String userID;
    private DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public HomeFragment() {

    }

    @Override
    public void onDestroyView() {
        hungerIncrease.cancel();
        fitnessIncrease.cancel();
        reference.child(userID).child("settings").child("hungerBar").setValue(hungerBar.getProgress());
        reference.child(userID).child("settings").child("happinessBar").setValue(fitnessBar.getProgress());
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        hungerBar = rootView.findViewById(R.id.hungerBar);
        fitnessBar = rootView.findViewById(R.id.fitnessBar);
        homeCoins = rootView.findViewById(R.id.Coin);
        homeFitCoin = rootView.findViewById(R.id.fitCoin);
        ImageButton homeMainB = rootView.findViewById(R.id.carinho);
        ImageButton foodB = rootView.findViewById(R.id.food);
        ImageButton sleepB = rootView.findViewById(R.id.sleep);
        ImageButton backgroundB = rootView.findViewById(R.id.backgroundBtn);
        loading = rootView.findViewById(R.id.loadingHomePage);

        MainActivity.menu.setVisibility(View.INVISIBLE);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DefaultSettings settings = snapshot.child("settings").getValue(DefaultSettings.class);
                int age = snapshot.child("userInfo").child("age").getValue(int.class);
                int height = snapshot.child("userInfo").child("height").getValue(int.class);
                int weight = snapshot.child("userInfo").child("weight").getValue(int.class);

                if(age == 0 || height == 0 || weight == 0){
                    showPopupWindowAlert(getView());
                }

                if(settings != null){
                    coin = settings.coins;
                    fitCoin = settings.fitCoins;
                    HungerProgress = settings.hungerBar;
                    FitnessProgress = settings.happinessBar;
                    Hunger_updateTimer = settings.hungerTimer;
                    Hunger_lastU = settings.hungerUpdate;
                    Fitness_updateTimer = settings.fitnessTimer;
                    Fitness_lastU = settings.fitnessUpdate;

                    homeCoins.setText(String.valueOf(coin));
                    homeFitCoin.setText(String.valueOf(fitCoin));

                    if(HungerProgress != 0) {
                        long Hunger_newU = System.currentTimeMillis() - Hunger_lastU;
                        int Hunger_newProgress = (int) (HungerProgress - (Hunger_newU / 600000));
                        if (Hunger_lastU == 0) {
                            hungerBar.setProgress(100);
                            reference.child(userID).child("settings").child("hungerBar").setValue(100);
                        }
                        else {
                            if(Hunger_newProgress < 0){
                                Hunger_newProgress = 0;
                            }
                            hungerBar.setProgress(Hunger_newProgress);
                            reference.child(userID).child("settings").child("hungerBar").setValue(Hunger_newProgress);
                        }
                    }
                    else{
                        hungerBar.setProgress(HungerProgress);
                        reference.child(userID).child("settings").child("hungerBar").setValue(HungerProgress);
                    }

                    hungerIncrease = new HungerCount(Hunger_updateTimer,1000).start();

                    if(FitnessProgress != 0) {
                        long Fitness_newU = System.currentTimeMillis() - Fitness_lastU;
                        int Fitness_newProgress = (int) (FitnessProgress - (Fitness_newU / 600000));
                        if (Fitness_lastU == 0) {
                            fitnessBar.setProgress(100);
                            reference.child(userID).child("settings").child("happinessBar").setValue(100);
                        }
                        else {
                            if(Fitness_newProgress < 0){
                                Fitness_newProgress = 0;
                            }
                            fitnessBar.setProgress(Fitness_newProgress);
                            reference.child(userID).child("settings").child("happinessBar").setValue(Fitness_newProgress);
                        }
                    }
                    else{
                        fitnessBar.setProgress(FitnessProgress);
                        reference.child(userID).child("settings").child("happinessBar").setValue(FitnessProgress);
                    }

                    fitnessIncrease = new FitnessCount(Fitness_updateTimer,1000).start();

                }

                loading.setVisibility(View.INVISIBLE);
                MainActivity.menu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homeMainB.setOnClickListener(v -> insertNestedFragment(1));

        foodB.setOnClickListener(v -> insertNestedFragment(2));

        sleepB.setOnClickListener(v -> insertNestedFragment(3));

        backgroundB.setOnClickListener(this::showPopupWindowStyle);

        insertNestedFragment(j);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            checkLocationPermission();
        }

        return rootView;
    }

    private void insertNestedFragment(int j) {
        if(j == 2){

            Fragment childFragment = new FoodFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.HFLayout, childFragment).commit();

        }
        else if(j == 3){
            Fragment childFragment = new SleepFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.HFLayout, childFragment).commit();

        }else
        {
            Fragment childFragment = new CarinhoFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.HFLayout, childFragment).commit();

        }

    }

    public class HungerCount extends CountDownTimer{

        public HungerCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long currentTime = System.currentTimeMillis();
            reference.child(userID).child("settings").child("hungerTimer").setValue(millisUntilFinished);
            reference.child(userID).child("settings").child("hungerUpdate").setValue(currentTime);
        }

        @Override
        public void onFinish() {


            int currentProgress = hungerBar.getProgress();
            currentProgress -= 1;
            hungerBar.setProgress(currentProgress);

            hungerIncrease = new HungerCount(600000,1000);
            hungerIncrease.start();
        }
    }

    public class FitnessCount extends CountDownTimer{

        public FitnessCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long currentTime = System.currentTimeMillis();
            reference.child(userID).child("settings").child("fitnessTimer").setValue(millisUntilFinished);
            reference.child(userID).child("settings").child("fitnessUpdate").setValue(currentTime);
        }

        @Override
        public void onFinish() {


            int currentProgress = fitnessBar.getProgress();
            currentProgress -= 1;
            fitnessBar.setProgress(currentProgress);

            fitnessIncrease = new FitnessCount(600000,1000);
            fitnessIncrease.start();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION ))
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
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

    public void showPopupWindowStyle(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_style, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        CardView loadingS = popupView.findViewById(R.id.loadingStylePage);

        ImageButton close = popupView.findViewById(R.id.closeStyleMenuBtn);

        Button redBtn = popupView.findViewById(R.id.redBtn);
        Button blueBtn = popupView.findViewById(R.id.blueBtn);
        Button orangeBtn = popupView.findViewById(R.id.orangeBtn);
        Button cianBtn = popupView.findViewById(R.id.cianBtn);
        Button purpleBtn = popupView.findViewById(R.id.purpleBtn);
        Button greenBtn = popupView.findViewById(R.id.greenBtn);
        Button colorBtn = popupView.findViewById(R.id.colorBtn);

        Button room1Btn = popupView.findViewById(R.id.room1Btn);
        Button room2Btn = popupView.findViewById(R.id.room2Btn);
        Button room3Btn = popupView.findViewById(R.id.room3Btn);
        Button bed1Btn = popupView.findViewById(R.id.bed1btn);
        Button bed2Btn = popupView.findViewById(R.id.bed2Btn);
        Button kitchen1Btn = popupView.findViewById(R.id.kit1Btn);
        Button kitchen2Btn = popupView.findViewById(R.id.kit2Btn);

        ImageView blueLock = popupView.findViewById(R.id.blueLock);
        ImageView orangeLock = popupView.findViewById(R.id.orangeLock);
        ImageView cianLock = popupView.findViewById(R.id.cianLock);
        ImageView purpleLock = popupView.findViewById(R.id.purpleLock);
        ImageView greenLock = popupView.findViewById(R.id.greenLock);
        ImageView colorLock = popupView.findViewById(R.id.colorLock);
        ImageView room2Lock = popupView.findViewById(R.id.room2Lock);
        ImageView room3Lock = popupView.findViewById(R.id.room3Lock);
        ImageView bed2Lock = popupView.findViewById(R.id.bed2Lock);
        ImageView kitchen2Lock = popupView.findViewById(R.id.kit2Lock);

        reference.child(userID).child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int defaultRoom = snapshot.child("defaultLivingRoom").getValue(int.class);
                int defaultBed = snapshot.child("defaultBedroom").getValue(int.class);
                int defaultKit = snapshot.child("defaultKitchen").getValue(int.class);
                String defaultMagoro = snapshot.child("defaultMagoro").getValue(String.class);
                boolean livingRoom2 = snapshot.child("livingRoom2").getValue(boolean.class);
                boolean livingRoom3 = snapshot.child("livingRoom3").getValue(boolean.class);
                boolean kitchen2 = snapshot.child("kitchen2").getValue(boolean.class);
                boolean bedRoom2 = snapshot.child("bedRoom2").getValue(boolean.class);
                boolean magoroDarkBlue = snapshot.child("magoroDarkBlue").getValue(boolean.class);
                boolean magoroGreen = snapshot.child("magoroGreen").getValue(boolean.class);
                boolean magoroLightBlue = snapshot.child("magoroLightBlue").getValue(boolean.class);
                boolean magoroOrange = snapshot.child("magoroOrange").getValue(boolean.class);
                boolean magoroPurple = snapshot.child("magoroPurple").getValue(boolean.class);
                boolean magoroRainbow = snapshot.child("magoroRainbow").getValue(boolean.class);

                switch(defaultMagoro){
                    case "red":
                        redBtn.setVisibility(View.GONE);
                        if(magoroDarkBlue){
                            blueBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroGreen){
                            greenBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroLightBlue){
                            cianBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroOrange){
                            orangeBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroPurple){
                            purpleBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroRainbow){
                            colorBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "orange":
                        orangeBtn.setVisibility(View.GONE);
                        redBtn.setVisibility(View.VISIBLE);
                        if(magoroDarkBlue){
                            blueBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroGreen){
                            greenBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroLightBlue){
                            cianBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroPurple){
                            purpleBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroRainbow){
                            colorBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "blue":
                        blueBtn.setVisibility(View.GONE);
                        redBtn.setVisibility(View.VISIBLE);
                        if(magoroGreen){
                            greenBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroLightBlue){
                            cianBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroOrange){
                            orangeBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroPurple){
                            purpleBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroRainbow){
                            colorBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "purple":
                        purpleBtn.setVisibility(View.GONE);
                        redBtn.setVisibility(View.VISIBLE);
                        if(magoroDarkBlue){
                            blueBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroGreen){
                            greenBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroLightBlue){
                            cianBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroOrange){
                            orangeBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroRainbow){
                            colorBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "cyan":
                        cianBtn.setVisibility(View.GONE);
                        redBtn.setVisibility(View.VISIBLE);
                        if(magoroDarkBlue){
                            blueBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroGreen){
                            greenBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroOrange){
                            orangeBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroPurple){
                            purpleBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroRainbow){
                            colorBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "green":
                        greenBtn.setVisibility(View.GONE);
                        redBtn.setVisibility(View.VISIBLE);
                        if(magoroDarkBlue){
                            blueBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroLightBlue){
                            cianBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroOrange){
                            orangeBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroPurple){
                            purpleBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroRainbow){
                            colorBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "color":
                        colorBtn.setVisibility(View.GONE);
                        redBtn.setVisibility(View.VISIBLE);
                        if(magoroDarkBlue){
                            blueBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroGreen){
                            greenBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroLightBlue){
                            cianBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroOrange){
                            orangeBtn.setVisibility(View.VISIBLE);
                        }
                        if(magoroPurple){
                            purpleBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                }

                switch(defaultRoom){
                    case 1:
                        room1Btn.setVisibility(View.GONE);
                        if(livingRoom2){
                            room2Btn.setVisibility(View.VISIBLE);
                        }
                        if(livingRoom3){
                            room3Btn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        room1Btn.setVisibility(View.VISIBLE);
                        if(livingRoom3){
                            room3Btn.setVisibility(View.VISIBLE);
                        }
                        room2Btn.setVisibility(View.GONE);
                        break;
                    case 3:
                        room1Btn.setVisibility(View.VISIBLE);
                        if(livingRoom2){
                            room2Btn.setVisibility(View.VISIBLE);
                        }
                        room3Btn.setVisibility(View.GONE);
                        break;
                }

                switch(defaultBed){
                    case 1:
                        bed1Btn.setVisibility(View.GONE);
                        if(bedRoom2){
                            bed2Btn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        bed1Btn.setVisibility(View.VISIBLE);
                        bed2Btn.setVisibility(View.GONE);
                        break;
                }

                switch(defaultKit){
                    case 1:
                        kitchen1Btn.setVisibility(View.GONE);
                        if(kitchen2){
                            kitchen2Btn.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        kitchen1Btn.setVisibility(View.VISIBLE);
                        kitchen2Btn.setVisibility(View.GONE);
                        break;
                }

                if(livingRoom2){
                    room2Lock.setVisibility(View.GONE);
                }
                if(livingRoom3){
                    room3Lock.setVisibility(View.GONE);
                }
                if(bedRoom2){
                    bed2Lock.setVisibility(View.GONE);
                }
                if(kitchen2){
                    kitchen2Lock.setVisibility(View.GONE);
                }
                if(magoroDarkBlue){
                    blueLock.setVisibility(View.GONE);
                }
                if(magoroGreen){
                    greenLock.setVisibility(View.GONE);
                }
                if(magoroLightBlue){
                    cianLock.setVisibility(View.GONE);
                }
                if(magoroOrange){
                    orangeLock.setVisibility(View.GONE);
                }
                if(magoroPurple){
                    purpleLock.setVisibility(View.GONE);
                }
                if(magoroRainbow){
                    colorLock.setVisibility(View.GONE);
                }

                loadingS.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        redBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("red");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        blueBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("blue");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        orangeBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("orange");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        cianBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("cyan");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        greenBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("green");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        purpleBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("purple");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        colorBtn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultMagoro").setValue("color");
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });

        room1Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultLivingRoom").setValue(1);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        room2Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultLivingRoom").setValue(2);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        room3Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultLivingRoom").setValue(3);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        bed1Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultBedroom").setValue(1);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        bed2Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultBedroom").setValue(2);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        kitchen1Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultKitchen").setValue(1);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });
        kitchen2Btn.setOnClickListener(v -> {
            reference.child(userID).child("items").child("defaultKitchen").setValue(2);
            popupWindow.dismiss();
            Fragment frag = new HomeFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
        });

        close.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }

    public void showPopupWindowAlert(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_alert, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button alertOK = popupView.findViewById(R.id.alertOK);

        alertOK.setOnClickListener(v -> {
            popupWindow.dismiss();

            Fragment frag = new SettingsFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
            ((MainActivity)getActivity()).MoveToSettings();
        });

        dimBehind(popupWindow);
    }
}