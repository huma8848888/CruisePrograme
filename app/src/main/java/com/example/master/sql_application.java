package com.example.master;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sql_application {

    /*公共变量区*/
    //public static int flag=0;//线程执行控制位；
    public static final String TAG = "MainActivity";//LogCat调试用
    public int i = 1;
    private Handler mHandler;//将mHandler指定轮询的Looper
    public Button button;//添加数据按钮
    //public TextView textView;
    /*公共变量区结束*/

    /*SQL代码变量区*/
    private String url,user,password;
    public static Connection conn=null;
    public static java.sql.Statement statement=null;
    public static ResultSet rs=null;
    private HandlerThread thread;
    /*SQL代码变量区结束*/

    /*constructor */
    public sql_application() {
        thread= new HandlerThread("handler thread");//实例化一个特殊的线程HandlerThread，必须给其指定一个名字
        thread.start();//开启HandlerThread
    }

    /*SQL方法区*/
    private void conn_set()
    {
        // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
        String ip = "192.168.186.111";//记住每天开始调试程序前对IP地址进行手动更新
        int port = 3306;
        String dbName = "test";
        url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName; // 构建连接mysql的字符串
        user = "root";
        password =null;
    }
    private void sql_query()
    {
        String sql_query ="select xyz_name from xyz" +
                " where xyz_id=1" ;
        try {
            // 执行sql查询语句并获取查询信息
            rs=statement.executeQuery(sql_query);
            while (rs.next())
            {
                Log.e(TAG,rs.getString(1));
            }
        } catch (SQLException e) {

            Log.e(TAG, e.toString());
        }
    }
    private void sql_update()
    {
        //String sql_insert="insert into xyz(xyz_id,xyz_name) values(null,'大傻逼');";
        String sql_update="update xyz set xyz_name='小公主' where xyz_id=1";
        try {
            statement.executeUpdate(sql_update);
        }catch (SQLException e)
        {
            Log.e(TAG,e.toString());
        }
    }

    /*SQL方法区结束*/

    /*公共方法区*/

    public void sql_handler(String[] index_s,int defNum)/*index_s为传递近来的参数,defNum为控制查询还是更新的识别号*/
    {
        mHandler = new Handler(thread.getLooper()) //将mHandler与thread相关联，将创建子线程并在主线程中处理合为一体
        {
            public void handleMessage(android.os.Message msg) {
                /*下面开始业务逻辑处理*/
                conn_set();//设置连接
                // 3.连接JDBC
                try {
                    conn= DriverManager.getConnection(url, user, password);
                    statement= conn.createStatement();
                    Log.i("111", "远程连接成功!");
                } catch (SQLException e) {
                    //e.printStackTrace();
                    Log.e("111", "远程连接失败!请检查IP地址是否更新"+e.toString());
                }
                sql_update();
                //sql_query();
                Log.e("111","操作数据库成功！");

                    /*使用runOnUIThread完成UI更新动作
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("数据库操作  完成");
                        }
                    });*/

                /*子线程中不能修改UI，此处代码需要修改，否则报错*///textView.setText("添加数据库数据成功");
                //sql_update();
                try {
                    conn.close();
                    Log.d("111","成功关闭连接");
                } catch (SQLException e) {
                    Log.d("111", "关闭连接失败");
                }
            }
        };

        Message message_HandlerThread=new Message();
        message_HandlerThread.what=1;
        mHandler.sendMessage(message_HandlerThread);
    }
    //主线程handler部分


    /*公共方法区结束*/

}
