package com.example.master;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class tempValreceiver extends BroadcastReceiver {

    private String tempVal;

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        tempVal=intent.getStringExtra("data");
        Toast.makeText(context,"温度:"+tempVal,Toast.LENGTH_SHORT).show();
    }
}
