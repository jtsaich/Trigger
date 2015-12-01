package com.trigger.trigger.Image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.trigger.trigger.R;

/**
 * Created by Jack on 12/1/2015.
 */
public class ImageHandler {
    Bitmap gameTarget;
    Bitmap pauseButton;

    /**
     * Loads bitmaps from the resources
     * @param res
     * 		resources reference
     * */
    public ImageHandler(Resources res)
    {
        gameTarget = BitmapFactory.decodeResource(res, R.drawable.target);
        pauseButton =BitmapFactory.decodeResource(res, R.drawable.pause52);
    }

    /**
     * @return red bubble Bitmap
     * */
    public Bitmap getGameTarget(int size)
    {
        if (size < 1) size = 1;
        return Bitmap.createScaledBitmap(gameTarget, size, size, false);
    }

    public Bitmap getPauseButton(int size)
    {
        return Bitmap.createScaledBitmap(pauseButton, size, size, false);
    }
}
