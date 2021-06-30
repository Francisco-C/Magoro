package com.example.magoro.Activities.Flappy;

import androidx.appcompat.app.AppCompatActivity;

import com.example.magoro.Activities.LoginActivity;
import com.example.magoro.Activities.MainActivity;
import com.example.magoro.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameTwoMainActivity extends AppCompatActivity {

    public static TextView game2Score, game2HighScore, game2finalScore, title, help;
    public static RelativeLayout game2gameOver;
    public static Button startBtn, exitBtn;
    public static boolean over = false;
    private GameTwoView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GameTwoConstants.SCREEN_WIDTH = displayMetrics.widthPixels;
        GameTwoConstants.SCREEN_HEIGHT = displayMetrics.heightPixels;

        setContentView(R.layout.activity_game_two_main);

        new CheckInternet(3000, 1000).start();

        game2Score = findViewById(R.id.game2Score);
        game2HighScore = findViewById(R.id.game2HighScore);
        game2finalScore = findViewById(R.id.game2finalScore);
        game2gameOver = findViewById(R.id.game2gameOver);
        title = findViewById(R.id.title_Flappy);
        help = findViewById(R.id.helpFlappy);
        exitBtn = findViewById(R.id.game2menuBtn);
        startBtn = findViewById(R.id.game2startBtn);
        gameView = findViewById(R.id.game2View);

        startBtn.setOnClickListener(v -> {
            gameView.setStart(true);
            game2Score.setVisibility(View.VISIBLE);
            startBtn.setVisibility(View.INVISIBLE);
            exitBtn.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
            help.setVisibility(View.INVISIBLE);
        });

        exitBtn.setOnClickListener(v -> {

            Intent a = new Intent(getApplicationContext(),MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(a);
        });

        game2gameOver.setOnClickListener(v -> {

            if(over){
                startBtn.setVisibility(View.VISIBLE);
                exitBtn.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                help.setVisibility(View.VISIBLE);
                game2gameOver.setVisibility(View.INVISIBLE);
                gameView.setStart(false);
                gameView.reset();
            }
        });
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