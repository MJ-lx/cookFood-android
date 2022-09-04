package com.example.cookfood;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class CollectViewpagerAdapter extends PagerAdapter {
    private List<View> collectListView;
    public CollectViewpagerAdapter(List<View> collectListView){
        this.collectListView = collectListView;
    }
    private String[] mTitles = new String[]{"分组", "菜谱"};


    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(collectListView.get(position),0);
        return collectListView.get(position);
    }

    @Override
    public int getCount() {
        return collectListView.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(collectListView.get(position));
    }
}
