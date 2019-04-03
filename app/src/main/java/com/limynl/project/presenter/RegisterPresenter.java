package com.limynl.project.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.limynl.project.R;
import com.limynl.project.constants.Constants;
import com.limynl.project.entity.healthy.HealthyUserInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;
import com.limynl.project.view.IRegisterView;

import okhttp3.Call;


public class RegisterPresenter implements IRegisterPresenter {
    private static final String TAG = "RegisterPresenter";
    private IRegisterView iRegisterView;
    private Context mContext;

    public RegisterPresenter(IRegisterView iRegisterView, Context context) {
        this.iRegisterView = iRegisterView;
        this.mContext = context;
    }

    /**
     * 获取验证码(先验证手机号是否已经被注册)
     *
     * @param phoneNum  手机号
     * @param getCodeTv 获得验证码的按钮
     * @param timer     验证码计时器
     * @return
     */
    @Override
    public void getVerificationCode(String phoneNum, TextView getCodeTv, CountDownTimer timer) {
        if (TextUtils.isEmpty(phoneNum)) {
            iRegisterView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
        } else {
            getCodeTv.setClickable(false);
            timer.start();

            iRegisterView.validateError(mContext.getString(R.string.str_verify_code_send_success));
        }
    }

    /**
     * 注册功能
     *
     * @param username
     * @param phoneNum
     * @param password
     * @param code     验证码
     */
    @Override
    public void register(String username, String phoneNum, String password, String code, String gender, CountDownTimer timer) {
        if (TextUtils.isEmpty(username)) {
            iRegisterView.validateError(mContext.getString(R.string.str_username_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(phoneNum)) {
            iRegisterView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            iRegisterView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }

        //进行注册
        registerUser(username, gender, phoneNum, password);
    }

    private void registerUser(String username, String gender, String phoneNum, String password) {
        HealthyUserInfo user = new HealthyUserInfo();
        user.setUsername(username);
        user.setGender(gender);
        user.setPhone(phoneNum);
        user.setPassword(password);
        Gson gson = new Gson();
        OkUtil.post()
                .url(Constants.userRegister)
                .postJson(gson.toJson(user))
                .execute(new ResultCallback<Result<Boolean>>() {
                    @Override
                    public void onSuccess(Result<Boolean> response) {
                        if (response.getData()) {
                            iRegisterView.registerSuccess("注册成功!");
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        iRegisterView.validateError("注册失败!");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}

interface IRegisterPresenter {
    void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer);

    void register(String username, String phoneNum, String password, String code, String gender, CountDownTimer timer);
}