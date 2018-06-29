package com.example.peng.flyingchess;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class createroomAct extends AppCompatActivity {
    Button btn;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.create_room);
        btn = (Button) findViewById(R.id.btn_ok);
        et = (EditText) findViewById(R.id.roomname);
        Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname");

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = et.getText().toString();
                Intent it = new Intent();
                it.putExtra("name", n);
                createroomAct.this.setResult(RESULT_OK, it);
                //createroomAct.this.finish();

                Intent it2 = new Intent(createroomAct.this, roomAct.class);
                it2.putExtra("name", n);
                it2.putExtra("nickname",nickname);
                startActivity(it2);
                finish();
            }
        });
    }

}
