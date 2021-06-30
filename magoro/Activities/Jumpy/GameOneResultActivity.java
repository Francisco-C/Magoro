package com.example.magoro.Activities.Jumpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.Fragments.SettingsFragment;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameOneResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one_result);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new CheckInternet(3000, 1000).start();

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        TextView gamesPlayedLabel = (TextView) findViewById(R.id.gamesPlayedLabel);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int highScore = snapshot.child("gamesStatus").child("ScoreJumpy").getValue(int.class);
                int games = snapshot.child("gamesStatus").child("GamesJumpy").getValue(int.class);
                int gameCoins = snapshot.child("settings").child("coins").getValue(int.class);
                View v = getWindow().getDecorView().getRootView();

                int score = getIntent().getIntExtra("SCORE", 0);
                scoreLabel.setText(""+score);

                if (score > highScore) {
                    highScoreLabel.setText("High Score: " + score);
                    reference.child(userID).child("gamesStatus").child("ScoreJumpy").setValue(score);

                } else {
                    highScoreLabel.setText("High Score: "+ highScore);
                }

                gamesPlayedLabel.setText("Games Played: "+ (games+1));
                reference.child(userID).child("gamesStatus").child("GamesJumpy").setValue(games+1);

                int coins = (int) score/6;
                reference.child(userID).child("settings").child("coins").setValue(gameCoins+coins);
                showPopupWindowPrize(v, coins, score);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(), GameOneStartActivity.class));
        finish();
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

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    public void showPopupWindowPrize(final View view, int value, int score){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_game_prize, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView text = popupView.findViewById(R.id.RedeemGame);
        text.setText("You won "+value+" game coins for making "+score+" points");
        Button redeem = popupView.findViewById(R.id.RedeemGameBtn);

        redeem.setOnClickListener(v -> {
            popupWindow.dismiss();

        });

        dimBehind(popupWindow);
    }

    public class CheckInternet extends CountDownTimer {

        public CheckInternet(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {

            boolean internet = isNetworkAvailable();
            if(!internet){
                View v = getWindow().getDecorView().getRootView();
                showPopupWindowInternet(v);
            }
            else{
                new CheckInternet(3000, 1000).start();
            }
        }
    }

    private boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void showPopupWindowInternet(final View view) {

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_internet, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button tryAgain = popupView.findViewById(R.id.InternetBtn);

        tryAgain.setOnClickListener(v -> {
            boolean internet = isNetworkAvailable();
            popupWindow.dismiss();
            if (!internet) {
                View view1 = getWindow().getDecorView().getRootView();
                showPopupWindowInternet(view1);
            }
            else{
                new CheckInternet(3000, 1000).start();
            }
        });

        dimBehind(popupWindow);
    }


}