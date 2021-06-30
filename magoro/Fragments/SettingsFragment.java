package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magoro.Activities.LoginActivity;
import com.example.magoro.Activities.MainActivity;
import com.example.magoro.DBobjects.UserInfo;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private TextView usernameText, weightText, heightText, ageText;
    private FirebaseAuth mAuth;
    private String userID;
    private DatabaseReference reference;
    CardView loading;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        usernameText = view.findViewById(R.id.username);
        weightText = view.findViewById(R.id.weight);
        heightText = view.findViewById(R.id.height);
        ageText = view.findViewById(R.id.age);
        TextView achievements = view.findViewById(R.id.achievements);
        TextView about = view.findViewById(R.id.about);
        TextView changePass = view.findViewById(R.id.changePass);
        Button signOut = view.findViewById(R.id.signOut);
        ImageButton editWeight = view.findViewById(R.id.editWeight);
        ImageButton editHeight = view.findViewById(R.id.editHeight);
        ImageButton editAge = view.findViewById(R.id.editAge);
        loading = view.findViewById(R.id.loadingSettingsPage);

        MainActivity.menu.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).child("userInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                if(userInfo != null){
                    String username = userInfo.username;
                    int age = userInfo.age;
                    int weight = userInfo.weight;
                    int height = userInfo.height;

                    usernameText.setText(username);
                    weightText.setText(weight + " kg");
                    heightText.setText(height + " m");
                    ageText.setText(String.valueOf(age));

                    loading.setVisibility(View.INVISIBLE);
                    MainActivity.menu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editWeight.setOnClickListener(this::showPopupWindowEditWeight);
        editHeight.setOnClickListener(this::showPopupWindowEditHeight);
        editAge.setOnClickListener(this::showPopupWindowEditAge);

        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent loginScreen = new Intent(getActivity(), LoginActivity.class);
            loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginScreen);
        });

        changePass.setOnClickListener(this::showPopupWindowChange);

        about.setOnClickListener(v -> {
            AboutFragment fragment1 = new AboutFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        achievements.setOnClickListener(v -> {
            AchievementsFragment fragment1 = new AchievementsFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        return view;
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

    public void showPopupWindowChange(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_reset_password, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button close = popupView.findViewById(R.id.passwordChange);

        close.setOnClickListener(v -> reference.child(userID).child("userInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                if(userInfo != null){
                    String mail = userInfo.email;
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(task -> {

                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Check your email to reset the password!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                        }
                        popupWindow.dismiss();
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));

        dimBehind(popupWindow);
    }

    public void showPopupWindowEditAge(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_idade, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText writeAge = popupView.findViewById(R.id.writeAge);
        Button saveAge = popupView.findViewById(R.id.saveAge);
        Button cancelAge = popupView.findViewById(R.id.cancelAge);

        saveAge.setOnClickListener(v -> {

            String age = writeAge.getText().toString().trim();
            if(age.isEmpty()){
                writeAge.setError("Age is required!");
                writeAge.requestFocus();
                return;
            }
            if(!TextUtils.isDigitsOnly(age)){
                writeAge.setError("Please provide a valid age!");
                writeAge.requestFocus();
                return;
            }
            if(Integer.parseInt(age) < 1 || Integer.parseInt(age) > 130){
                writeAge.setError("Please provide a valid age!");
                writeAge.requestFocus();
                return;
            }

            reference.child(userID).child("userInfo").child("age").setValue(Integer.parseInt(age)).addOnCompleteListener(task ->{

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Edited successfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                }
                popupWindow.dismiss();
                Fragment frag = new SettingsFragment();
                FragmentManager fm= getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
            });
        });

        cancelAge.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }

    public void showPopupWindowEditWeight(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_peso, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText writeWeight = popupView.findViewById(R.id.writeWeight);
        Button saveWeight = popupView.findViewById(R.id.saveWeight);
        Button cancelWeight = popupView.findViewById(R.id.cancelWeight);

        saveWeight.setOnClickListener(v -> {

            String weight = writeWeight.getText().toString().trim();
            if(weight.isEmpty()){
                writeWeight.setError("Weight is required!");
                writeWeight.requestFocus();
                return;
            }
            if(!TextUtils.isDigitsOnly(weight)){
                writeWeight.setError("Please provide a valid weight!");
                writeWeight.requestFocus();
                return;
            }
            if(Integer.parseInt(weight) < 1 || Integer.parseInt(weight) > 650){
                writeWeight.setError("Please provide a valid weight!");
                writeWeight.requestFocus();
                return;
            }

            reference.child(userID).child("userInfo").child("weight").setValue(Integer.parseInt(weight)).addOnCompleteListener(task ->{

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Edited successfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                }
                popupWindow.dismiss();
                Fragment frag = new SettingsFragment();
                FragmentManager fm= getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
            });

        });

        cancelWeight.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }

    public void showPopupWindowEditHeight(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_altura, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int heights = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, heights, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText writeHeight = popupView.findViewById(R.id.writeHeight);
        Button saveHeight = popupView.findViewById(R.id.saveHeight);
        Button cancelHeight = popupView.findViewById(R.id.cancelHeight);

        saveHeight.setOnClickListener(v -> {

            String height = writeHeight.getText().toString().trim();
            if(height.isEmpty()){
                writeHeight.setError("Height is required!");
                writeHeight.requestFocus();
                return;
            }
            if(!TextUtils.isDigitsOnly(height)){
                writeHeight.setError("Please provide a valid height!");
                writeHeight.requestFocus();
                return;
            }
            if(Integer.parseInt(height) < 1 || Integer.parseInt(height) > 275){
                writeHeight.setError("Please provide a valid height!");
                writeHeight.requestFocus();
                return;
            }

            reference.child(userID).child("userInfo").child("height").setValue(Integer.parseInt(height)).addOnCompleteListener(task ->{

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Edited successfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                }
                popupWindow.dismiss();
                Fragment frag = new SettingsFragment();
                FragmentManager fm= getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
            });

        });

        cancelHeight.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }
}