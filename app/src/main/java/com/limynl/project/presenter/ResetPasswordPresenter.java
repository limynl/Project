package com.limynl.project.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.limynl.project.R;
import com.limynl.project.view.IResetPasswordView;


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

        if (TextUtils.isEmpty(code)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_verify_code_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(password)){
            iResetPasswordView.validateError(mContext.getString(R.string.str_password_cannot_empty));
            return;
        }

        //发送请求进行重置密码
        //首先检查验证码是否正确
        iResetPasswordView.validateError("重置成功");

    }
}

interface IResetPasswordPresenter{
    void getVerificationCode(final String phoneNum, final TextView getCodeTv, final CountDownTimer timer);
    void resetPassword(String phoneNum, String password, String code, CountDownTimer timer);
}