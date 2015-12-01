package com.trigger.trigger.Game.GameCore;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.trigger.trigger.AppConstants;
import com.trigger.trigger.Game.GameElement.GameTarget;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 12/1/2015.
 */
public class GameEngine {
    static final int MAX_TARGETS = 5;
    static final float PAUSE_BUTTON_SIZE = 80, PAUSE_BUTTON_X = 10, PAUSE_BUTTON_Y = AppConstants.SCREEN_HEIGHT - 200;
    static final float SCORE_SIZE = 120, SCORE_X = AppConstants.SCREEN_WIDTH - 160, SCORE_Y = 240;

    static Date endGameTime;
    static Date startGameTime;
    static long remainingMilliseconds;
    static int currentScore;
    static List<GameTarget> targets;
    static List<GameTarget> removedTargets;
    static int targetThreshold;
    static final Object sync = new Object();
    static Double lastTouchedX, lastTouchedY;

    static boolean gamePaused;

    Paint paint;
    Random rand;

    public GameEngine()
    {
        targets = new LinkedList<GameTarget>();
        removedTargets = new LinkedList<GameTarget>();
        paint = new Paint();
        rand = new Random();
    }


    /**
     * Updates all relevant objects business logic
     * */
    public void update()
    {
        Date now = new Date();
        if (gamePaused || (startGameTime != null && now.before(startGameTime))) {
            return;
        }

        int randNum = rand.nextInt(100);
        if (randNum < 10 && targetThreshold < MAX_TARGETS) {
            targetThreshold++;
            createNewTarget(
                    rand.nextInt(AppConstants.SCREEN_WIDTH),
                    rand.nextInt(AppConstants.SCREEN_HEIGHT),
                    4
            );
        }
        updateTargets();
    }

    /**
     * Iterates through the Bubble list and advances them
     * */
    private void updateTargets()
    {
        synchronized (sync)
        {
            for(GameTarget target : targets)
            {
                target.update();
                if (target.isMarkedForRemoval()) {
                    removedTargets.add(target);
                }

                if (lastTouchedX != null && lastTouchedX != null) {
                    if (target.collidesWith(lastTouchedX, lastTouchedY)) {
                        target.onHit();
                        currentScore += 4;
                    }
                }
            }

            for(GameTarget removedTarget : removedTargets) {
                targets.remove(removedTarget);
                targetThreshold--;
            }

            removedTargets.clear();

            lastTouchedX = lastTouchedY = null;
        }
    }

    /**
     * Draws all objects according to their parameters
     * @param canvas
     * 			canvas on which will be drawn the objects
     * */
    public void draw(Canvas canvas)
    {
        drawTargets(canvas);
        drawPauseButton(canvas);
        drawScore(canvas);
    }

    /**
     * Draws bubble bitmaps
     * @param canvas
     * 			canvas on which will be drawn the bitmap
     * */
    private void drawTargets(Canvas canvas) {
        synchronized (sync) {
            for (GameTarget target : targets) {
                //Drawing bubble
                Bitmap bitmap = AppConstants.getImageHandler().getGameTarget((int) target.getSize());
                canvas.drawBitmap(bitmap, (float) (target.getX() - GameTarget.MAX_SIZE / 2), (float) (target.getY() - GameTarget.MAX_SIZE / 2), paint);
            }
        }
    }

    private void drawPauseButton(Canvas canvas) {
        synchronized (sync) {
            Bitmap bitmap = AppConstants.getImageHandler().getPauseButton((int) PAUSE_BUTTON_SIZE);
            canvas.drawBitmap(bitmap, PAUSE_BUTTON_X, PAUSE_BUTTON_Y, paint);
        }
    }

    private void drawScore(Canvas canvas) {
        synchronized (sync) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(SCORE_SIZE);
            canvas.drawText(String.valueOf(currentScore), SCORE_X, SCORE_Y, paint);
        }
    }

    public void resumeGame() {
        gamePaused = false;
        Calendar secondsLater = Calendar.getInstance();
        secondsLater.add(Calendar.SECOND, 3);
        startGameTime = secondsLater.getTime();

        secondsLater.add(Calendar.MILLISECOND, (int)remainingMilliseconds);
        endGameTime = secondsLater.getTime();

    }

    public void pauseGame() {
        gamePaused = true;
        Date now = new Date();
        remainingMilliseconds = endGameTime.getTime() - now.getTime();
    }

    public void restartGame() {
        currentScore = 0;
        targets.clear();

        Calendar secondsLater = Calendar.getInstance();
        secondsLater.add(Calendar.SECOND, 3);
        startGameTime = secondsLater.getTime();

        secondsLater.add(Calendar.SECOND, 60);
        endGameTime = secondsLater.getTime();
    }

    public void createNewTarget(double x, double y, double scale) {
        synchronized (sync) {
            targets.add(new GameTarget(x, y, scale));
        }
    }

    /**
     * Sets previous touch coordinates
     * @param x
     * 		current touch x coordinate
     * @param y
     * 		current touch y coordinate
     * */
    public void setLastTouch(float x, float y) {
        lastTouchedX = (double) x;
        lastTouchedY = (double) y;
    }
}
