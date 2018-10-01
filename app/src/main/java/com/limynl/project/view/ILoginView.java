package com.limynl.project.view;

/**
 * email 1434117404@qq.com
 */

public interface ILoginView {
    //登录成功接口
    void loginSuccess(String msg);
    //登录失败接口
    void loginFailed(String msg);
    //合法性检验错误
    void validateError(String msg);
    //跳转到选择兴趣界面
    void gotoChooseInterestedCategoryActivity(String msg);
}
