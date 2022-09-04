package com.example.cookfood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookfood.ui.caipu.CaiPuClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends BaseAdapter {

    private final Context context;
    private List<CaiPuClass> data;
    public itemAdapter(List<CaiPuClass> data, Context context){
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
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.recommend_item,parent,false);
        }

        ImageView titleImg = convertView.findViewById(R.id.title_img);
        Resources res = context.getResources();
        int resId = res.getIdentifier(data.get(position).titleimg, "mipmap",context.getPackageName());
        titleImg.setImageResource(resId);
        TextView title = convertView.findViewById(R.id.title);
        title.setText(data.get(position).title);
        TextView tag = convertView.findViewById(R.id.tags);
        tag.setText(String.valueOf(data.get(position).id));
        TextView username = convertView.findViewById(R.id.user_name);
        username.setText(data.get(position).name);
        TextView textview = convertView.findViewById(R.id.likecount);
        textview.setText(data.get(position).likes);

        return convertView;
    }


}
