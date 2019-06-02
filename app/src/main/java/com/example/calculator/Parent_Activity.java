package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

public class Parent_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityparent);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addItem(new FinanceActivity());
        adapter.addItem(new MainActivity());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    ((DrawerLayout) findViewById(R.id.drawer)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = getCurrentFocus();
                    if (view == null) {
                        view = new View(Parent_Activity.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    ((DrawerLayout) findViewById(R.id.drawer)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.BOTTOM | Gravity.END);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    class Adapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }


        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Calculator";
            } else {
                return "Finance";
            }
        }
    }
}
