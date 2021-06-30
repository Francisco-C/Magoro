package com.example.magoro.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.magoro.Activities.Flappy.GameTwoMainActivity;
import com.example.magoro.Activities.Game2048.GameActivity;
import com.example.magoro.Activities.Jumpy.GameOneStartActivity;
import com.example.magoro.Activities.TicTacToe.TicTacToeActivity;
import com.example.magoro.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class GameFragment extends Fragment {

    Button GameOne, GameTwo, GameThree, GameFour;

    public GameFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View vi = inflater.inflate(R.layout.fragment_game, container, false);

        GameOne = vi.findViewById(R.id.firstgame);
        GameTwo = vi.findViewById(R.id.secondgame);
        GameThree = vi.findViewById(R.id.thirdgame);
        GameFour = vi.findViewById(R.id.fourthgame);

        GameOne.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), GameOneStartActivity.class);
            startActivity(intent);
        });

        GameTwo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GameTwoMainActivity.class);
            startActivity(intent);
        });

        GameThree.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TicTacToeActivity.class);
            startActivity(intent);
        });

        GameFour.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            startActivity(intent);
        });

        return  vi;
    }

}