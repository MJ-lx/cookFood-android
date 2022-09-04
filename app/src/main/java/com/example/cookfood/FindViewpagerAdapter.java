package com.example.cookfood;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class FindViewpagerAdapter extends PagerAdapter {

    private List<View> homeListView;
    public FindViewpagerAdapter(List<View> homeListView){
        this.homeListView = homeListView;
    }
    private String[] mTitles = new String[]{"最新", "最热"};


    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(homeListView.get(position),0);
        return homeListView.get(position);
    }

    @Override
    public int getCount() {
        return homeListView.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(homeListView.get(position));
    }
}
