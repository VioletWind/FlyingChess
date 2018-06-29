package com.example.peng.flyingchess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONObject;


public class settingAct extends AppCompatActivity {
    Button btn_multi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.setting_layout);

        final Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname");
        final String gender = intent.getStringExtra("gender");

        btn_multi = (Button) findViewById(R.id.btn_multiPlayer);
        btn_multi.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(settingAct.this, localAct.class);
                it.putExtra("nickname", nickname);
                it.putExtra("gender", gender);
                startActivity(it);
            }
        });
    }
}
