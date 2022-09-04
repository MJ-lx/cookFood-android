package com.example.cookfood;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MenuViewpagerAdapter extends FragmentPagerAdapter {
        private String[] list;

        public  MenuViewpagerAdapter(FragmentManager fm, String[] list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return MenuFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list[position];
        }

}
