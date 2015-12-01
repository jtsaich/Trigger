package com.trigger.trigger.Game.GameCore;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.trigger.trigger.AppConstants;
import com.trigger.trigger.UI.Menu.PauseMenuDialogFragment;

/**
 * Created by Jack on 12/1/2015.
 */
public class GameActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the activity view as GameView class
        SurfaceView view = new GameView(this, AppConstants.getEngine());

        setContentView(view);
        AppConstants.getEngine().restartGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                onActionDown(event);
                break;
            }
            default:
                break;
        }
        return false;
    }

    /*activates on touch down event*/
    private void onActionDown(MotionEvent event)
    {
        AppConstants.getEngine()
                .setLastTouch(event.getX(), event.getY());

        if (event.getX() <= 160 && event.getY() >= AppConstants.SCREEN_HEIGHT - 160) {
            pauseGame();
        }
    }

    public void pauseGame() {
        AppConstants.getEngine().pauseGame();

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = PauseMenuDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
    }
}
