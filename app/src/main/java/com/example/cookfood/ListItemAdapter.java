package com.example.cookfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListItemAdapter extends BaseAdapter {

    private final Context context;
    private List<String> data;
    public ListItemAdapter(List<String> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (Integer.parseInt(data.get(position))%3){
            case 0:
                convertView = LayoutInflater.from(context).inflate(R.layout.attentionlayout1,parent,false);
                break;
            case 1:
                convertView = LayoutInflater.from(context).inflate(R.layout.attentionlayout4,parent,false);
                break;
            case 2:
                convertView = LayoutInflater.from(context).inflate(R.layout.attentionlayout9,parent,false);
                break;
        }

        return convertView;
    }


}
