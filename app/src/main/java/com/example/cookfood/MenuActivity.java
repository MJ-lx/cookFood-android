package com.example.cookfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class MenuActivity extends AppCompatActivity {
    private ArrayList<Fragment> mFragmentsList = new ArrayList<>();
    private VerticalTabLayout mTablayout;
    private ViewPager mViewpager;
    private String[] mTitles = new String[]{"热门", "一日三餐", "蔬菜","肉食","水产海鲜","菜式菜系","甜品烘焙"};
    private ImageButton backBtn;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mTablayout = findViewById(R.id.menu_tab);
        mViewpager = findViewById(R.id.menu_viewpager);
        initView();
        initData();

//        返回按钮点击事件
        backBtn = findViewById(R.id.menu_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                //子线程
                new Thread() {
                    public void run() {
                        //错误捕获
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

        //搜索按钮
        searchBtn = findViewById(R.id.search);
        searchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Search.class);
                startActivity(intent);
            }
        });
    }

    //    初始化控件
    private void initView() {
        mTablayout = findViewById(R.id.menu_tab);
        for(int i = 0 ;i<mTitles.length;i++){
            mFragmentsList.add(MenuFragment.newInstance(i+1));
        }


    }

    //    初始化数据
    private void initData() {
        //适配器
        MenuViewpagerAdapter menuViewpagerAdapter = new MenuViewpagerAdapter(getSupportFragmentManager(),mTitles);
        mViewpager.setAdapter(menuViewpagerAdapter);
        //进行关联
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setupWithFragment(getSupportFragmentManager(),mFragmentsList);
        mTablayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                ITabView.TabTitle title = new ITabView.TabTitle.Builder()
                        .setContent(mTitles[position])
                        .setTextColor(getResources().getColor(R.color.orange),getResources().getColor(R.color.gray))
                        .setTextSize(20)//设置数据   也有设置字体颜色的方法
                        .build();
                return title;
            }

            @Override
            public int getBackground(int position) {
                return R.drawable.tab_selector;//选中的背景颜色
            }
        });

    }

}
