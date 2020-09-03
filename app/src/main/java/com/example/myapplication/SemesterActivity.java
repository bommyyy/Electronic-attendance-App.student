package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SemesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        ListView listview = findViewById(R.id.subtable);

        semAdapter adapter = new semAdapter();

        adapter.addItem(new Sem_item("1", "종단형 PBL"));
        adapter.addItem(new Sem_item("2", "해피크라프츠"));
        adapter.addItem(new Sem_item("3", "JAVA"));
        adapter.addItem(new Sem_item("4", "C언어"));
        adapter.addItem(new Sem_item("5", "악성코드"));
        adapter.addItem(new Sem_item("6", "대학영어 읽기쓰기"));
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Sub1Activity.class);
                startActivity(intent);
            }
        });

    }


    class semAdapter extends BaseAdapter {
        private ArrayList<Sem_item> items = new ArrayList<>();

        public void addItem(Sem_item item){
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
            SemItemView view = new SemItemView(getApplicationContext());
            Sem_item item = items.get(position);
            view.setNum(item.getNum());
            view.setName(item.getName());

            return view;
        }
    }

}

//슬기
