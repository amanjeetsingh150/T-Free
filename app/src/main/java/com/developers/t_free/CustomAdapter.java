package com.developers.t_free;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amanjeet Singh on 16-Jul-16.
 */
public class CustomAdapter extends BaseAdapter{
    private Activity activity;
    private ListView mList;
    private CustomAdapter adapter;
    private ArrayList<String> eve=new ArrayList<>();
    private ArrayList<String> dat=new ArrayList<>();
    public CustomAdapter(Activity activity, ArrayList eve, ArrayList dat) {
        this.activity=activity;
        this.eve=eve;
        this.dat=dat;
    }

    @Override
    public int getCount() {
        return eve.size();
    }

    @Override
    public Object getItem(int position) {
        return eve.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.card_view_row,null);
        String a=eve.get(position);
        String b=dat.get(position);
        TextView tt=(TextView)convertView.findViewById(R.id.event);
        TextView tt1=(TextView)convertView.findViewById(R.id.date);
        tt.setText(a);
        tt1.setText(b);
        return convertView;
    }
}
