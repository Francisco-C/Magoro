package com.example.magoro.Activities.Jumpy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.Fragments.GameFragment;
import com.example.magoro.R;

public class GameOneStartActivity extends AppCompatActivity {

    ImageButton play;
    Button Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_start);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        play = findViewById(R.id.play);
        Close = findViewById(R.id.close);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GameOneMainActivity.class));
                finish();
            }
        });

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(getApplicationContext(),MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(a);
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case  KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return  super.dispatchKeyEvent(event);
    }
}