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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.home.ListItemAdapterSearch;

import java.util.ArrayList;

public class MenuSearch extends AppCompatActivity {

    private EditText searchTxt;
    private ArrayList<CaiPuClass> data = new ArrayList<CaiPuClass>();
    private LinearLayout linear;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search);

        //获取传输数据
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        //搜索功能
        searchTxt = findViewById(R.id.search);
        searchTxt.setText(key);
        searchTxt.clearFocus();
        searchTxt.setEnabled(false);
        searchResult(key);

        linear = findViewById(R.id.search_list);
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(linear.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);
        //传输数据
        //初始化数据
        listView.setAdapter(new ListItemAdapterSearch(data, linear.getContext()));
        linear.addView(listView);

        //返回按钮
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
    }

    public void searchResult(String key) {

        SQLiteOpenHelper helper = CookSqlite.getInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "select _id, title, imgurls, userid from caipus where title like '%" + key + "%'";//注意：这里有单引号
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CaiPuClass caipu = new CaiPuClass(cursor.getString(cursor.getColumnIndex("userid")));

            caipu.id = cursor.getInt(cursor.getColumnIndex("_id"));
            caipu.title = cursor.getString(cursor.getColumnIndex("title"));
            caipu.getMethodList(cursor.getString(cursor.getColumnIndex("imgurls")), null);
            Cursor cursor1 = db.query("users", new String[]{"phone", "name"}, "phone=" + caipu.userId, null, null, null, null);
            if (cursor1.moveToNext()) {
                if (cursor1.getString(cursor1.getColumnIndex("name")) == null) {
                    caipu.name = caipu.userId;
                } else {
                    caipu.name = cursor1.getString(cursor1.getColumnIndex("name"));
                }
            } else {
                caipu.name = caipu.userId;
            }
            cursor1.close();
            data.add(caipu);
        }
        cursor.close();
        db.close();
    }
}