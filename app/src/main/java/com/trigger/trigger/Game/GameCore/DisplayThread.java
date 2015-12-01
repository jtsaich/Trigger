package com.trigger.trigger.Game.GameCore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.trigger.trigger.AppConstants;

/**
 * Created by Jack on 12/1/2015.
 */
public class DisplayThread extends Thread {
    SurfaceHolder surfaceHolder;
    Paint backgroundPaint;

    long _sleepTime;

    //Delay amount between screen refreshes
    final long  DELAY = 4;

    boolean isOnRun;

    public DisplayThread(SurfaceHolder surfaceHolder, Context context)
    {
        this.surfaceHolder = surfaceHolder;

        //black painter below to clear the screen before the game is rendered
        backgroundPaint = new Paint();
        backgroundPaint.setARGB(255, 0, 0, 0);
        isOnRun = true;
    }

    /**
     * This is the main nucleus of our program.
     * From here will be called all the method that are associated with the display in GameEngine object
     * */
    @Override
    public void run()
    {
        //Looping until the boolean is false
        while (isOnRun)
        {
            //Updates the game objects buisiness logic
            AppConstants.getEngine().update();

            //locking the canvas
            Canvas canvas = surfaceHolder.lockCanvas(null);

            if (canvas != null)
            {
                //Clears the screen with black paint and draws object on the canvas
                synchronized (surfaceHolder)
                {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    AppConstants.getEngine().draw(canvas);
                }

                //unlocking the Canvas
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            //delay time
            try
            {
                Thread.sleep(DELAY);
            }
            catch (InterruptedException ex)
            {
                //TODO: Log
            }
        }
    }

    /**
     * @return whether the thread is running
     * */
    public boolean isRunning()
    {
        return isOnRun;
    }
    /**
     * Sets the thread state, false = stoped, true = running
     **/
    public void setIsRunning(boolean state)
    {
        isOnRun = state;
    }
}
