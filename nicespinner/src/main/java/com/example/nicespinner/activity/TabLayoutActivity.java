package com.example.nicespinner.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.nicespinner.MyPagerAdapter;
import com.example.nicespinner.R;
import com.example.nicespinner.fragment.BasicInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * TabLayout使用
 * Created by dingxujun on 2018/2/5.
 *
 * @project MyApplication
 */

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<String> mDatas;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        initData();
        initFragments();
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mDatas, fragments);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(1);
        mTablayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTablayout.setTabsFromPagerAdapter(myPagerAdapter);//给Tabs设置适配器,可不写

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'E'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            BasicInfoFragment fragment = BasicInfoFragment.newInstance(mDatas.get(i));
            fragments.add(fragment);
        }
    }
}

