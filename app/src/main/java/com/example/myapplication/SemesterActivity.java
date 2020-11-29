package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SemesterActivity extends AppCompatActivity {


    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_NO = "SUBno";
    private static final String TAG_NAME = "SUBname";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        list = (ListView) findViewById(R.id.subtable);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.56.1/connect.php"); //수정 필요
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String SUBno = c.getString(TAG_NO);
                String SUBname = c.getString(TAG_NAME);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_NO, SUBno);
                persons.put(TAG_NAME, SUBname);


                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    SemesterActivity.this, personList, R.layout.sem_item,
                    new String[]{TAG_NO, TAG_NAME},
                    new int[]{R.id.textView, R.id.textView2}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


//        semAdapter adapter = new semAdapter();

//        adapter.addItem(new Sem_item("1", "종단형 PBL"));
//        adapter.addItem(new Sem_item("2", "해피크라프츠"));
//        adapter.addItem(new Sem_item("3", "JAVA"));
//        adapter.addItem(new Sem_item("4", "C언어"));
//        adapter.addItem(new Sem_item("5", "악성코드"));
//        adapter.addItem(new Sem_item("6", "대학영어 읽기쓰기"));
//        listview.setAdapter(adapter);


//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(),Sub1Activity.class);
//                startActivity(intent);
//            }
//        });
//
//    }

    private void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }


            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}


//    class semAdapter extends BaseAdapter {
//        private ArrayList<Sem_item> items = new ArrayList<>();
//
//        public void addItem(Sem_item item){
//            items.add(item);
//        }
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            SemItemView view = new SemItemView(getApplicationContext());
//            Sem_item item = items.get(position);
//            view.setNum(item.getNum());
//            view.setName(item.getName());
//
//            return view;
//        }
//    }



//슬기
