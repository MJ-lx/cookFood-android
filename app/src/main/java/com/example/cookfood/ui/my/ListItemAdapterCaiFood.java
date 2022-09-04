package com.example.cookfood.ui.my;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cookfood.CookSqlite;
import com.example.cookfood.MyListView;
import com.example.cookfood.R;
import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.caipu.NeedFood;

import java.util.List;

public class ListItemAdapterCaiFood extends BaseAdapter {

    private final Context context;
    private List<NeedFood> data;


    public ListItemAdapterCaiFood(List<NeedFood> data, Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.cailanzi_item2,parent,false);
        }

        TextView food = convertView.findViewById(R.id.food);
        TextView num = convertView.findViewById(R.id.num);
        food.setText(data.get(position).food);
        num.setText(data.get(position).num);
        return convertView;
    }


}
