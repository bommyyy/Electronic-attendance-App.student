package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minew.beaconset.BluetoothState;
import com.minew.beaconset.ConnectionState;
import com.minew.beaconset.MinewBeacon;
import com.minew.beaconset.MinewBeaconConnection;
import com.minew.beaconset.MinewBeaconConnectionListener;
import com.minew.beaconset.MinewBeaconManager;
import com.minew.beaconset.MinewBeaconManagerListener;

import java.util.Collections;
import java.util.List;

public class TodayActivity extends AppCompatActivity {
    ListView timetable;
    myAdapter adapter;
    String[] number = {"1","2","3","4","5","6"};
    String[] name={"종단형 PBL 이병걸/ 제2 과학관","종단형 PBL 이병걸/ 제2 과학관"," "," ","전공진로탐색세미나 김명주/ 제1 과학관","전공진로탐색세미나 김명주/ 제1 과학관"};

    public static Context context_main;
    int beacon_num;

    //////////////////////////////////////////////-->보미지현
    private RecyclerView mRecycle = null;
    private MinewBeaconManager mMinewBeaconManager;
    private BeaconListAdapter  mAdapter;
    UserRssi comp = new UserRssi();
    private ProgressDialog mpDialog;
    public static MinewBeacon clickBeacon;
    private static final int REQUEST_ENABLE_BT = 2;
   ////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        timetable = (ListView) findViewById(R.id.timetable);
        adapter = new myAdapter();
        timetable.setAdapter(adapter);

        Button btn1 = (Button)findViewById(R.id.btn_sub);
        Button btn2 = (Button)findViewById(R.id.btn_onair);


//        major = mAdapter.getData(1).getMajor();
        //major = "50";
        context_main = this;

        //Intent secondIntent = getIntent();
        //secondIntent.getStringExtra("id");
        //TextView textView4 = findViewById(R.id.textView4);
        //String id_value = secondIntent.getStringExtra("id");
        //textView4.setText(id_value);

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
                beacon_num= MinewBeaconManager.scannedBeacons.size();
                if (beacon_num == 0) {
                    Intent intent = new Intent(
                            getApplicationContext(), // 현재 화면의 제어권자
                            NoAttendActivity.class); // 다음 넘어갈 클래스 지정
                    Intent secondIntent = getIntent();
                    TextView textView4 = findViewById(R.id.textView4);
                    String id_value = secondIntent.getStringExtra("id");
                    intent.putExtra("id",id_value);
                    intent.putExtra("beacon","2");

                    startActivity(intent);// 다음 화면으로 넘어간다
                }
                else{
                    Intent intent = new Intent(
                            getApplicationContext(), // 현재 화면의 제어권자
                            OnairActivity.class); // 다음 넘어갈 클래스 지정
                    Intent secondIntent = getIntent();
                    TextView textView4 = findViewById(R.id.textView4);
                    String id_value = secondIntent.getStringExtra("id");
                    intent.putExtra("id",id_value);
                    intent.putExtra("beacon","1");
                    String major = mAdapter.getData(0).getMajor();
                    intent.putExtra("major",major);
                    startActivity(intent);
                }
            }
        });
        //OnairActivity로 전환
        //////////////////////////////////////-->보미지현
        initView();
        initManager();
        checkBluetooth();
        checkLocation();

        dialogshow();
        mMinewBeaconManager.startService();
        ////////////////////////////////////////
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

    ////////////////////////////////보미지현
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecycle = (RecyclerView) findViewById(R.id.main_recyeler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(layoutManager);
        mAdapter = new BeaconListAdapter();

        mRecycle.setAdapter(mAdapter);

        mRecycle.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));

       // beacon_num = mAdapter.ItemCount();
    }

    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);

    }

    /*
     * check location
     * */
    private void checkLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},101);
        }

    }
    /**
     * check Bluetooth state
     */
    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                Toast.makeText(this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BluetoothStatePowerOff:
                showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }
    private void initListener() {
        //scan listener;
        mMinewBeaconManager.setMinewbeaconManagerListener(new MinewBeaconManagerListener() {
            @Override
            public void onUpdateBluetoothState(BluetoothState state) {
                switch (state) {
                    case BluetoothStatePowerOff:
                        Toast.makeText(getApplicationContext(), "bluetooth off", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothStatePowerOn:
                        Toast.makeText(getApplicationContext(), "bluetooth on", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onRangeBeacons(List<MinewBeacon> beacons) {
                Collections.sort(beacons, comp);

                mAdapter.setData(beacons);
            }

            @Override
            public void onAppearBeacons(List<MinewBeacon> beacons) {

            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> beacons) {

            }
        });
        mAdapter.setOnItemClickLitener(new BeaconListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mpDialog.setMessage(getString(R.string.connecting)
                        + mAdapter.getData(position).getName());
                mpDialog.show();
                mMinewBeaconManager.stopScan();
                //connect to beacon
                MinewBeacon minewBeacon = mAdapter.getData(position);

                MinewBeaconConnection minewBeaconConnection = new MinewBeaconConnection(TodayActivity.this, minewBeacon);
                minewBeaconConnection.setMinewBeaconConnectionListener(minewBeaconConnectionListener);
                minewBeaconConnection.connect();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }


    //connect listener;
    MinewBeaconConnectionListener minewBeaconConnectionListener = new MinewBeaconConnectionListener() {
        @Override
        public void onChangeState(MinewBeaconConnection connection, ConnectionState state) {
            switch (state) {
                case BeaconStatus_Connected:
                    mpDialog.dismiss();

                    Intent intent = new Intent(TodayActivity.this, DetilActivity.class);
                    intent.putExtra("mac", connection.setting.getMacAddress());
                    startActivity(intent);
                    break;
                case BeaconStatus_ConnectFailed:
                case BeaconStatus_Disconnect:
                    if (mpDialog != null) {
                        mpDialog.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
            }
        }

        @Override
        public void onWriteSettings(MinewBeaconConnection connection, boolean success) {

        }
    };
    @Override
    protected void onResume() {
        mMinewBeaconManager.startScan();
        initListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMinewBeaconManager.stopScan();
        super.onPause();
    }
    protected void dialogshow() {
        mpDialog = new ProgressDialog(TodayActivity.this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mpDialog.setTitle(null);//
        mpDialog.setIcon(null);//
        mpDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {

            }
        });
        mpDialog.setCancelable(true);//
        mpDialog.setCanceledOnTouchOutside(false);
    }

    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                mMinewBeaconManager.startScan();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mMinewBeaconManager.stopService();
        super.onDestroy();
    }
    ///////////////////////////////////////////////////
}

//정원
