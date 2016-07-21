package com.developers.t_free;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class option extends AppCompatActivity {
    private Button mpay;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        mpay=(Button)findViewById(R.id.button3);
        mpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payinfo mp=new payinfo(option.this);
                mp.show();
            }
        });
    }
}
