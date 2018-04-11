package com.example.master;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.master.MainActivity;

public class cardID_receiver extends BroadcastReceiver {

    public static String cardID="";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        cardID=intent.getStringExtra("data");
        MainActivity.textview_showCardID.setText(cardID);
        Toast.makeText(context,"卡号:"+cardID,Toast.LENGTH_SHORT).show();
    }


}
