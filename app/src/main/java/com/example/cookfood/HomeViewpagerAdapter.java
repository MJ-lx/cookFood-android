package com.example.cookfood;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class HomeViewpagerAdapter extends PagerAdapter {

    private List<View> homeListView;
    public HomeViewpagerAdapter(List<View> homeListView){
        this.homeListView = homeListView;
    }
    private String[] mTitles = new String[]{"好友", "推荐", "视频"};


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
