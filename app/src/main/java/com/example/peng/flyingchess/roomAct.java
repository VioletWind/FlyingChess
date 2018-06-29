package com.example.peng.flyingchess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class roomAct extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.room_layout);

        Intent it = getIntent();
        String roomname = it.getStringExtra("name");
        String nickname = it.getStringExtra("nickname");

        Toast.makeText(getApplicationContext(), nickname + "进入房间" + roomname, Toast.LENGTH_SHORT).show();

        btn = (Button) findViewById(R.id.btn_begin);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(roomAct.this, gameAct.class);
                startActivity(it);
            }
        });

    }

}
