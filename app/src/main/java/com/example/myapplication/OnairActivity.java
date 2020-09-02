package com.example.myapplication;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class OnairActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onair);

        prog();
    }

    public void prog(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressBar.setProgress(counter);
                if(counter ==100){
                    t.cancel();
                }
            }
        };
        t.schedule(tt,0,100);
    }
}

//지현 progressbar진행 코드