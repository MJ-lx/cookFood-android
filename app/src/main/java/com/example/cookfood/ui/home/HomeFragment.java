package com.example.cookfood.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.cookfood.CaipuDetail;
import com.example.cookfood.CookSqlite;
import com.example.cookfood.HomeViewpagerAdapter;
import com.example.cookfood.ListItemAdapter;
import com.example.cookfood.Login;
import com.example.cookfood.MainActivity;
import com.example.cookfood.MenuActivity;
import com.example.cookfood.MyListView;
import com.example.cookfood.R;
import com.example.cookfood.Search;
import com.example.cookfood.itemAdapter;
import com.example.cookfood.MyGridView;
import com.example.cookfood.ui.caipu.CaiPuClass;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TabLayout mTabLayout;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private GridLayout detailGrid;
    private GridView gridView;
    private GridLayout grid;
    private LinearLayout linear;
    private EditText search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        // ????????????
        ImageButton menuBtn = root.findViewById(R.id.home_menu);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MenuActivity.class));
            }
        });
        //????????????
        search = root.findViewById(R.id.search);
        search.clearFocus();//????????????????????????
        search.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // ???????????????????????????????????????
                    startActivity(new Intent(getActivity(), Search.class));

                } else {
                    // ???????????????????????????????????????

                }
            }
        });

        //?????????viewpager
        LayoutInflater lf = getLayoutInflater();
        View view_attention = lf.inflate(R.layout.home_attention,null);
        View view_recommend = lf.inflate(R.layout.home_recommend,null);
        View view_video = lf.inflate(R.layout.home_video,null);
        view_recommend = initRecommand(view_recommend);
        view_attention = initAttention(view_attention);
        //??????????????????
        List<View> viewList = new ArrayList<>();
        viewList.add(view_attention);
        viewList.add(view_recommend);
        viewList.add(view_video);

        ViewPager homepager = root.findViewById(R.id.home_viewpager);
        HomeViewpagerAdapter viewpagerAdapter = new HomeViewpagerAdapter(viewList);
        homepager.setAdapter(viewpagerAdapter);
        //???TabLayout???ViewPager???????????????
        mTabLayout = (TabLayout) root.findViewById(R.id.home_tabLayout);
        mTabLayout.setupWithViewPager(homepager);

        //??????Tab?????????
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);

        homepager.setCurrentItem(1);
        EditText searEdit = root.findViewById(R.id.search);
        searEdit.clearFocus();
        return root;
    }


    public View initRecommand(View root){
        grid = root.findViewById(R.id.gridAdd);

        //?????????GridView????????????ScrollView?????????
        gridView = new MyGridView(root.getContext());
        gridView.setNumColumns(2);

        ScrollView sv = (ScrollView)root.findViewById(R.id.scrollView);
        sv.smoothScrollTo(0,0);

        //????????????
        List<CaiPuClass> data = new ArrayList<CaiPuClass>();
//        ???????????????????????????
        SQLiteOpenHelper helper = CookSqlite.getInstance(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        if(db.isOpen()){//?????????????????????
        Cursor cursor = db.query("caipus",new String[]{"_id","time","title","imgurls","content","foods","userid","likes","collect"},null,null,null,null,null);

            while (cursor.moveToNext()) {
                CaiPuClass caipu = new CaiPuClass(cursor.getString(cursor.getColumnIndex("userid")));
                caipu.title = cursor.getString(cursor.getColumnIndex("title"));
                caipu.time = cursor.getString(cursor.getColumnIndex("time"));
                caipu.getFoodList(cursor.getString(cursor.getColumnIndex("foods")));
                caipu.getMethodList(cursor.getString(cursor.getColumnIndex("imgurls")),cursor.getString(cursor.getColumnIndex("content")));
                caipu.likes = cursor.getString(cursor.getColumnIndex("likes"));
                caipu.collect = cursor.getString(cursor.getColumnIndex("collect"));
                caipu.id = cursor.getInt(cursor.getColumnIndex("_id"));

                Cursor cursor1 = db.query("users",new String[]{"phone","name"},"phone="+cursor.getString(cursor.getColumnIndex("userid")),null,null,null,null);
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
                data.add(caipu);
                cursor1.close();
            }

            cursor.close();
            db.close();
        }
        //?????????????????????
        Intent intent = getActivity().getIntent();
        String user_id = intent.getStringExtra("user_id");
        gridView.setAdapter(new itemAdapter(data,root.getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //??????????????????
                Intent intent = new Intent(getActivity(), CaipuDetail.class);
                //?????????????????????
                TextView tag = view.findViewById(R.id.tags);
                intent.putExtra("caipu_id",String.valueOf(tag.getText()));
                intent.putExtra("user_id",user_id);
                //????????????
                startActivity(intent);
            }
        });
        grid.addView(gridView);

        search.clearFocus();//????????????????????????
        return root;
    }

    public View initAttention(View root){
        linear = root.findViewById(R.id.attention_add);

        //?????????ListView????????????ScrollView?????????
        MyListView listView = new MyListView(root.getContext());
        //??????item??????????????????
        listView.setDividerHeight(0);
        //????????????
        List<String> data = new ArrayList<String>();
        for(int i=0;i<9;i++){
            data.add(String.valueOf(i));
        }

        listView.setAdapter(new ListItemAdapter(data,root.getContext()));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(getActivity(),CaipuDetail.class));
//            }
//        });
        linear.addView(listView);
        search.clearFocus();//????????????????????????
        return root;
    }
}