package com.example.cookfood.ui.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.cookfood.R;
import com.example.cookfood.CollectViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectFragment extends Fragment {

    private CollectViewModel collectViewModel;
    private TabLayout mTabLayout;

    private TabLayout.Tab one;
    private TabLayout.Tab two;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        collectViewModel =
                new ViewModelProvider(this).get(CollectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_collect, container, false);
        collectViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        //滑动的viewpager
        LayoutInflater lf = getLayoutInflater();
        View view_group = lf.inflate(R.layout.collect_group,null);
        View view_caipu = lf.inflate(R.layout.collect_caipu,null);

        //添加数据集合
        List<View> viewList = new ArrayList<>();
        viewList.add(view_group);
        viewList.add(view_caipu);

        ViewPager collectpager = root.findViewById(R.id.collect_viewpager);
        CollectViewpagerAdapter viewpagerAdapter = new CollectViewpagerAdapter(viewList);
        collectpager.setAdapter(viewpagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) root.findViewById(R.id.collect_tabLayout);
        mTabLayout.setupWithViewPager(collectpager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);

        collectpager.setCurrentItem(1);
        return root;
    }
}