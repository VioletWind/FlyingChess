package com.example.peng.flyingchess;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by peng on 2018/6/28.
 */

public class SocketService extends Service implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Binder binder;
    private Thread td;
    private String workStatus;
    private String currAction;

    public void sendRequest(String action) {
        try {
            workStatus = null;
            JSONObject json = new JSONObject();
            json.put("action", action);
            currAction = action;
            sendMessage(json);
        } catch (Exception e) {
            workStatus = "FAILURE";
            e.printStackTrace();
        }
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void closeConnection() {
        JSONObject json = new JSONObject();
        try {
            json.put("action", "exit");
            sendMessage(json);// 向服务器端发送断开连接请求
            //Log.v("qlq", "the request is " + json.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void connectService() {
        try {
            workStatus = "connect";
            socket = new Socket("119.29.58.195", 8989);

            reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "GBK"));

            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(), "GBK")), true);

        } catch (SocketException ex) {
            ex.printStackTrace();
            workStatus = "TAG_CONNECTFAILURE";// 如果是网络连接出错了，则提示网络连接错误
            return;
        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();
            workStatus = "TAG_CONNECTFAILURE";// 如果是网络连接出错了，则提示网络连接错误
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
            workStatus = "TAG_CONNECTFAILURE";// 如果是网络连接出错了，则提示网络连接错误
            return;
        }
    }

    private void sendMessage(JSONObject json) {
        /*if (!isNetworkConnected()) {
            workStatus = "TAG_CONNECTFAILURE";
            return;
        }*/
        if (socket == null)
            connectService();
        if(!SocketService.this.td.isAlive())
            (td = new Thread(SocketService.this)).start();

        if (!socket.isConnected() || (socket.isClosed())) {
            for (int i = 0; i < 3 && workStatus == null; i++) {// 如果连接处于关闭状态，重试三次，如果连接正常了，跳出循环
                socket = null;
                connectService();
                if (socket.isConnected() && (!socket.isClosed())) {
                    //Log.v("QLQ", "workStatus is not connected!11333");
                    break;
                }
            }

            if (!socket.isConnected() || (socket.isClosed()))// 如果此时连接还是不正常，提示错误，并跳出循环
            {
                workStatus = "TAG_CONNECTFAILURE";
                //Log.v("QLQ", "workStatus is not connected!111444");
                return;
            }
        }

        if (!socket.isOutputShutdown()) {
            try {
                workStatus = "send";
                writer.println(json.toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //Log.v("QLQ", "workStatus is not connected!55555666666");
                e.printStackTrace();
                workStatus = "FAILURE";
            }
        } else {
            workStatus = "TAG_CONNECTFAILURE";
        }
    }
    private void dealUploadSuperviseTask(JSONObject json) {
        try{
            workStatus = json.getString("result");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            workStatus = "FAILURE";
        }
    }

    private void getMessage(String str) {
        try {
            JSONObject json = new JSONObject(str);
            /*String action = json.getString("action");// 提取JSON的action信息，获取当前JSON响应的是哪个操作。
            if(!currAction.equals(action))
                return;
            if (action.equals("getCategory")) {
                dealUploadSuperviseTask(json);
            }*/
            dealUploadSuperviseTask(json);
        } catch (Exception ex) {
            ex.printStackTrace();
            workStatus = "FAILURE";
        }
    }
    public class InterBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        binder = new InterBinder();
        td = new Thread(SocketService.this);// 启动线程
        td.start();

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        workStatus = "test";
        connectService();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.v("QLQ", "Service is on destroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //Log.v("QLQ", "service on onUnbind");
        return super.onUnbind(intent);
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(500);// 休眠0.5s
                if (socket != null && !socket.isClosed()) {// 如果socket没有被关闭
                    if (socket.isConnected()) {// 判断socket是否连接成功
                        if (!socket.isInputShutdown()) {
                            String content;
                            if ((content = reader.readLine()) != null) {
                                getMessage(content);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {

            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            workStatus = "TAG_CONNECTFAILURE";// 如果出现异常，提示网络连接出现问题。
            ex.printStackTrace();
        }
    }
}
