package com.microwaveteam.quarantinecoffee.serviceclasses;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class MyAlertDialog {
    /**
     *
     * @param content: your message
     * @param context: current activity class
     */
    public static void alert(String content, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
