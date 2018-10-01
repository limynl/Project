package com.limynl.project.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.presenter.ResetPasswordPresenter;
import com.limynl.project.view.IResetPasswordView;

import butterknife.BindView;
import butterknife.OnClick;


public class ResetPasswordActivity extends TopBarBaseActivity implements IResetPasswordView {
    @BindView(R.id.id_forget_phone_number)
    EditText mForgetPhoneNumEdit;
    @BindView(R.id.id_forget_identify_code)
    EditText mForgetIdentifyCodeEdit;
    @BindView(R.id.id_forget_get_identify_code)
    TextView mForgetGetIdentifyCodeTv;
    @BindView(R.id.id_forget_input_password_edit)
    EditText mForgetInputPasswordEdit;
    @BindView(R.id.id_forget_confirm)
    Button mForgetConfirmBtn;

    private ResetPasswordPresenter mResetPasswordPresenter;

    //验证码倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mForgetGetIdentifyCodeTv.setText(millisUntilFinished/1000 + "秒");
        }

        @Override
        public void onFinish() {
            mForgetGetIdentifyCodeTv.setClickable(true);
            mForgetGetIdentifyCodeTv.setText("获取验证码");
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_reset_password));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
                timer.cancel();
            }
        });
        mResetPasswordPresenter = new ResetPasswordPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.id_forget_get_identify_code, R.id.id_forget_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_forget_get_identify_code:
                mResetPasswordPresenter.getVerificationCode(mForgetPhoneNumEdit.getText().toString(), mForgetGetIdentifyCodeTv, timer);
                break;
            case R.id.id_forget_confirm:
                mResetPasswordPresenter.resetPassword(mForgetPhoneNumEdit.getText().toString(), mForgetInputPasswordEdit.getText().toString(), mForgetIdentifyCodeEdit.getText().toString(), timer);
                break;
        }
    }

    @Override
    public void resetSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        this.finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void resetFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}