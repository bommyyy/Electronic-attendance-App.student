package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckViewHolder {

    ImageView itemImage;
    TextView programTitle;
    CheckViewHolder(View v)
    {
        itemImage = v.findViewById(R.id.imageView);
        programTitle = v.findViewById(R.id.textView1);

    }
}

//정원