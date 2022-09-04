package com.example.cookfood.ui.caipu;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cookfood.R;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

public class FoodListItemAdapter extends BaseAdapter {
    private final Context context;
    private List<NeedFood> data;
    private List<Map<String,String> > mdata;
    public FoodListItemAdapter(List<NeedFood> data, Context context){
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
    private Integer index = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final NeedFood needFoods = data.get(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //清除焦点
        holder.food.clearFocus();
        holder.num.clearFocus();

        //先清除之前的文本改变监听
        if (holder.food.getTag() instanceof TextWatcher) {
            holder.food.removeTextChangedListener((TextWatcher) (holder.food.getTag()));
        }
        if (holder.num.getTag() instanceof TextWatcher) {
            holder.num.removeTextChangedListener((TextWatcher) (holder.num.getTag()));
        }

        //设置数据
        holder.food.setText(TextUtils.isEmpty(needFoods.food)? "":needFoods.food);
        holder.num.setText(TextUtils.isEmpty(needFoods.num)? "":needFoods.num);

        //文本改变监听
        final TextWatcher FoodWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    needFoods.food = null;
                } else {
                   needFoods.food = String.valueOf(s);
                }
            }
        };
        final TextWatcher NumWatcher =  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    needFoods.num = null;
                } else {
                    needFoods.num = String.valueOf(s);
                }
            }
        };

        //把监听设置到不同的EditText上
        holder.food.addTextChangedListener(FoodWatcher);
        holder.food.setTag(FoodWatcher);

        holder.num.addTextChangedListener(NumWatcher);
        holder.num.setTag(NumWatcher);

        return convertView;
    }
    public final class ViewHolder {
        public EditText food;// ListView中的输入
        public EditText num;// ListView中的输入
        public ViewHolder(View convertView){
            food = (EditText) convertView.findViewById(R.id.food);
            num = (EditText) convertView.findViewById(R.id.num);
        }
    }

}
