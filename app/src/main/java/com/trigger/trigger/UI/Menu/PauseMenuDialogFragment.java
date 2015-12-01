package com.trigger.trigger.UI.Menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.trigger.trigger.AppConstants;


/**
 * Created by Jack on 12/1/2015.
 */
public class PauseMenuDialogFragment extends DialogFragment {
    final CharSequence[] items = { "Resume", "Restart" };

    public static PauseMenuDialogFragment newInstance() {
        PauseMenuDialogFragment f = new PauseMenuDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            AppConstants.getEngine().resumeGame();
                        } else {
                            AppConstants.getEngine().restartGame();
                        }
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


}
