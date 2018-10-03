package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.fragment.MeFragment;
import com.limynl.project.fragment.OtherFragment;
import com.limynl.project.fragment.SocialFragment;
import com.limynl.project.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import be.webelite.ion.IconView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends TopBarBaseActivity implements View.OnClickListener {

    @BindView(R.id.id_view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.id_tab_task_img)
    IconView tabTaskImg;
    @BindView(R.id.id_tab_task_text)
    TextView tabTaskText;
    @BindView(R.id.id_tab_rank_img)
    IconView tabRankImg;
    @BindView(R.id.id_tab_rank_text)
    TextView tabRankText;
    @BindView(R.id.id_tab_me_img)
    IconView tabMeImg;
    @BindView(R.id.id_tab_me_text)
    TextView tabMeText;

    private FragmentPagerAdapter adapter;
    private List<Fragment> listFragment;

    private OtherFragment otherFragment;
    private SocialFragment socialFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initEvent();
        viewPager.setCurrentItem(3);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_tab_me));
        UserDbHelper.setInstance(this);
    }

    private void initEvent() {
        //禁止viewPager左右滑动
        viewPager.setNoScroll(true);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initView() {
        listFragment = new ArrayList<>();
        otherFragment = new OtherFragment();
        socialFragment = new SocialFragment();
        meFragment = new MeFragment();
        listFragment.add(otherFragment);
        listFragment.add(socialFragment);
        listFragment.add(meFragment);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }
        };
        viewPager.setAdapter(adapter);

    }

    @OnClick({R.id.id_tab_task, R.id.id_tab_rank, R.id.id_tab_me})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_task:
                setTitle(getString(R.string.str_tab_task));
                isShowToolBar(true);
                viewPager.setCurrentItem(0);
                break;
            case R.id.id_tab_rank:
                setTitle(getString(R.string.str_tab_rank));
                isShowToolBar(true);
                viewPager.setCurrentItem(1);
                break;
            case R.id.id_tab_me:
                setTitle(getString(R.string.str_tab_me));
                isShowToolBar(true);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    private void setSelect(int i) {
        setTab(i);
    }

    private void setTab(int i) {
        resetImg();
        switch (i) {
            case 0:
                tabTaskImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabTaskText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
            case 1:
                tabRankImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabRankText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
            case 2:
                tabMeImg.setTextColor(getResources().getColor(R.color.colorTheme2));
                tabMeText.setTextColor(getResources().getColor(R.color.colorTheme2));
                break;
        }
    }

    /**
     * 将所有图片切换成暗色
     */
    private void resetImg() {
        tabTaskImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabTaskText.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabRankImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabRankText.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabMeImg.setTextColor(getResources().getColor(R.color.colorTextHint));
        tabMeText.setTextColor(getResources().getColor(R.color.colorTextHint));
    }

    private boolean mIsExit;

    //双击返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                UserDbHelper.getInstance().saveLoginState(true);
                this.finish();
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constants.ACTIVITY_PUBLISH:
                int id = data.getIntExtra(Constants.GO_INDEX, R.id.id_tab_rank);
                // 非导航本身事件，手动切换
                setTitle(getString(R.string.str_tab_rank));
                isShowToolBar(true);
                viewPager.setCurrentItem(1);
                break;
            case Constants.ACTIVITY_PERSONAL:
//                mMineFragment.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        ActivityCollector.finishAll();
    }
}
