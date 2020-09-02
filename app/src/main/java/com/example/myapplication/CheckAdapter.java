package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CheckAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] programName;

    public CheckAdapter(Context context, String[]programName, int[] images) {
        super(context, R.layout.check_item, R.id.textView1, programName);
        this.context=context;
        this.images=images;
        this.programName= programName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View singleItem = convertView;
       CheckViewHolder holder = null;
        if(singleItem ==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.check_item,parent,false);
            holder = new CheckViewHolder(singleItem);
            singleItem.setTag(holder);
        }
        else{
            holder= (CheckViewHolder) singleItem.getTag();
        }
        holder.itemImage.setImageResource(images[position]);
        holder.programTitle.setText(programName[position]);

        return singleItem;
    }
}
//정원