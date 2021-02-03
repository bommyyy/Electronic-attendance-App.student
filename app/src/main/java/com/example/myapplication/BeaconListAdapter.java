package com.example.myapplication;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.minew.beaconset.MinewBeacon;
import com.minew.beaconset.MinewBeaconManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import androidx.recyclerview.widget.RecyclerView; //문제가 생기면 위에꺼 살리자


public class BeaconListAdapter extends RecyclerView.Adapter<BeaconListAdapter.MyViewHolder> {

    private List<MinewBeacon> mMinewBeacons;

    public static Context context_main;
    public String major;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.main_item, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setDataAndUi(mMinewBeacons.get(position));

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }
    public int ItemCount(){
        int a = mMinewBeacons.size();
        return a;
    }

    @Override
    public int getItemCount() {
        if (mMinewBeacons != null) {
            return mMinewBeacons.size();
        }
        return 0;
    }

    public void setData(List<MinewBeacon> minewBeacons) {
        this.mMinewBeacons = minewBeacons;
        notifyDataSetChanged();

    }

    public MinewBeacon getData(int position) {
        return mMinewBeacons.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private MinewBeacon mMinewBeacon;
        private final TextView mDevice_name;
        private final TextView mDevice_uuid;
        private final TextView mDevice_other;
        private final TextView mConnectable;
      TextView dateNow;

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);



        public MyViewHolder(View itemView) {
            super(itemView);
            mDevice_name = (TextView) itemView.findViewById(R.id.device_name);
            mDevice_uuid = (TextView) itemView.findViewById(R.id.device_uuid);
            mDevice_other = (TextView) itemView.findViewById(R.id.device_other);
            mConnectable = (TextView) itemView.findViewById(R.id.device_connectable);
            dateNow = (TextView) itemView.findViewById(R.id.datenow);

        }

        public void setDataAndUi(MinewBeacon minewBeacon) {

            mMinewBeacon = minewBeacon;
            mDevice_name.setText(mMinewBeacon.getName());
            mDevice_uuid.setText("UUID:" + mMinewBeacon.getUuid()); dateNow.setText("DateNow"+formatDate);
            if (mMinewBeacon.isConnectable()) {
                mConnectable.setText("CONN: YES");
            } else {
                mConnectable.setText("CONN: NO");
            }
//            String format = String.format("Major:%s Minor:%s Rssi:%s Battery:%s",
//                    mMinewBeacon.getMajor(),
//                    mMinewBeacon.getMinor(),
//                    mMinewBeacon.getRssi(),
//                    mMinewBeacon.getBattery());
            String format = mMinewBeacon.getMajor();
            mDevice_other.setText(format);

            insertoToDatabase(mMinewBeacon.getMajor().toString(),mMinewBeacon.getRssi(),formatDate.toString());
    }
        public void getmajor(MinewBeacon minewBeacon){
            mMinewBeacon = minewBeacon;
            major=mMinewBeacon.getMajor();
        }



        private void insertoToDatabase(final String UUID,int Rssi, String DateNow){
            class InsertData extends AsyncTask<String, Void, String> {
         //     ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
//                    loading = ProgressDialog.show(BeaconListAdapter.this, "Please Wait", null, true, true);
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                   //oading.dismiss();
                    //Log.d("Tag : ", s); // php에서 가져온 값을 최종 출력함
                  //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
                @Override
                protected String doInBackground(String... params) {

                    try {
                        String UUID = (String) params[0];
                        String Rssi = params[1];
                        String DateNow = (String) params[2];
                        String link = "http://192.168.219.184/Beacon.php";
                        String data = URLEncoder.encode("UUID", "UTF-8") + "=" + URLEncoder.encode(UUID, "UTF-8");
                        data += "&" + URLEncoder.encode("Rssi", "UTF-8") + "=" + URLEncoder.encode(Rssi, "UTF-8");
                       data +="&"+ URLEncoder.encode("DateNow","UTF-8")+"="+ URLEncoder.encode(Rssi, "UTF-8");
                        URL url = new URL(link);
                        URLConnection conn = url.openConnection();

                        conn.setDoOutput(true);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                        outputStreamWriter.write(data);
                        outputStreamWriter.flush();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        // Read Server Response
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        Log.d("tag : ", sb.toString()); // php에서 결과값을 리턴
                        return sb.toString();

                    } catch (Exception e) {
                        return new String("Exception: " + e.getMessage());
                    }
                }
            }
            InsertData task = new InsertData();
            task.execute(UUID,DateNow);
        }
}
}