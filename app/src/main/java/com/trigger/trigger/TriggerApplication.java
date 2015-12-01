package com.trigger.trigger;

import android.app.Application;

/**
 * Created by Jack on 12/1/2015.
 */
public class TriggerApplication extends Application {
    public TriggerApplication() {}

    @Override
    public void onCreate() {
        super.onCreate();
        AppConstants.initialization(this.getApplicationContext());
    }
}
