package com.example.cookfood.ui.my;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cookfood.CookSqlite;
import com.example.cookfood.MyListView;
import com.example.cookfood.R;
import com.example.cookfood.lanziClass;
import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.caipu.NeedFood;

import java.util.List;

public class ListItemAdapterCaiList extends BaseAdapter {

    private final Context context;
    private List<lanziClass> data;
    private List<NeedFood> data_food;
    public ListItemAdapterCaiList(List<lanziClass> data, Context context){
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

        convertView = LayoutInflater.from(context).inflate(R.layout.cailanzi_item1,parent,false);

        TextView food = convertView.findViewById(R.id.food_title);
        food.setText(data.get(position).title);
        TextView tag = convertView.findViewById(R.id.tags);
        tag.setText(data.get(position).id);

        Button delBtn = convertView.findViewById(R.id.delete);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View convertView = LayoutInflater.from(context).inflate(R.layout.cailanzi_item1,parent,false);
                SQLiteOpenHelper helper = CookSqlite.getInstance(convertView.getContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                //删除
                if(db.isOpen()){
                    db.execSQL("delete from lanzis where _id =?",new Object[]{data.get(position).id});
                    db.close();
                }
                data.remove(position);
                notifyDataSetChanged();
            }
        });



        LinearLayout linear = convertView.findViewById(R.id.food_list);
        //食材列表
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(linear.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);
        //传输数据
        SQLiteOpenHelper helper = CookSqlite.getInstance(convertView.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        TextView textView = convertView.findViewById(R.id.tags);
        String str = String.valueOf(textView.getText());
        if(str!=null){
            if(db.isOpen()) {//数据库成功打开
                Cursor cursor = db.query("lanzis", new String[]{"userid","caipuid"}, "_id=" + str, null, null, null, null);
                if (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("caipuid"));
                    CaiPuClass data = new CaiPuClass(cursor.getString(cursor.getColumnIndex("userid")));

                    Cursor cursor1 = db.query("caipus", new String[]{"foods"}, "_id=" + id, null, null, null, null);
                    cursor1.moveToNext();

                    data.getFoodList(cursor1.getString(cursor1.getColumnIndex("foods")));
                    data_food = data.food;
                    cursor1.close();
                }
                cursor.close();
            }

            db.close();
        }

        listView.setAdapter(new ListItemAdapterCaiFood(data_food,linear.getContext()));
        linear.addView(listView);

        return convertView;
    }


}
