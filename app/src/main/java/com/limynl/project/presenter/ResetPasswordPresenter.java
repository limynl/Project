package com.limynl.project.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.limynl.project.R;
import com.limynl.project.constants.Constants;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;
import com.limynl.project.view.IResetPasswordView;

import okhttp3.Call;


public class ResetPasswordPresenter implements IResetPasswordPresenter{
    private static final String TAG = "ResetPasswordPresenter";
    private IResetPasswordView iResetPasswordView;
    private Context mContext;

    public ResetPasswordPresenter(IResetPasswordView iResetPasswordView, Context context){
        this.iResetPasswordView = iResetPasswordView;
        this.mContext = context;
    }

    /**
     * 获取手机验证码
     * @param phoneNum
     * @param getCodeTv
     * @param timer
     */
    @Override
    public void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer) {
        if (TextUtils.isEmpty(phoneNum)) {
            iResetPasswordView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
        } else {
            getCodeTv.setClickable(false);
            timer.start();

            iResetPasswordView.validateError("验证码发送成功");
        }
    }

    /**
     * 重置密码
     * @param phoneNum
     * @param password
     * @param code
     */
    @Override
    public void resetPassword(String phoneNum, String password, String code, CountDownTimer timer) {
        if (TextUtils.isEmpty(phoneNum)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(password)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }

        resetUserPassword(phoneNum, password);
    }

    private void resetUserPassword(String phoneNum, String password) {
        OkUtil.post()
                .url(Constants.userResetPassword)
                .addParam("phone", phoneNum)
                .addParam("password", password)
                .execute(new ResultCallback<Result<Boolean>>() {
                    @Override
                    public void onSuccess(Result<Boolean> response) {
                        if (response.getData()) {
                            iResetPasswordView.resetSuccess("密码重置成功!");
                            return;
                        }
                        iResetPasswordView.validateError("请检查输入的手机号");
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        iResetPasswordView.validateError("密码重置失败!");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}

interface IResetPasswordPresenter{
    void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer);
    void resetPassword(String phoneNum, String password, String code, CountDownTimer timer);
}