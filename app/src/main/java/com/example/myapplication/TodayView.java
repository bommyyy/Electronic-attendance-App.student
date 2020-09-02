package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TodayView extends LinearLayout {
    TextView textView;
    TextView textView2;
    public TodayView(Context context) {
        super(context);
        inflation_init(context);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
    }

    private void inflation_init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.today_item,this,true);
    }

    public void setNumber(String number){
        textView.setText(number);
    }
    public void setName(String name){
        textView2.setText(name);
    }
}

//정원