package com.example.cookfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cookfood.ui.caipu.NeedFood;

import java.util.List;

public class ListItemAdapterFood extends BaseAdapter {

    private final Context context;
    private List<NeedFood> data;
    public ListItemAdapterFood(List<NeedFood> data, Context context){
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.caipu_food_item,parent,false);
        }
        TextView food = convertView.findViewById(R.id.food);
        TextView num = convertView.findViewById(R.id.num);
        food.setText(data.get(position).food);
        num.setText(data.get(position).num);

        return convertView;
    }


}
