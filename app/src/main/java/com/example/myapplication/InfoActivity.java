package com.example.myapplication;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class InfoActivity extends AppCompatActivity {

    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_Break = "Break";
    private static final String TAG_Late = "Late";
    private static final String TAG_Absence = "Absence";
    private static final String TAG_Notice = "Notice";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

//        ListView listview = findViewById(R.id.infotable);
//        infoAdapter adapter = new infoAdapter();
//        adapter.addItem(new Info_item("1. 쉬는 시간(출튀에 대한 시간 설정)",": 10분"));
//        adapter.addItem(new Info_item("2. 지각(△의 기준 설정)",": 5분"));
//        adapter.addItem(new Info_item("3. 결석(x의 기준 설정_수업마치기 N분 전)",": 1시간 30분"));
//        adapter.addItem(new Info_item("4. 교수님 전달사항",": 한 학기 동안 2번 결석 가능"));
//        listview.setAdapter(adapter);

        //list = (ListView) findViewById(R.id.infotable);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.219.199/info.php"); //ip주소변경
    }

//    class infoAdapter extends BaseAdapter {
//        private ArrayList<Info_item> items = new ArrayList<>();
//
//        public void addItem(Info_item item){
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
//            InfoItemView view = new InfoItemView(getApplicationContext());
//            Info_item item = items.get(position);
//            view.setContents(item.getContents());
//            view.setResult(item.getResult());
//
//            return view;
//        }
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String Break = c.getString(TAG_Break);
                String Late = c.getString(TAG_Late);
                String Absence = c.getString(TAG_Absence);
                String Notice = c.getString(TAG_Notice);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_Break, Break);
                persons.put(TAG_Late, Late);
                persons.put(TAG_Absence, Absence);
                persons.put(TAG_Notice,Notice);

                TextView textView = (TextView) findViewById(R.id.textView3);
                textView.setText(Break);
                TextView textView2 = (TextView) findViewById(R.id.textView5);
                textView2.setText(Late);
                TextView textView3 = (TextView) findViewById(R.id.textView6);
                textView3.setText(Absence);
                TextView textView4 = (TextView) findViewById(R.id.textView7);
                textView4.setText(Notice);
                //persons.put(TAG_NAME, SUBname);

                personList.add(persons);
            }


//            ListAdapter adapter = new SimpleAdapter(
//                    InfoActivity.this, personList, R.layout.content_info,
//                    new String[]{TAG_Break,TAG_Notice,TAG_Late,TAG_Absence},
//                    new int[]{R.id.textView3,R.id.textView6,R.id.textView5,R.id.textView7}
//            );

//            list.setAdapter(adapter);
//            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView parent, View v, int position, long id) {
//                    Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
//                    startActivity(intent);
//                }
//            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

//슬기

