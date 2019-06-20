package com.limynl.project.presenter;

import android.content.Context;

import com.limynl.project.view.ILoginView;



public class LoginPresenter implements ILoginPresenter{
    private ILoginView iLoginView;
    private Context mContext;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.iLoginView = loginView;
        this.mContext = context;
    }

    /**
     * 登录功能
     * @param phoneNum
     * @param password
     */
    @Override
    public void login(final String phoneNum, String password) {
        iLoginView.loginSuccess("登录成功");
    }
}

interface ILoginPresenter{
    void login(String phoneNum, String password);
}