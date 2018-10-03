package me.cl.library.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import me.cl.library.R;
import me.cl.library.util.ToastUtil;
import me.cl.library.view.MoeToast;

public class BaseFragment extends Fragment {

    public static final String TAG = "lcDev";

    /**
     * setupToolbar
     * @param toolbar Toolbar
     * @param titleId TitleId
     * @param menuId MenuId
     * @param listener Menu监听
     */
    public void setupToolbar(@NonNull Toolbar toolbar, @StringRes int titleId, int menuId, Toolbar.OnMenuItemClickListener listener) {
        setupToolbar(toolbar, getString(titleId), menuId, listener);
    }

    /**
     * setupToolbar
     * @param toolbar Toolbar
     * @param title Title
     * @param menuId MenuId
     * @param listener Menu监听
     */
    public void setupToolbar(@NonNull Toolbar toolbar, @NonNull String title , int menuId, Toolbar.OnMenuItemClickListener listener) {
        toolbar.setTitle(title);
        toolbar.setTitleTextAppearance(getActivity(), R.style.Lib_AppTextAppearance);
        if (listener != null) {
            toolbar.inflateMenu(menuId);
            toolbar.setOnMenuItemClickListener(listener);
        }
    }

    public void showToast(@StringRes int msgId) {
        ToastUtil.showToast(getContext(), msgId);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(getContext(), msg);
    }

    public void showMoeToast(@StringRes int msgId) {
        MoeToast.makeText(getContext(), msgId);
    }

    public void showMoeToast(String msg) {
        MoeToast.makeText(getContext(), msg);
    }

}
