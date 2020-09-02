package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class InfoItemView extends LinearLayout {
    TextView textView,textView2;


    public InfoItemView(Context context){
        super(context);
        init(context);
    }

    public InfoItemView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.info_item,this,true);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }

    public void setContents(String contents){
        textView.setText(contents);
    }
    public void setResult(String result){
        textView2.setText(result);
    }
}

//슬기