package com.example.magoro.Activities.Flappy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.ArrayList;

public class GameTwoBird extends GameTwoBaseObject{

    private ArrayList<Bitmap> alb = new ArrayList<>();
    private int counter, vFlap, idCurrentBitmap;
    private float drop;

    public GameTwoBird(){

        this.counter = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
        this.drop = 0;
    }

    public void draw(Canvas canvas){
        drop();
        canvas.drawBitmap(this.getBitmap(), this.x, this.y, null);
    }

    private void drop() {

        this.drop += 0.6;
        this.y += this.drop;
    }

    public ArrayList<Bitmap> getAlb() {
        return alb;
    }

    public void setAlb(ArrayList<Bitmap> alb) {
        this.alb = alb;
        for(int i =  0; i < alb.size(); i++){
            this.alb.set(i, Bitmap.createScaledBitmap(this.alb.get(i), this.widths, this.heights, true));
        }
    }

    @Override
    public Bitmap getBitmap() {

        counter++;

        if(this.counter == this.vFlap){
            for(int i=0; i < alb.size(); i++){
                if(i == alb.size()-1){
                    this.idCurrentBitmap = 0;
                    break;
                }
                else if(this.idCurrentBitmap == i){
                    idCurrentBitmap = i+1;
                    break;
                }
            }
            counter = 0;
        }

        if(this.drop < 0){
            Matrix matrix = new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(alb.get(idCurrentBitmap), 0, 0, alb.get(idCurrentBitmap).getWidth(),
                    alb.get(idCurrentBitmap).getHeight(), matrix, true);
        }
        else if(drop >= 0){
            Matrix matrix = new Matrix();
            if(drop < 70){
                matrix.postRotate(-25+(drop*2));
            }
            else{
                matrix.postRotate(45);
            }
            return Bitmap.createBitmap(alb.get(idCurrentBitmap), 0, 0, alb.get(idCurrentBitmap).getWidth(),
                    alb.get(idCurrentBitmap).getHeight(), matrix, true);
        }

        return this.getAlb().get(idCurrentBitmap);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }

    @Override
    public Rect getRect() {
        return new Rect((int)this.x+20, (int)this.y+20, (int)this.x+this.widths-10, (int)this.y+this.heights-10);
    }
}
