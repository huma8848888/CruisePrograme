package com.example.master;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
/*测温和测震的广播都在此接收*/
public class tempValreceiver extends BroadcastReceiver {

    private String tempVal,squezVal;

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getStringExtra("temp_data")!=null)
        {
            tempVal=intent.getStringExtra("temp_data");
            MainActivity.textview_showTempVal.setText(tempVal+"℃");}
            else Log.d("111","temp data is empty");
        if (intent.getStringExtra("ppdata")!=null)
        {
            squezVal=intent.getStringExtra("ppdata");
            MainActivity.textview_showSquezVal.setText(squezVal+"赫兹");}
            else Log.d("111","squez data is empty");

        //Toast.makeText(context,"温度:"+tempVal,Toast.LENGTH_SHORT).show();
    }
}
