package com.trigger.trigger.Game.GameCore;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.trigger.trigger.AppConstants;

/**
 * Created by Jack on 12/1/2015.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    Context context;

    private DisplayThread displayThread;

    public GameView(Context context, GameEngine engine) {
        super(context);

        this.context = context;
        InitView();
    }

    /**
     * Initiate the view components
     */
    void InitView() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        displayThread = new DisplayThread(holder, context);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
           /*DO NOTHING*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        //Stop the display thread
        displayThread.setIsRunning(false);
        AppConstants.stopThread(displayThread);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        //Starts the display thread
        if (!displayThread.isRunning()) {
            displayThread = new DisplayThread(getHolder(), context);
            displayThread.start();
        } else {
            displayThread.start();
        }
    }
}
