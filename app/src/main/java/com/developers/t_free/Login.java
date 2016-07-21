package com.developers.t_free;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TabLayout tab=(TabLayout)findViewById(R.id.tab_layout);
        ViewPager mPager= (ViewPager) findViewById(R.id.pager);
        tab.addTab(tab.newTab().setText("Home"));
        tab.addTab(tab.newTab().setText("Payment & Recharge"));
        TabsPagerAdapter tb=new TabsPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(tb);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
