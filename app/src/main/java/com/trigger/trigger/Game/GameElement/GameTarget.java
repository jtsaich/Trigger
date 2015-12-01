package com.trigger.trigger.Game.GameElement;

import android.util.Log;

/**
 * Created by Jack on 12/1/2015.
 */
public class GameTarget {
    public static final double MAX_SIZE = 300;

    private double xPos;
    private double yPos;
    private double sizeScale;
    private double size;
    private boolean isExpanding;
    private boolean markedForRemoval;

    public GameTarget(double x, double y, double s) {
        xPos = x;
        yPos = y;
        sizeScale = s;
        size = 0;
        isExpanding = true;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getSize() {
        return size;
    }

    public boolean collidesWith(double x, double y) {
        return (this.getX() <= x && this.getX() + this.getSize() >= x &&
                this.getY() <= y && this.getY() + this.getSize() >= y);
    }

    public void onHit() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void update() {
        if (size >= MAX_SIZE) {
            isExpanding = false;
        }

        if (isExpanding) {
            if (size > MAX_SIZE / 2)
                sizeScale = 6;
            size += sizeScale;
        } else {
            if (size < MAX_SIZE / 2)
                sizeScale = 4;
            size -= sizeScale;
        }

        if (size < 0 && !isExpanding)
            this.markedForRemoval = true;

    }
}
