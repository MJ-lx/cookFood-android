package com.example.cookfood.ui.find;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.cookfood.FindViewpagerAdapter;
import com.example.cookfood.HomeViewpagerAdapter;
import com.example.cookfood.R;
import com.example.cookfood.ui.my.MyViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {
    private FindViewModel notificationsViewModel;
    private TabLayout mTabLayout;
    private TabLayout.Tab one;
    private TabLayout.Tab two;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(FindViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });
        //滑动的viewpager
        LayoutInflater lf = getLayoutInflater();
        View view_newest = lf.inflate(R.layout.find_newest,null);
        View view_hotest = lf.inflate(R.layout.find_hotest,null);

        //添加数据集合
        List<View> viewList = new ArrayList<>();
        viewList.add(view_newest);
        viewList.add(view_hotest);

        ViewPager findpager = root.findViewById(R.id.find_viewpager);
        FindViewpagerAdapter viewpagerAdapter = new FindViewpagerAdapter(viewList);
        findpager.setAdapter(viewpagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) root.findViewById(R.id.find_tabLayout);
        mTabLayout.setupWithViewPager(findpager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);

        findpager.setCurrentItem(1);
        return root;
    }
}

