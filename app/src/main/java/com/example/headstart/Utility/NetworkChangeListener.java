package com.example.headstart.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.example.headstart.R;

public class NetworkChangeListener extends BroadcastReceiver {
    private static final String TAG = "NetworkChangeListener";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        //if not connected to the internet
        if (!Connectivity.isConnectedToInternet(context)){
            Log.d(TAG, "onReceive: checks if Is Internet Access");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_activity, null);
            //set layout to parent view
            alertDialogBuilder.setView(layout_dialog);

            Button retryBTN = layout_dialog.findViewById(R.id.retryBTN);

            //show Dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCancelable(true);

            //alertDialog.getWindow().setGravity(Gravity.CENTER);

            //when retry button is clicked, close dialog(Internet Accessible) , no Internet show dialog ag
            retryBTN.setOnClickListener(v -> {
                Log.d(TAG, "onClick: If button is clicked and Is Internet access Dismiss Dialog" +
                        "else continue showing dialog");
                alertDialog.dismiss();
                onReceive(context, intent);
            });
        }
    }
}
