package com.developers.t_free;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Amanjeet Singh on 17-Jul-16.
 */
public class amount extends Dialog {
    String url1,mrf;
    private EditText e1;
    private Button b;
    private HttpURLConnection ur=null;
    String am;
    public amount(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amt);
        e1= (EditText) findViewById(R.id.editText5);
        b=(Button)findViewById(R.id.button4);
        mrf=DataHolder.getRf();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t1=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            am=e1.getText().toString();
                            url1="http://10.1.32.50:8500/recharge/rfid="+mrf+"&amount="+am;
                            System.out.println("aaaaaaaaaaaaaaa"+url1);
                            URL u=new URL(url1);
                            ur=(HttpURLConnection)u.openConnection();
                            ur.setRequestMethod("GET");
                            ur.setRequestProperty("Content-length", "0");
                            ur.setUseCaches(false);
                            ur.setAllowUserInteraction(false);
                            ur.connect();
                            BufferedReader br = new BufferedReader(new InputStreamReader(ur.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            Toast.makeText(getContext(),"Recharge Done!!",Toast.LENGTH_LONG).show();
                            dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t1.start();
            }
        });

    }

    }
