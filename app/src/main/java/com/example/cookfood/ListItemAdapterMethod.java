package com.example.cookfood;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookfood.ui.caipu.MethodClass;
import com.example.cookfood.ui.caipu.NeedFood;

import java.util.List;

public class ListItemAdapterMethod extends BaseAdapter {

    private final Context context;
    private List<MethodClass> data;
    private static final String[] strings = {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三"};
    public ListItemAdapterMethod(List<MethodClass> data, Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.caipu_method_item,parent,false);
        }
        TextView index = convertView.findViewById(R.id.index);
        ImageView image = convertView.findViewById(R.id.image);
        TextView content = convertView.findViewById(R.id.method);

        index.setText("第"+strings[position]+"步");
        Resources res = context.getResources();
        int resId = res.getIdentifier(data.get(position).img, "mipmap",context.getPackageName());
        image.setImageResource(resId);

        content.setText(data.get(position).content);

        return convertView;
    }


}
