package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OnairActivity extends AppCompatActivity {

    HttpClient httpclient;
    HttpPost httppost;
    List<NameValuePair> nameValuePairs;
    HttpResponse response;

    ProgressBar progressBar;
    int counter = 0;
    //String var2 = ((MainActivity)MainActivity.context_main).var; //지현 추가
    ///public static final String URL_UPDT = "http://192.168.219.184/onair.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onair);



        prog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateAttend();
            }
        }).start();

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

    public void updateAttend(){
        try {
//            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view2 = inflater.inflate(R.layout.activity_today, null);

//            TextView msg_id = (TextView)view2.findViewById(R.id.textView4);
//            //String id = var2;
//            String id = msg_id.getText().toString();
            Intent secondIntent = getIntent();
            String id_value = secondIntent.getStringExtra("id");
            String beacon= secondIntent.getStringExtra("beacon");

            //HashMap<String,String> requestedParams = new HashMap<>();
            //requestedParams.put("id",var2);
            //Log.d("HashMap",requestedParams.get("id"));
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://192.168.219.184/onair.php"); //ip주소변경
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", id_value));
            nameValuePairs.add(new BasicNameValuePair("beacon",beacon));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
        } catch (IOException e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

}

//지현 progressbar진행 코드