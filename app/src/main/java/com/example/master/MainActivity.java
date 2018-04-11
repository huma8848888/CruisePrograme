package com.example.master;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import com.jaeger.library.StatusBarUtil;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    /*--UI--*/
    TextView textView_time;
    public static TextView textview_showCardID;
    public static TextView textview_showTempVal;
    public static TextView textview_showSquezVal;
    Button button_cardID;
    Button button_gettempVal;
    Button button_getsquezVal;
    Button button_sendData;
    /*--UI--*/

    /*SQL*/
    sql_application sql_application_handler=new sql_application();
    private final int UPDATE=1,QUERY=2;
    private String[] indexToSend=new String[]{"","",""};//第一位为卡ID，第二位为温度，第三位为震动频率
    /*SQL*/

    /*获取系统当前时间函数*/
    public String getCurrentSystemTime(){

        String CH_day="";
        Calendar calendar_now=Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar_now.get(Calendar.YEAR);
        //月
        int month = calendar_now.get(Calendar.MONTH)+1;
        //日
        int day = calendar_now.get(Calendar.DAY_OF_MONTH);
        //获取系统时间
        //小时
        int hour = calendar_now.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar_now.get(Calendar.MINUTE);
        //秒
        int second = calendar_now.get(Calendar.SECOND);
        //星期
        int day_in_week =calendar_now.get(Calendar.DAY_OF_WEEK);
        switch (day_in_week){
            case 1:CH_day="星期一";break;
            case 2:CH_day="星期二";break;
            case 3:CH_day="星期三";break;
            case 4:CH_day="星期四";break;
            case 5:CH_day="星期五";break;
            case 6:CH_day="星期六";break;
            default:CH_day="星期日";break;

        }
        return year+"年"+month+"月"+day+"日"+hour+"时"+minute+"分"+second+"秒"+" "+CH_day;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*UI实例化*/
        textView_time=(TextView) findViewById(R.id.textView_time);//实例化显示时间的textview
        textview_showCardID=(TextView) findViewById(R.id.textView_CARDID);//实例化卡ID显示
        textview_showTempVal=(TextView) findViewById(R.id.textView_tempVal);//实例化温度显示
        textview_showSquezVal=(TextView) findViewById(R.id.textView_sqzVal);//实例化震动频率显示

        button_cardID=(Button) findViewById(R.id.button_card);
        button_gettempVal=(Button) findViewById(R.id.button_temp);
        button_getsquezVal=(Button) findViewById(R.id.button_squez);
        button_sendData=(Button) findViewById(R.id.button_send);


        button_cardID.setOnClickListener(this);
        button_gettempVal.setOnClickListener(this);
        button_getsquezVal.setOnClickListener(this);
        button_sendData.setOnClickListener(this);
        /*UI实例化*/

        //目的是让状态栏全透明
        StatusBarUtil.setTransparent(MainActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new get_timeThread().start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {

        //Log.d("MainActivity","按键按下");
        switch (v.getId())
        {
            case R.id.button_card:cardID_start_app();break;
            case R.id.button_temp:TempAndSquez_start_app("get_Temp");break;
            case R.id.button_squez:TempAndSquez_start_app("get_Squez");break;
            case R.id.button_send:sql_handler();break;
            default:
        }

    }

    private void cardID_start_app(){

        try {
        Intent intent = new Intent();
        ComponentName name = new ComponentName("com.example.master.zc_basicnfcfunctest"
                                                    ,"com.example.master.zc_basicnfcfunctest.BasicNFCFunc");
        intent.setComponent(name);
        startActivity(intent);
        }catch(Exception e)
        {
             Toast.makeText(this,"请先安装该app",Toast.LENGTH_SHORT).show();
        }

    }

    private void TempAndSquez_start_app(String chooser){

        try {
            Intent intent = new Intent();
            ComponentName name = new ComponentName("com.example.master.temp_squeez","com.example.master.temp_squeez.MainActivity");
            intent.setComponent(name);
            if (chooser=="get_Temp")
            {
                intent.putExtra("getter",chooser);//在这里判断是测温还是测震

            }
            else
                intent.putExtra("getter",chooser);//
            startActivity(intent);
        }catch(Exception e)
        {
            Toast.makeText(this,"请先安装该app",Toast.LENGTH_SHORT).show();
        }

    }


    private void sql_handler()
    {
        indexToSend[0]=cardID_receiver.cardID;
        indexToSend[1]=tempValreceiver.tempVal;
        indexToSend[2]=tempValreceiver.squezVal;

        
        sql_application_handler.sql_handler(indexToSend,UPDATE);
    }

    private class get_timeThread extends Thread {//用来计时1秒的子线程
        @Override
        public void run() {
            while (true)
            {
                //super.run();
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.what = 1;
                    mhandler.sendMessage(msg);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1)
            {
                textView_time.setText(getCurrentSystemTime());
            }
        }
    };

}
