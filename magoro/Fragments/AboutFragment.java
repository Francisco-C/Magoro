package com.example.magoro.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.R;

public class AboutFragment extends Fragment {

    public AboutFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        MainActivity.menu.setVisibility(View.GONE);

        ImageView close = view.findViewById(R.id.closeAbt);
        close.setOnClickListener(v -> {
            SettingsFragment fragment1 = new SettingsFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        return view;
    }
}
