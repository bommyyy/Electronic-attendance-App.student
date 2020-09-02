package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    }
    class myAdapter extends BaseAdapter{

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