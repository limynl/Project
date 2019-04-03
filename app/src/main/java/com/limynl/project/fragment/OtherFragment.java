package com.limynl.project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limynl.project.R;
import com.limynl.project.adapter.MyFragmentPagerAdapter;
import com.limynl.project.base.LazyLoadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class OtherFragment extends LazyLoadFragment {
    @BindView(R.id.timeline_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.timeline_viewpager)
    ViewPager viewPager;

    private Unbinder unbinder;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_other;
    }

    @Override
    protected void lazyLoad() {
        mContext = getActivity();
        initTabLayout();
    }

    private void initTabLayout() {
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getFragmentManager(), mContext);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
