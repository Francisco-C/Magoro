package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CarinhoFragment extends Fragment {

    private RelativeLayout layout;
    private ImageView magoro;
    private CardView loading;
    private AnimationDrawable idle, hello;
    private GestureDetector mDetector;
    private int anim;

    public CarinhoFragment() {

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_carinho, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        layout = view.findViewById(R.id.roomBack);
        magoro = view.findViewById(R.id.homeMagoro);
        loading = view.findViewById(R.id.loadingCarinhoPage);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String defaultMagoro = snapshot.child("items").child("defaultMagoro").getValue(String.class);
                int sleepState = snapshot.child("settings").child("sleepState").getValue(int.class);
                int defaultroom = snapshot.child("items").child("defaultLivingRoom").getValue(int.class);

                if(defaultroom == 1){
                    layout.setBackgroundResource(R.drawable.sala4);
                }else if(defaultroom == 2){
                    layout.setBackgroundResource(R.drawable.sala6);
                }else if(defaultroom == 3){
                    layout.setBackgroundResource(R.drawable.sala8);
                }

                if(sleepState == 1){
                    magoro.setVisibility(View.GONE);
                }
                else{
                    switch(defaultMagoro){
                        case "red":
                            magoro.setImageResource(R.drawable.idle1red);
                            magoro.setImageResource(R.drawable.red_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 1;
                            break;
                        case "orange":
                            magoro.setImageResource(R.drawable.idle1orange);
                            magoro.setImageResource(R.drawable.orange_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 2;
                            break;
                        case "blue":
                            magoro.setImageResource(R.drawable.idle1blue);
                            magoro.setImageResource(R.drawable.blue_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 3;
                            break;
                        case "purple":
                            magoro.setImageResource(R.drawable.idle1roxo);
                            magoro.setImageResource(R.drawable.roxo_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 4;
                            break;
                        case "cyan":
                            magoro.setImageResource(R.drawable.idle1cian);
                            magoro.setImageResource(R.drawable.cian_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 5;
                            break;
                        case "green":
                            magoro.setImageResource(R.drawable.idle1green);
                            magoro.setImageResource(R.drawable.green_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 6;
                            break;
                        case "color":
                            magoro.setImageResource(R.drawable.idle1color);
                            magoro.setImageResource(R.drawable.color_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                            anim = 7;
                            break;
                    }
                }

                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTapEvent(MotionEvent e)
            {
                Handler handler;
                switch(anim){
                    case 1:
                        magoro.setImageResource(R.drawable.red_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.red_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                    case 2:
                        magoro.setImageResource(R.drawable.orange_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.orange_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                    case 3:
                        magoro.setImageResource(R.drawable.blue_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.blue_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                    case 4:
                        magoro.setImageResource(R.drawable.roxo_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.roxo_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                    case 5:
                        magoro.setImageResource(R.drawable.cian_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.cian_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                    case 6:
                        magoro.setImageResource(R.drawable.green_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.green_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                    case 7:
                        magoro.setImageResource(R.drawable.color_hello);
                        hello = (AnimationDrawable) magoro.getDrawable();
                        hello.start();
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            magoro.setImageResource(R.drawable.color_idle);
                            idle = (AnimationDrawable) magoro.getDrawable();
                            idle.start();
                        }, 1000);
                        break;
                }

                return true;
            }
        });

        magoro.setOnTouchListener((v, event) -> {
            mDetector.onTouchEvent(event);
            return true;
        });

        return view;
    }
}