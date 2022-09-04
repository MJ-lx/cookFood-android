package com.example.cookfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cookfood.ui.caipu.CaiPuClass;
import com.example.cookfood.ui.home.ListItemAdapterSearch;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private Button searchBtn;
    private EditText searchTxt;
    private ArrayList<CaiPuClass> data = new ArrayList<CaiPuClass>();
    private LinearLayout linear;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchTxt = findViewById(R.id.search);

        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTxt.clearFocus();
                data.clear();
//                输入框失去焦点
                searchBtn.setFocusableInTouchMode(true);
                searchBtn.setFocusable(true);
                searchBtn.requestFocus();
//                将输入法隐藏
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchTxt.getWindowToken(),0);
                searchResult(String.valueOf(searchTxt.getText()));
            }
        });

        linear = findViewById(R.id.search_list);
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(linear.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);
        //传输数据
        //初始化数据
        listView.setAdapter(new ListItemAdapterSearch(data,linear.getContext()));
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

    public void searchResult(String key){

        SQLiteOpenHelper helper = CookSqlite.getInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "select _id, title, imgurls, userid from caipus where title like '%"+key+"%'";//注意：这里有单引号
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            CaiPuClass caipu = new CaiPuClass(cursor.getString(cursor.getColumnIndex("userid")));

            caipu.id = cursor.getInt(cursor.getColumnIndex("_id"));
            caipu.title = cursor.getString(cursor.getColumnIndex("title"));
            caipu.getMethodList(cursor.getString(cursor.getColumnIndex("imgurls")),null);
            Cursor cursor1 = db.query("users",new String[]{"phone","name"},"phone="+caipu.userId,null,null,null,null);
            if(cursor1.moveToNext()) {
                if (cursor1.getString(cursor1.getColumnIndex("name")) == null) {
                    caipu.name = caipu.userId;
                } else {
                    caipu.name = cursor1.getString(cursor1.getColumnIndex("name"));
                }
            }
            else {
                caipu.name = caipu.userId;
            }
            cursor1.close();
            data.add(caipu);
        }
        cursor.close();
        db.close();
    }
}