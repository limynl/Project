package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.presenter.LoginPresenter;
import com.limynl.project.view.ILoginView;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends TopBarBaseActivity implements ILoginView {
    @BindView(R.id.id_login_phone_number)
    EditText mLoginPhoneNumber;
    @BindView(R.id.id_login_password)
    EditText mLoginPassword;
    @BindView(R.id.id_login_btn)
    Button mLoginBtn;
    @BindView(R.id.id_login_forget_password)
    TextView mLoginForgetPassword;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.str_login));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        setTopRightButton(getString(R.string.str_register), new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mLoginPresenter = new LoginPresenter(this, this);
        UserDbHelper.setInstance(this);
        Intent intent = null;
        // 当前用户已登录
        if(UserDbHelper.getInstance().getLoginState()){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void loginSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void loginFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoChooseInterestedCategoryActivity(String msg) {
    }

    @OnClick({R.id.id_login_btn, R.id.id_login_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_login_btn:{
                mLoginPresenter.login(mLoginPhoneNumber.getText().toString().trim(), mLoginPassword.getText().toString().trim());
            }break;
            case R.id.id_login_forget_password:{
                Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}