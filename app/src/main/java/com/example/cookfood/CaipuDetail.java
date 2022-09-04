package com.example.cookfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.caipu.FoodListItemAdapter;
import com.example.cookfood.ui.caipu.MethodClass;
import com.example.cookfood.ui.caipu.MethodListItemAdapter;
import com.example.cookfood.ui.caipu.NeedFood;
import com.google.android.material.tabs.TabLayout;

import static android.view.View.VISIBLE;

public class CaipuDetail extends AppCompatActivity {

    private ImageView backBtn;
    public CaiPuClass data;
    private TableLayout tablelayout;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caipudetail);

        backBtn = findViewById(R.id.detail_back);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            Log.e("Exception when sendKeyDownUpSync",
                                    e.toString());
                        }
                    }
                }.start();
            }
        });

        String id = getIntent().getStringExtra("caipu_id");
        String user_id = getIntent().getStringExtra("user_id");
        SQLiteOpenHelper helper = CookSqlite.getInstance(CaipuDetail.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        if(db.isOpen()) {//数据库成功打开
            Cursor cursor = db.query("caipus", new String[]{"time", "title", "imgurls", "content", "foods", "userid"}, "_id=" + id, null, null, null, null);
            if (cursor.moveToNext()) {
                data = new CaiPuClass(cursor.getString(cursor.getColumnIndex("userid")));
                data.title = cursor.getString(cursor.getColumnIndex("title"));
                data.time = cursor.getString(cursor.getColumnIndex("time"));
                data.getFoodList(cursor.getString(cursor.getColumnIndex("foods")));
                data.getMethodList(cursor.getString(cursor.getColumnIndex("imgurls")), cursor.getString(cursor.getColumnIndex("content")));
                Cursor cursor1 = db.query("users",new String[]{"phone","name"},"phone="+cursor.getString(cursor.getColumnIndex("userid")),null,null,null,null);
                if(cursor1.moveToNext()) {
                    if (cursor1.getString(cursor1.getColumnIndex("name")) == null) {
                        data.name = data.userId;
                    } else {
                        data.name = cursor1.getString(cursor1.getColumnIndex("name"));
                    }
                }
                else {
                    data.name = data.userId;
                }
                cursor1.close();
            }
            cursor.close();
    }
        db.close();
        ImageView img_title = findViewById(R.id.title_img);
        Resources res = getResources();
        int resId = res.getIdentifier(data.titleimg, "mipmap",getPackageName());
        img_title.setImageResource(resId);
        TextView title = findViewById(R.id.title);
        title.setText(data.title);
        TextView user_name = findViewById(R.id.user_name);
        user_name.setText(data.name);

        tablelayout = findViewById(R.id.foodlist);
//创建食材列表
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(tablelayout.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);
        //传输数据
        //初始化数据
        listView.setAdapter(new ListItemAdapterFood(data.food,tablelayout.getContext()));
        tablelayout.addView(listView);

        //创建制作步骤列表
        linear = findViewById(R.id.steps);

        //自定义ListView，消除与ScrollView的冲突
        MyListView listView2 = new MyListView(linear.getContext());
        //消除item自带的分割线
        listView2.setDividerHeight(0);

        //初始化数据
        listView2.setAdapter(new ListItemAdapterMethod(data.imglist,linear.getContext()));
        linear.addView(listView2);

        Button addBtn = findViewById(R.id.add_lanzi);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper helper = CookSqlite.getInstance(CaipuDetail.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                //把数据加入到存储列表
                values.put("userid", user_id);
                values.put("caipuid", id);
                //执行插入，第二个参数是当values列表出现空值时候的替补数据
                db.insert("lanzis", null, values);
                db.close();
                addBtn.setVisibility(View.INVISIBLE);
            }
        });
    }
}