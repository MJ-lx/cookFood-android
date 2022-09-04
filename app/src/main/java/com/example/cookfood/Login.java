package com.example.cookfood;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookfood.ui.caipu.PutoutCaipu;
import com.example.cookfood.ui.my.MyFragment;

public class Login extends AppCompatActivity {

    private Button enrollBtn;
    private Button loginBtn;
    private EditText phonetxt;
    private EditText passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        enrollBtn = findViewById(R.id.enroll);
        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,enroll.class));
            }
        });

        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonetxt = findViewById(R.id.phone);
                passwordtxt = findViewById(R.id.password);
                //判断密码是否输入正确
                SQLiteOpenHelper helper = CookSqlite.getInstance(Login.this);
                SQLiteDatabase db = helper.getReadableDatabase();
                if(db.isOpen()){//数据库成功打开
                    String id = String.valueOf((phonetxt.getText()));
                    Cursor cursor = db.query("users",new String[]{"phone","password"},"phone="+id,null,null,null,null);
//                    判断是否注册
                    if(cursor == null || !cursor.moveToNext()){
                        Toast.makeText(Login.this,"此账号未注册！",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(cursor.getString(cursor.getColumnIndex("password")).equals(String.valueOf(passwordtxt.getText()))){
                            cursor.close();
                            db.close();
                            //创建意图对象
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            //设置传递键值对
                            intent.putExtra("user_id",String.valueOf(phonetxt.getText()));
                            intent.putExtra("id",4);
                            //激活意图
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Login.this,"密码输入错误",Toast.LENGTH_SHORT).show();
                        }
                    }


                }

            }
        });
    }

}