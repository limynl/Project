package com.limynl.project.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.CheckInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;
import com.limynl.project.view.IRegisterView;

import okhttp3.Call;

public class AddCheckPresenter implements IAddCheckPresenter {

    private IRegisterView iRegisterView;
    private Context mContext;

    public AddCheckPresenter(IRegisterView iResetPasswordView, Context context){
        this.iRegisterView = iResetPasswordView;
        this.mContext = context;
    }

    @Override
    public void addCheck(CheckInfo info) {
        UserDbHelper.setInstance(mContext);
        info.setUserId(UserDbHelper.getInstance().getUserInfo().getId());
        Gson gson = new Gson();
        OkUtil.post()
                .url(Constants.useraddCheck)
                .postJson(gson.toJson(info))
                .execute(new ResultCallback<Result<Boolean>>() {
                    @Override
                    public void onSuccess(Result<Boolean> response) {
                        if (response.getData()) {
                            iRegisterView.registerSuccess("添加成功!");
                            return;
                        }
                        iRegisterView.validateError("添加失败!");
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        iRegisterView.validateError("添加失败!");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}

interface IAddCheckPresenter{
    void addCheck(CheckInfo info);
}