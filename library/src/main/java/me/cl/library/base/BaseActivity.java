package me.cl.library.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.cl.library.R;
import me.cl.library.util.ToastUtil;
import me.cl.library.view.LoadingDialog;
import me.cl.library.view.MoeToast;


public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "lcDev";

    // 加载动画开始
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
    // 加载动画结束

    /**
     * setupToolbar
     * @param toolbar Toolbar
     * @param titleId TitleId
     * @param isBack 是否添加返回
     * @param menuId MenuId
     * @param listener Menu监听
     */
    public void setupToolbar(@NonNull Toolbar toolbar, @StringRes int titleId, boolean isBack, int menuId, Toolbar.OnMenuItemClickListener listener) {
        setupToolbar(toolbar, getString(titleId), isBack, menuId, listener);
    }

    /**
     * setupToolbar
     * @param toolbar Toolbar
     * @param title Title
     * @param isBack 是否添加返回
     * @param menuId MenuId
     * @param listener Menu监听
     */
    public void setupToolbar(@NonNull Toolbar toolbar, @NonNull String title, boolean isBack, int menuId, Toolbar.OnMenuItemClickListener listener) {
        toolbar.setTitle(title);
        toolbar.setTitleTextAppearance(this, R.style.Lib_AppTextAppearance);
        if (isBack) {
            toolbar.setNavigationIcon(R.drawable.ic_navigate);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        if (listener != null) {
            toolbar.inflateMenu(menuId);
            toolbar.setOnMenuItemClickListener(listener);
        }
    }

    public void showToast(@StringRes int msgId) {
        ToastUtil.showToast(this, msgId);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    public void showMoeToast(@StringRes int msgId) {
        MoeToast.makeText(this, msgId);
    }

    public void showMoeToast(String msg) {
        MoeToast.makeText(this, msg);
    }
}
