package com.developers.t_free;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment{
    private String url;
    String rfid;
    private String url3;
    private ListView mList;
    private HttpURLConnection ur=null;
    private TextView t1,t2,t3,t4;
    ArrayList<String> eventlist,datetime;
    private SwipeRefreshLayout mSwipe;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        t1= (TextView) v.findViewById(R.id.textView4);
        eventlist=new ArrayList<String>();
        datetime=new ArrayList<String>();
        mList=(ListView)v.findViewById(R.id.listView);
        t2= (TextView) v.findViewById(R.id.textView6);
        t3= (TextView) v.findViewById(R.id.textView7);
        t4= (TextView) v.findViewById(R.id.textView8);
        mSwipe=(SwipeRefreshLayout)v.findViewById(R.id.activity_main_swipe_refresh_layout);
        String email=DataHolder.getMail();
        url="http://10.1.32.50:8500/data/mail="+email;
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa ------> "+url);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u=new URL(url);
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
                    System.out.println("dataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+sb);
                    JSONObject result=new JSONObject(sb.toString());
                    String name=result.getString("name");
                    t1.setText(name);
                    String address=result.getString("address");
                    t2.setText(address);
                    String contact=result.getString("phone");
                    t3.setText(contact);
                    rfid=result.getString("rfid");
                    DataHolder.setRf(rfid);
                    t4.setText(rfid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    ur.disconnect();
                }
            }
        });
        t.start();
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
                CustomAdapter adapter=new CustomAdapter(getActivity(),eventlist,datetime);
                mList.setAdapter(adapter);
            }
        });
        return v;
    }
  private void refreshContent(){
      url3="http://10.1.32.50:8500/note/rfid="+rfid;
      mSwipe.setRefreshing(false);
      Thread t2=new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  URL u=new URL(url3);
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
                  System.out.println("dataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+sb);
                  JSONObject result=new JSONObject(sb.toString());
                  JSONArray arrdat=result.optJSONArray("date");
                  for(int i=0;i<arrdat.length();i++){
                      System.out.println("aaaaaaaaaaaaaaaa "+arrdat.get(i).toString());
                      String date=arrdat.get(i).toString();
                      if(datetime.size()==0)
                        datetime.add(date);
                      else
                      {
                          boolean l=true;
                          for(int j=0;j<datetime.size();j++)
                          {
                              if(datetime.get(j).equals(date))
                              {
                                  l=false;
                                  break;
                              }
                          }
                          if(l)
                              datetime.add(date);
                      }
                  }
                  JSONArray arrevents=result.optJSONArray("event");
                  for(int j=0;j<arrevents.length();j++){
                      System.out.println("aaaaaaaaaaaa "+arrevents);
                      String event=arrevents.get(j).toString();
                      if(eventlist.size()==0)
                          eventlist.add(event);
                      else
                      {
                          boolean l=true;
                          for(int k=0;k<eventlist.size();k++)
                          {
                              if(eventlist.get(k).equals(event))
                              {
                                  l=false;
                                  break;
                              }
                          }
                          if(l)
                              eventlist.add(event);
                      }
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
              finally {
                  ur.disconnect();
              }
          }
      });
      t2.start();
  }
}
