package com.example.lec08;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class CustomAdapter extends BaseAdapter {
    List<String[]> adapterData = new ArrayList<>();

    public CustomAdapter(List<String[]> adapterData) {
        this.adapterData = adapterData;
    }

    @Override
    public int getCount() {
        return adapterData.size();
    }

    @Override
    public Object getItem(int position) {
        return adapterData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater myLayoutInflater = LayoutInflater.from(parent.getContext());
            convertView = myLayoutInflater.inflate(R.layout.layout_item,parent,false);
        }
        TextView id = convertView.findViewById(R.id.textView4);
        TextView name = convertView.findViewById(R.id.textView5);
        TextView dept = convertView.findViewById(R.id.textView6);
        String[] eachRecord = (String[]) getItem(position);
        id.setText(eachRecord[0]);
        name.setText(eachRecord[1]);
        dept.setText(eachRecord[2]);
        if(position == 0 ){
            id.setTextColor(Color.RED);
            name.setTextColor(Color.RED);
            dept.setTextColor(Color.RED);
        }
        return convertView;
    }
}
