package com.limynl.project.presenter;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.limynl.project.R;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.HealthyUserInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;
import com.limynl.project.view.IRegisterView;

import okhttp3.Call;

public class UpdatePresenter implements IUpdatePresenter{

    private IRegisterView iRegisterView;
    private Context mContext;

    public UpdatePresenter(IRegisterView iResetPasswordView, Context context){
        this.iRegisterView = iResetPasswordView;
        this.mContext = context;
    }

    @Override
    public void update(String username, String phoneNum, String gender) {
        if (TextUtils.isEmpty(username)) {
            iRegisterView.validateError(mContext.getString(R.string.str_username_cannot_empty));
            return;
        }

        if (TextUtils.isEmpty(phoneNum)) {
            iRegisterView.validateError(mContext.getString(R.string.str_phone_num_cannot_empty));
            return;
        }

        //进行注册
        updateUser(username, phoneNum, gender);
    }

    private void updateUser(String username, String phoneNum, String gender) {
        UserDbHelper.setInstance(mContext);
        final HealthyUserInfo user = new HealthyUserInfo();
        user.setId(UserDbHelper.getInstance().getUserInfo().getId());
        user.setUsername(username);
        user.setGender(gender);
        user.setPhone(phoneNum);
        Gson gson = new Gson();
        OkUtil.post()
                .url(Constants.userUpdate)
                .postJson(gson.toJson(user))
                .execute(new ResultCallback<Result<Boolean>>() {
                    @Override
                    public void onSuccess(Result<Boolean> response) {
                        if (response.getData()) {
                            iRegisterView.registerSuccess("更新成功!!");
                            UserDbHelper.getInstance().saveUserLoginInfo(user);
                            return;
                        }
                        iRegisterView.validateError("该电话号码已注册！");
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        iRegisterView.validateError("更新失败!");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

}
interface IUpdatePresenter{
    void update(String username, String phoneNum, String gender);
}