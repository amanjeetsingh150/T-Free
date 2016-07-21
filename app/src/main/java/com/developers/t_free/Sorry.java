package com.developers.t_free;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Sorry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorry);
        AlertDialog.Builder prompt = new AlertDialog.Builder(this);
        prompt.setTitle("Error");
        prompt.setMessage("Sorry, your email is not registered in the government databse.");
        prompt.show();
    }
}
