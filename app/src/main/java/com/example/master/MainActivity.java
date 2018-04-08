package com.example.master;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*--UI--*/
    TextView textView_time;
    TextView textview_showCardID;
    TextView textview_showTempVal;
    TextView textview_showSquezVal;
    Button button_cardID;
    Button button_gettempVal;
    Button button_getsquezVal;
    Button button_sendData;
    /*--UI--*/


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



        textView_time=(TextView) findViewById(R.id.textView_time);//实例化显示时间的textview
        textview_showCardID=(TextView) findViewById(R.id.textView_CARDID);//实例化ID显示
        textview_showTempVal=(TextView) findViewById(R.id.textView_sqzVal);//实例化温度显示
        textview_showSquezVal=(TextView) findViewById(R.id.textView_sqzVal);//实例化震动频率显示

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
    public void onClick(View v) {

    }

    private class get_timeThread extends Thread {
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
