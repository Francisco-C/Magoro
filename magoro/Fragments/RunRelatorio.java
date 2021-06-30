package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magoro.DBobjects.RunningInfo;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RunRelatorio extends Fragment {

    public RunRelatorio() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View rootView = inflater.inflate(R.layout.fragment_run_relatorio, container, false);

        TextView textTime = rootView.findViewById(R.id.textView14);
        TextView textDistance = rootView.findViewById(R.id.textView10);
        TextView textSpeed = rootView.findViewById(R.id.textView15);
        TextView textCalories = rootView.findViewById(R.id.textView16);
        ImageView runImg = rootView.findViewById(R.id.runImg);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("relatorio", Context.MODE_PRIVATE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).child("runningInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RunningInfo runningInfo = snapshot.getValue(RunningInfo.class);

                if(runningInfo != null){
                    float distance = runningInfo.distance;
                    String time = runningInfo.time;
                    float speed = runningInfo.speed;
                    float calories = runningInfo.calories;
                    String route = runningInfo.route;

                    textDistance.setText(distance + " Km");
                    textTime.setText(time);
                    textSpeed.setText(speed + " Km/h");
                    textCalories.setText(calories + " Cal");

                    byte[] decoded = Base64.decode(route, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                    runImg.setImageBitmap(bitmap);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton closeBtn = rootView.findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(v -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lastLoc","0,0");
            editor.apply();

            RunFragment fragment1 = new RunFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        return rootView;
    }
}