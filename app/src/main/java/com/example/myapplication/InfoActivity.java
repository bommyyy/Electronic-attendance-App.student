package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ListView listview = findViewById(R.id.infotable);

        infoAdapter adapter = new infoAdapter();


        adapter.addItem(new Info_item("1. 쉬는 시간(출튀에 대한 시간 설정)",": 10분"));
        adapter.addItem(new Info_item("2. 지각(△의 기준 설정)",": 5분"));
        adapter.addItem(new Info_item("3. 결석(x의 기준 설정_수업마치기 N분 전)",": 1시간 30분"));
        adapter.addItem(new Info_item("4. 교수님 전달사항",": 한 학기 동안 2번 결석 가능"));
        listview.setAdapter(adapter);




    }

    class infoAdapter extends BaseAdapter {
        private ArrayList<Info_item> items = new ArrayList<>();

        public void addItem(Info_item item){
            items.add(item);
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            InfoItemView view = new InfoItemView(getApplicationContext());
            Info_item item = items.get(position);
            view.setContents(item.getContents());
            view.setResult(item.getResult());

            return view;
        }
    }

}

//슬기

