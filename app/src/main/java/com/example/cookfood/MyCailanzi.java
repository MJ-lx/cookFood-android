package com.example.cookfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.my.ListItemAdapterCaiList;

import java.util.ArrayList;

public class MyCailanzi extends AppCompatActivity {

    private LinearLayout linear;
    private ArrayList<lanziClass> data = new ArrayList<lanziClass>();
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cailanzi);

        backBtn = findViewById(R.id.back);
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


        linear = findViewById(R.id.list);
        //食材列表
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(linear.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);

        SQLiteOpenHelper helper = CookSqlite.getInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("user_id");
        if (db.isOpen()) {//数据库成功打开
            Cursor cursor = db.query("lanzis", new String[]{"_id","caipuid"}, "userid="+str, null, null, null, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("caipuid"));

                Cursor cursor1 = db.query("caipus", new String[]{"title"}, "_id=" + id, null, null, null, null);
                lanziClass lanzi = new lanziClass();
                cursor1.moveToNext();
                lanzi.id =String.valueOf(cursor.getInt(cursor.getColumnIndex("_id")));
                lanzi.title = cursor1.getString(cursor1.getColumnIndex("title"));
                data.add(lanzi);

                cursor1.close();
            }
            cursor.close();
            db.close();
        }

        listView.setAdapter(new ListItemAdapterCaiList(data,linear.getContext()));
        linear.addView(listView);
    }

}