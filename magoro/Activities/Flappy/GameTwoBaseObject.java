package com.example.magoro.Activities.Flappy;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class GameTwoBaseObject {

    protected float x, y;
    protected int widths, heights;
    protected Rect rect;
    protected Bitmap bitmap;

    public GameTwoBaseObject(){

    }

    public GameTwoBaseObject(float x, float y, int widths, int heights) {
        this.x = x;
        this.y = y;
        this.widths = widths;
        this.heights = heights;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidths() {
        return widths;
    }

    public void setWidths(int widths) {
        this.widths = widths;
    }

    public int getHeights() {
        return heights;
    }

    public void setHeights(int heights) {
        this.heights = heights;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getRect() {
        return new Rect((int) this.x,(int) this.y,(int) this.x+this.widths,(int) this.y+this.heights);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
