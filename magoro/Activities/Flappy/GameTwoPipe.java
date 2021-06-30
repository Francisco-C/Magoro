package com.example.magoro.Activities.Flappy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class GameTwoPipe extends GameTwoBaseObject{

    public static int speed;

   public GameTwoPipe(float x, float y, int width, int height){
        super(x, y, width, height);

        speed = 10 * GameTwoConstants.SCREEN_WIDTH/1080;
   }

   public void draw(Canvas canvas){
       this.x -= speed;
       canvas.drawBitmap(this.bitmap, this.x, this.y, null);
   }

   public void randomY(){
       Random r = new Random();
       this.y = r.nextInt((0+this.heights/4)+1)-this.heights/4;
   }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, widths, heights, true);
    }
}
