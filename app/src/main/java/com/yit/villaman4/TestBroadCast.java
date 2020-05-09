package com.yit.villaman4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

public class TestBroadCast extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        String title;
        if(action.equalsIgnoreCase("Approve")){
            title = "Approve title";
        }
        else{
            title = "Reject title";
        }
        AlertDialog a = new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // ok button
                        if(action.equalsIgnoreCase("Approve")){
                            //Approve YES action
                        }
                        else{
                            //Reject YES action;
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel button
                        if(action.equalsIgnoreCase("Approve")){
                            //Approve NO action
                        }
                        else{
                            //Reject NO action;
                        }
                    }
                }).create();
        //You have to use below line, otherwise you will get "Unable to add window -- token null is not for an application"
        a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        a.show();
    }
}
