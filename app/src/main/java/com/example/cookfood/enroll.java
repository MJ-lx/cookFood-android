package com.example.cookfood;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookfood.ui.caipu.PutoutCaipu;
import com.example.cookfood.ui.my.MyFragment;

public class enroll extends AppCompatActivity {

    private TextView mTextView;
    private Button loginBtn;
    private Button enrollBtn;
    private EditText phonetxt;
    private EditText passwordtxt;
    private EditText password2txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        enrollBtn = findViewById(R.id.enroll);
        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonetxt = findViewById(R.id.phone);
                passwordtxt = findViewById(R.id.password);
                password2txt = findViewById(R.id.againpassword);

                String str1 = String.valueOf(passwordtxt.getText());
                String str2 = String.valueOf(password2txt.getText());
                String phone = String.valueOf(phonetxt.getText());
                if(!phone.equals("")&&!str1.equals("")&&!str2.equals("")){

                    if(str1.equals(str2)){
                        //插入数据到数据库
                        SQLiteOpenHelper helper = CookSqlite.getInstance(enroll.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        if(db.isOpen()){//数据库成功打开
                        //一个类似于Map的键值对存储结构
                            Cursor cursor = db.query("users",new String[]{"phone","password"},"phone="+phone,null,null,null,null);
                        //判断是否注册
                            if(cursor == null || !cursor.moveToNext()){
                                ContentValues values=new ContentValues();
                                //把数据加入到存储列表
                                values.put("phone", phone);
                                values.put("password",str1);
                                //执行插入，第二个参数是当values列表出现空值时候的替补数据
                                db.insert("users", null, values);
                                db.close();

                                //创建意图对象
                                Intent intent = new Intent(enroll.this, MainActivity.class);
                                //设置传递键值对
                                intent.putExtra("user_id",String.valueOf(phonetxt.getText()));
                                intent.putExtra("id",4);
                                //激活意图
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(enroll.this,"此账号已注册！",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else {
                        Toast.makeText(enroll.this,"两次密码输入不正确！无法实现注册",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(enroll.this,Login.class));
            }
        });
        password2txt = findViewById(R.id.againpassword);
        password2txt.clearFocus();
        if(password2txt.getTag() instanceof TextWatcher){
            password2txt.removeTextChangedListener((TextWatcher) (password2txt.getTag()));
        }
        //文本改变监听
        password2txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    passwordtxt = findViewById(R.id.password);
                    if(String.valueOf(s).equals(String.valueOf(passwordtxt.getText()))){
                        Toast.makeText(enroll.this,"两次密码输入不正确！无法实现注册",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}