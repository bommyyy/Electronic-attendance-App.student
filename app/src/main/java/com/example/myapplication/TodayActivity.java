package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TodayActivity extends AppCompatActivity {
    ListView timetable;
    myAdapter adapter;
    String[] number = {"1","2","3","4","5","6"};
    String[] name={"종단형 PBL 이병걸/ 제2 과학관","종단형 PBL 이병걸/ 제2 과학관"," "," ","전공진로탐색세미나 김명주/ 제1 과학관","전공진로탐색세미나 김명주/ 제1 과학관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        timetable = (ListView) findViewById(R.id.timetable);
        adapter = new myAdapter();
        timetable.setAdapter(adapter);

        Button btn1 = (Button)findViewById(R.id.btn_sub);
        Button btn2 = (Button)findViewById(R.id.btn_onair);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        SemesterActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });
        //SemesterActivity로 전환
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        OnairActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });
        //OnairActivity로 전환



    }
    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return number.length;
        }

        @Override
        public Object getItem(int position) {
            return number[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TodayView view = new TodayView(getApplicationContext());
            view.setNumber(number[position]);
            view.setName(name[position]);
            return view;
        }
    }



}

//정원
