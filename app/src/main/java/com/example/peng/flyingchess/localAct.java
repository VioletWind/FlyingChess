package com.example.peng.flyingchess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class localAct extends AppCompatActivity {
    RefreshableView refreshableView;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> item = new ArrayList<String>();
    Button btn_create;
    String roomName;
    ArrayList<Room> rm;
    /*SocketService ss;
    String response;

    public ServiceConnection SocketServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ss = ((SocketService.InterBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ss = null;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.local_rooms);

        //bindService(new Intent(localAct.this, SocketService.class),SocketServiceConnection, Context.BIND_AUTO_CREATE);
        //startService(new Intent(localAct.this, SocketService.class));
        //Toast.makeText(getApplicationContext(),ss.getWorkStatus() , Toast.LENGTH_SHORT).show();


        final Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname");
        String gender = intent.getStringExtra("gender");
        btn_create = (Button) findViewById(R.id.btn_create);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();

            }
        }, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),""+ position, Toast.LENGTH_SHORT).show();
                // TODO: Enter a room
                /*Short num = rm.get(position).num;
                if(num < 4) {
                    Intent it = new Intent(localAct.this, roomAct.class);
                    it.putExtra("name", roomName);
                    it.putExtra("nickname", nickname);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(),"房间已满", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        btn_create.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(localAct.this, createroomAct.class);
                intent1.putExtra("nickname",nickname);
                startActivityForResult(intent1,1);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String name = data.getExtras().getString("name");//得到新Activity 关闭后返回的数据
        //Log.i("test", result);
        item.add(name);
        roomName = name;
        adapter.notifyDataSetChanged();
    }

}
