package com.limynl.project.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.limynl.project.R;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.HealthyUserInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;
import com.limynl.project.view.ILoginView;

import okhttp3.Call;


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
        if (TextUtils.isEmpty(phoneNum)){
            iLoginView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }
        if (TextUtils.isEmpty(password)){
            iLoginView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }

        loginUser(phoneNum, password);
    }

    private void loginUser(final String phoneNum, String password) {
        UserDbHelper.setInstance(mContext);
        OkUtil.post()
                .url(Constants.userLogin)
                .addParam("phone", phoneNum)
                .addParam("password", password)
                .execute(new ResultCallback<Result<HealthyUserInfo>>() {
                    @Override
                    public void onSuccess(Result<HealthyUserInfo> response) {
                        HealthyUserInfo info = response.getData();
                        if (info != null) {
                            iLoginView.loginSuccess("登录成功!");
                            UserDbHelper.getInstance().saveLoginState(true);
                            UserDbHelper.getInstance().saveUserLoginInfo(info);
                            return;
                        }
                        iLoginView.validateError("登录失败，请检查手机号或密码");
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        iLoginView.validateError("登录失败，服务器异常!");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}

interface ILoginPresenter{
    void login(String phoneNum, String password);
}