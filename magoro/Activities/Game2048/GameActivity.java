package com.example.magoro.Activities.Game2048;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends Activity implements View.OnTouchListener {

    private MatrixView matrixView;
    private ScoreBoxView currentScore;
    private ScoreBoxView bestScore;
    private Button buttonNewGame, buttonExitGame;
    private SoundManager soundManager;
    private SwipeListener swipeListener;
    private LinearLayout linearLayoutOutermost;
    private Animation slideUpAnimation;
    private TextView textViewLucky;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    String userID = user.getUid();

    @Override
    protected void onResume() {

        reference.child(userID).child("gamesStatus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int score = snapshot.child("Score2048").getValue(int.class);
                bestScore.setScore(score);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        soundManager = new SoundManager(getApplicationContext());

        new CheckInternet(3000, 1000).start();

        textViewLucky = (TextView) findViewById(R.id.main_textview_lucky);
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.all_the_way_up);
        slideUpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textViewLucky.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        currentScore = (ScoreBoxView) findViewById(R.id.main_scoreboxview_current);
        bestScore = (ScoreBoxView) findViewById(R.id.main_scoreboxview_best);
        matrixView = (MatrixView) findViewById(R.id.main_matrixview);
        matrixView.setMoveListener(new MoveListener() {
            @Override
            public void onMove(int score, boolean gameOver, boolean newSquare) {
                if (gameOver) {
                    displayGameOverDialog();
                } else {
                    soundManager.playSliding();
                    if (!newSquare) {
                        textViewLucky.setVisibility(View.VISIBLE);
                        textViewLucky.startAnimation(slideUpAnimation);
                    }
                    if (score > 0) {
                        currentScore.addScore(score);
                        if (currentScore.getScore() > bestScore.getScore()) {
                            bestScore.setScore(currentScore.getScore());
                            GamePreferences.saveBestScore(bestScore.getScore());
                        }
                        if (score >= 2048) {
                            displayCongratsDialog();
                        }
                    }
                }
            }
        });

        buttonNewGame = findViewById(R.id.main_button_new_game);
        buttonNewGame.setOnClickListener(v -> onNewGameClick());

        buttonExitGame = findViewById(R.id.main_button_Menu_game);
        buttonExitGame.setOnClickListener(v -> {

            Intent a = new Intent(getApplicationContext(),MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(a);
        });

        linearLayoutOutermost = (LinearLayout) findViewById(R.id.main_linearlayout_outermost);
        linearLayoutOutermost.setOnTouchListener(this);
        swipeListener = new SwipeListener(this, new Swiper() {
            @Override
            public void onSwipeLeft() {
                matrixView.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                matrixView.onSwipeRight();
            }

            @Override
            public void onSwipeUp() {
                matrixView.onSwipeUp();
            }

            @Override
            public void onSwipeDown() {
                matrixView.onSwipeDown();
            }
        });
    }

    private void onNewGameClick() {
        matrixView.reset();

        currentScore.resetScore();
    }

    private void displayGameOverDialog() {
        final GameDialog d = new GameDialog(this, "Oops, you lost! Try again now?", "Cancel", "New Game");
        d.setOnGameDialogClickListener(new OnGameDialogClickListener() {
            @Override
            public void onLeftClick() {
                d.dismiss();
                reference.child(userID).child("settings").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int gameCoins = snapshot.child("coins").getValue(int.class);
                        int prize = (int) currentScore.getScore() / 400;
                        View v = getWindow().getDecorView().getRootView();

                        reference.child(userID).child("settings").child("coins").setValue(gameCoins+prize);
                        showPopupWindowPrize(v, prize, currentScore.getScore());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onRightClick() {
                d.dismiss();

                reference.child(userID).child("settings").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int gameCoins = snapshot.child("coins").getValue(int.class);
                        int prize = (int) currentScore.getScore() / 400;
                        View v = getWindow().getDecorView().getRootView();

                        reference.child(userID).child("settings").child("coins").setValue(gameCoins+prize);
                        showPopupWindowPrize(v, prize, currentScore.getScore());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                onNewGameClick();
            }
        });
        d.show();
    }

    private void displayCongratsDialog() {
        final GameDialog d = new GameDialog(this, "Wow, you win! Continue to get a better > 2048?", "New Game", "Continue");
        d.setOnGameDialogClickListener(new OnGameDialogClickListener() {
            @Override
            public void onLeftClick() {
                d.dismiss();

                reference.child(userID).child("settings").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int gameCoins = snapshot.child("coins").getValue(int.class);
                        int prize = (int) currentScore.getScore() / 400;
                        View v = getWindow().getDecorView().getRootView();

                        reference.child(userID).child("settings").child("coins").setValue(gameCoins+prize);
                        showPopupWindowPrize(v, prize, currentScore.getScore());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                onNewGameClick();
            }

            @Override
            public void onRightClick() {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return swipeListener.getGestureDetector().onTouchEvent(event);
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
