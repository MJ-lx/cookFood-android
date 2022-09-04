package com.example.cookfood.ui.caipu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.cookfood.CaipuDetail;
import com.example.cookfood.CookSqlite;
import com.example.cookfood.ListItemAdapter;
import com.example.cookfood.MainActivity;
import com.example.cookfood.MyListView;
import com.example.cookfood.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PutoutCaipu extends AppCompatActivity {

    private ImageButton putoutBackBtn;
    private Button okBtn;
    private Button checkBtn;
    private Button addFoodBtn;
    private Button addMethodBtn;
    private TableLayout tablelayout;
    private LinearLayout linear;

    private List<NeedFood> needFood = new ArrayList<NeedFood>();
    private List<MethodClass> methods = new ArrayList<MethodClass>();
    private CaiPuClass data = new CaiPuClass("userid");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putoutcaipu);

        tablelayout = findViewById(R.id.add_food);
//创建食材列表
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(tablelayout.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);
        //传输数据

        //初始化数据
        for(int i=0;i<3;i++){
            data.food.add(new NeedFood());
        }
        listView.setAdapter(new FoodListItemAdapter(data.food,tablelayout.getContext()));
        tablelayout.addView(listView);
        //点击添加食材列表
        addFoodBtn = findViewById(R.id.add_food_btn);
        addFoodBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int length = data.food.size()+1;
                data.food.add(new NeedFood());
                listView.setAdapter(new FoodListItemAdapter(data.food,tablelayout.getContext()));
            }
        });

        //添加封面图片
        ImageButton titleImg = findViewById(R.id.title_img);
        titleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请权限
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                        .WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PutoutCaipu.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    //执行启动相册的方法
                    openAlbum();
                }
            }
        });
        linear = findViewById(R.id.method);
        //创建制作步骤列表
        //自定义ListView，消除与ScrollView的冲突
        MyListView listView2 = new MyListView(linear.getContext());
        //消除item自带的分割线
        listView2.setDividerHeight(0);

        //初始化数据
        data.imglist.add(new MethodClass());
        listView2.setAdapter(new MethodListItemAdapter(data.imglist,linear.getContext()));
        linear.addView(listView2);
        //点击添加步骤按钮
        addMethodBtn = findViewById(R.id.add_method);
        addMethodBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int length = data.imglist.size()+1;
                data.imglist.add(new MethodClass());
                listView2.setAdapter(new MethodListItemAdapter(data.imglist,linear.getContext()));
            }
        });

        //点击返回按钮
        putoutBackBtn = findViewById(R.id.putoutcaipu_back);
        putoutBackBtn.setOnClickListener(new View.OnClickListener() {

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
        //点击发布按钮提交数据，返回首页
        okBtn = findViewById(R.id.putout_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                data.time = String.valueOf(date.getTime());
                EditText et = findViewById(R.id.title);
                data.title = String.valueOf(et.getText());
                if(getDate(listView,listView2)){
                    addDateToDB();
                }
                startActivity(new Intent(PutoutCaipu.this, MainActivity.class));
            }
        });
        //点击预览按钮，将数据临时存储并显示在展示页上
        checkBtn = findViewById(R.id.putout_check);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PutoutCaipu.this, CaipuDetail.class));
            }
        });
    }

    //获取权限的结果
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED) openAlbum();
            else Toast.makeText(getApplicationContext(),"你拒绝了",Toast.LENGTH_SHORT).show();
        }
    };

    //启动相册的方法
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        ActivityCompat.startActivityForResult(PutoutCaipu.this,intent,2,null);
    };


    public Boolean getDate(ListView lv1,ListView lv2){
        if(lv1.getChildCount()!=0&&lv2.getChildCount()!=0){
            for (int i = 0;i<lv1.getChildCount();i++){
                EditText et1 = lv1.getChildAt(i).findViewById(R.id.food);
                EditText et2 = lv1.getChildAt(i).findViewById(R.id.num);

                NeedFood fooditem = new NeedFood();
                fooditem.food = String.valueOf(et1.getText());
                fooditem.num = String.valueOf(et2.getText());
                needFood.add(fooditem);
            }
            for (int i = 0;i<lv2.getChildCount();i++){
                EditText et1 = lv2.getChildAt(i).findViewById(R.id.method1);
                ImageButton ib1 = lv2.getChildAt(i).findViewById(R.id.caipu_imgbtn1);

                MethodClass methoditem = new MethodClass();
                methoditem.content = String.valueOf(et1.getText());
                methoditem.img = String.valueOf(ib1.getBackground());
                methods.add(methoditem);
            }
            data.food = needFood;
            data.imglist = methods;
            Toast.makeText(PutoutCaipu.this,"发布成功！",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(PutoutCaipu.this,"发布失败！",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public void addDateToDB(){
        String food = "";
        String methodurl = data.titleimg;
        String methodtxt = "";
        for(int i=0;i<data.food.size();i++){
            food += data.food.get(i).food+":"+data.food.get(i).num;
            if(i!=data.food.size()-1){
                food+=";";
            }
        }
        for(int i=0;i<data.imglist.size();i++){
            methodurl += data.imglist.get(i).img;
            methodtxt = data.imglist.get(i).content;
            if(i!=data.imglist.size()-1){
                methodurl +=";";
                methodtxt +=";";
            }
        }
        SQLiteOpenHelper helper = CookSqlite.getInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()){//数据库成功打开
//一个类似于Map的键值对存储结构
            ContentValues values=new ContentValues();
//把数据加入到存储列表
            values.put("time", data.time);
            values.put("title", data.title);
            values.put("imgurls", methodurl);
            values.put("content", methodtxt);
            values.put("foods", food);
            values.put("userid", data.userId);
//执行插入，第二个参数是当values列表出现空值时候的替补数据
            db.insert("caipus", null, values);
        }
        db.close();
    }
}