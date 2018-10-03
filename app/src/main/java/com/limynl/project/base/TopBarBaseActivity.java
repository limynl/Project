package com.limynl.project.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.limynl.project.R;
import com.limynl.project.utils.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.cl.library.view.LoadingDialog;

/**
 * email 1434117404@qq.com
 */

public abstract class TopBarBaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    private Toolbar toolbar;
    private FrameLayout viewContent;
    private TextView tvTitle;
    private OnClickListener onClickListenerTopLeft;
    private OnClickListener onClickListenerTopRight;
    private boolean showMenu;
    private String menuStr;

    public interface OnClickListener {
        void onClick();
    }

    //加载动画
    private LoadingDialog mLoading;

    public void setLoading(){
        setLoading(getString(R.string.lib_dialog_loading));
    }

    public void setLoading(@StringRes int msgId){
        setLoading(getString(msgId));
    }

    public void setLoading(CharSequence msg){
        mLoading = new LoadingDialog(this, msg);
    }

    public void showLoading() {
        if (mLoading == null) return;
        if (mLoading.isShowing()) return;
        mLoading.show();
    }

    public void dismissLoading() {
        if (mLoading == null) return;
        if (mLoading.isShowing()) mLoading.dismiss();
    }
    //end




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);

        ActivityCollector.addActivity(this.getClass().getName(), this);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        viewContent = (FrameLayout) findViewById(R.id.id_viewContent);
        tvTitle = (TextView) findViewById(R.id.id_tvTitle);

        //初始化设置 Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(TopBarBaseActivity.this).inflate(getContentView(), viewContent);

        mUnbinder = ButterKnife.bind(TopBarBaseActivity.this, viewContent);

        init(savedInstanceState);
    }

    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    protected void isShowToolBar(boolean flag) {
        if (!flag) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    protected void setTopLeftButton() {
        setTopLeftButton(R.drawable.ic_return_white_24dp, null);
    }

    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener) {
        toolbar.setNavigationIcon(iconResId);
        this.onClickListenerTopLeft = onClickListener;
    }

    protected void setTopRightButton(String menuStr, OnClickListener onClickListener) {
        this.onClickListenerTopRight = onClickListener;
        this.menuStr = menuStr;
    }

    protected void setTopRightButton(String menuStr, boolean showMenu, OnClickListener onClickListener) {
        this.showMenu = showMenu;
        this.menuStr = menuStr;
        this.onClickListenerTopRight = onClickListener;
    }

    protected abstract int getContentView();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(menuStr)) {
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onClickListenerTopLeft.onClick();
        } else if (item.getItemId() == R.id.menu_1) {
            onClickListenerTopRight.onClick();
        }
        return true; // true 告诉系统我们自己处理了点击事件
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        ActivityCollector.removeActivity(this.getClass().getName());
    }
}