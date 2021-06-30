package com.example.magoro.Activities.Flappy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameTwoView extends View {

    private GameTwoBird bird;
    private Handler handler;
    private Runnable runnable;
    private ArrayList<GameTwoPipe> pipes;
    private int sumPipe, distance;
    private int score, highScore = 0;
    private boolean start;
    private int soundJump, soundScore, soundOver;
    private float volume = (float) 0.5;
    private boolean loadedSound, soundHit = false;
    private SoundPool soundPool;

    public GameTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initBird();
        initPipes();
        score = 0;
        start = false;

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
        this.soundPool = builder.build();

        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> loadedSound = true);

        soundJump = this.soundPool.load(context, R.raw.game2jump, 1);
        soundScore = this.soundPool.load(context, R.raw.game2score, 1);
        soundOver = this.soundPool.load(context, R.raw.game2lose, 1);
    }

    private void initBird(){

        bird = new GameTwoBird();
        bird.setWidths(100*GameTwoConstants.SCREEN_WIDTH/1080);
        bird.setHeights(100*GameTwoConstants.SCREEN_HEIGHT/1920);
        bird.setX(100*GameTwoConstants.SCREEN_WIDTH/1080);
        bird.setY(GameTwoConstants.SCREEN_HEIGHT/2-bird.getHeights()/2);

        ArrayList<Bitmap> arrayList = new ArrayList<>();
        arrayList.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrayList.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        bird.setAlb(arrayList);
    }

    private void initPipes(){

        sumPipe = 6;
        distance = 300 * GameTwoConstants.SCREEN_HEIGHT/1920;
        pipes = new ArrayList<>();

        for(int i = 0; i < sumPipe; i++){
            if(i < sumPipe/2){
                this.pipes.add(new GameTwoPipe(GameTwoConstants.SCREEN_WIDTH+i*((GameTwoConstants.SCREEN_WIDTH+200*GameTwoConstants.SCREEN_WIDTH/1080)/(sumPipe/2)),
                        0, 200*GameTwoConstants.SCREEN_WIDTH/1080, GameTwoConstants.SCREEN_HEIGHT/2));
                this.pipes.get(this.pipes.size()-1).setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe2));
                this.pipes.get(this.pipes.size()-1).randomY();
            }
            else{
                this.pipes.add(new GameTwoPipe(this.pipes.get(i-sumPipe/2).getX(), this.pipes.get(i-sumPipe/2).getY()
                       +this.pipes.get(i-sumPipe/2).getHeights() + this.distance, 200*GameTwoConstants.SCREEN_WIDTH/1080,
                        GameTwoConstants.SCREEN_HEIGHT/2));
                this.pipes.get(this.pipes.size()-1).setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe1));
            }
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);

        if(start){

            bird.draw(canvas);

            for(int i = 0; i < sumPipe; i++){

                if(bird.getRect().intersect(pipes.get(i).getRect()) || bird.getY()-bird.getHeights()<0 ||
                        bird.getY()>GameTwoConstants.SCREEN_HEIGHT){
                    GameTwoPipe.speed = 0;
                    if(!soundHit){
                        this.soundPool.play(this.soundOver, volume, volume, 1, 0, 1f);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        String userID = user.getUid();

                        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int highScoreDB = snapshot.child("gamesStatus").child("ScoreFlappy").getValue(int.class);
                                int gameCoins = snapshot.child("settings").child("coins").getValue(int.class);
                                int IntScore = Integer.parseInt(GameTwoMainActivity.game2Score.getText().toString());
                                GameTwoMainActivity.game2finalScore.setText(GameTwoMainActivity.game2Score.getText());
                                GameTwoMainActivity.game2HighScore.setText("Best: " + highScoreDB);

                                int coins = (int)  IntScore / 2;
                                reference.child(userID).child("settings").child("coins").setValue(gameCoins+coins);
                                showPopupWindowPrize(getRootView(), coins, IntScore);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        GameTwoMainActivity.game2Score.setVisibility(INVISIBLE);
                        GameTwoMainActivity.game2gameOver.setVisibility(VISIBLE);

                        soundHit = true;
                    }
                }

                if(this.bird.getX()+this.bird.getWidths() > pipes.get(i).getX()+pipes.get(i).getWidths()/2
                        && this.bird.getX()+this.bird.getWidths() <= pipes.get(i).getX()+pipes.get(i).getWidths()/2+GameTwoPipe.speed
                        && i < sumPipe/2){
                    score++;
                    this.soundPool.play(this.soundScore, volume, volume, 1, 0, 1f);
                    if(score > highScore){
                        highScore = score;

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        String userID = user.getUid();

                        reference.child(userID).child("gamesStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int highScoreDB = snapshot.child("ScoreFlappy").getValue(int.class);
                                if(highScore > highScoreDB){
                                    reference.child(userID).child("gamesStatus").child("ScoreFlappy").setValue(highScore);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    GameTwoMainActivity.game2Score.setText(String.valueOf(score));
                }

                if(this.pipes.get(i).getX() < -pipes.get(i).getWidths()){
                    this.pipes.get(i).setX(GameTwoConstants.SCREEN_WIDTH);
                    if(i < sumPipe/2){
                        pipes.get(i).randomY();
                    }
                    else{
                        pipes.get(i).setY(this.pipes.get(i-sumPipe/2).getY()
                                +this.pipes.get(i-sumPipe/2).getHeights() + this.distance);
                    }
                }
                this.pipes.get(i).draw(canvas);
            }
        }
        else{
            if(bird.getY() > GameTwoConstants.SCREEN_HEIGHT/2){
                bird.setDrop(-15*GameTwoConstants.SCREEN_HEIGHT/1920);
            }
            bird.draw(canvas);
        }

        handler.postDelayed(runnable, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            bird.setDrop(-15);
            if(loadedSound){
                this.soundPool.play(this.soundJump, volume, volume, 1, 0, 1f);
            }
        }

        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        soundHit = false;
        GameTwoMainActivity.game2Score.setText("0");
        score = 0;
        initPipes();
        initBird();
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
            GameTwoMainActivity.over = true;
        });

        dimBehind(popupWindow);
    }
}
