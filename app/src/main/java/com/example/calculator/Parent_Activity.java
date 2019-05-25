package com.example.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
        adapter.addItem(new MainActivity());
        adapter.addItem(new FinanceActivity());

        viewPager.setAdapter(adapter);
    }

    class Adapter extends FragmentPagerAdapter{
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
        public CharSequence getPageTitle (int position) {

            if(position == 0){
                return "Calculator";
            }else{
                return "Finance";
            }
        }
    }
}
