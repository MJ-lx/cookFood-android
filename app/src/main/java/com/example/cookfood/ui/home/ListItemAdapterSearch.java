package com.example.cookfood.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cookfood.CaipuDetail;
import com.example.cookfood.MainActivity;
import com.example.cookfood.R;
import com.example.cookfood.Search;
import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.caipu.NeedFood;

import java.util.List;

public class ListItemAdapterSearch extends BaseAdapter {

    private final Context context;
    private List<CaiPuClass> data;


    public ListItemAdapterSearch(List<CaiPuClass> data, Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        }
        TextView title = convertView.findViewById(R.id.title);
        title.setText(data.get(position).title);
        TextView user_name = convertView.findViewById(R.id.user_name);
        user_name.setText(data.get(position).name);

        ImageButton imageButton = convertView.findViewById(R.id.title_img);
        Resources res = context.getResources();
        int resId = res.getIdentifier(data.get(position).titleimg, "mipmap",context.getPackageName());
        imageButton.setImageResource(resId);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建意图对象
                Intent intent = new Intent(context, CaipuDetail.class);
                //设置传递键值对
                intent.putExtra("caipu_id",String.valueOf(data.get(position).id));
                intent.putExtra("user_id",data.get(position).userId);
                //激活意图
                context.startActivity(intent);
            }
        });

        return convertView;
    }


}
