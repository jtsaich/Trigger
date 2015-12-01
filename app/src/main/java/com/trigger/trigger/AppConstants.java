package com.trigger.trigger;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.trigger.trigger.Game.GameCore.GameEngine;
import com.trigger.trigger.Image.ImageHandler;

/**
 * Created by Jack on 12/1/2015.
 */
public class AppConstants {
    static ImageHandler imageHandler;
    static GameEngine engine;

    public static int SCREEN_WIDTH,
            SCREEN_HEIGHT;

    /**
     * Initiates the applciation constants
     * */
    public static void initialization(Context context)
    {
        imageHandler = new ImageHandler(context.getResources());
        setScreenSize(context);
        engine = new GameEngine();
    }


    /**
     * Sets screen size constants accordingly to device screen size
     * */
    private static void setScreenSize(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        AppConstants.SCREEN_WIDTH = width;
        AppConstants.SCREEN_HEIGHT = height;
    }

    /**
     * @return BitmapBank instance
     * */
    public static ImageHandler getImageHandler()
    {
        return imageHandler;
    }

    /**
     * @return GameEngine instance
     * */
    public static GameEngine getEngine()
    {
        return engine;
    }


    /**
     * Stops the given thread
     * @param thread
     * 			thread to stop
     * */
    public static void stopThread(Thread thread)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }
}
