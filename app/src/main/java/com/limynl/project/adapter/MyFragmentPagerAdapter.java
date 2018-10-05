package com.limynl.project.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.limynl.project.fragment.other.GwFragment;
import com.limynl.project.fragment.other.JkFragment;
import com.limynl.project.fragment.other.ShFragment;
import com.limynl.project.fragment.other.SsFragment;
import com.limynl.project.fragment.other.YuLeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * email 1434117404@qq.com
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tableTitle = new String[] {"娱乐","购物","生活","时尚","健康"};
    private Context mContext;
    private List<Fragment> mFragmentTab;
    private GwFragment gwFragment;
    private JkFragment jkFragment;
    private ShFragment shFragment;
    private SsFragment ssFragment;
    private YuLeFragment yuLeFragment;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragmentTab();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentTab.get(position);
    }

    @Override
    public int getCount() {
        return tableTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tableTitle[position];
    }

    private void initFragmentTab() {
        gwFragment = new GwFragment();
        jkFragment = new JkFragment();
        shFragment = new ShFragment();
        ssFragment = new SsFragment();
        yuLeFragment = new YuLeFragment();

        mFragmentTab = new ArrayList<Fragment>();
        mFragmentTab.add(yuLeFragment);
        mFragmentTab.add(gwFragment);
        mFragmentTab.add(shFragment);
        mFragmentTab.add(ssFragment);
        mFragmentTab.add(jkFragment);
    }
}
