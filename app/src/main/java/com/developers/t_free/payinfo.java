package com.developers.t_free;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Amanjeet Singh on 16-Jul-16.
 */
public class payinfo extends Dialog {
    private Button payment;
    public payinfo(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payinfo);
        payment=(Button)findViewById(R.id.pay);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Processing",Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),"Payment done",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });


    }
}
