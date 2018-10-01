package com.limynl.project.view;

/**
 * email 1434117404@qq.com
 */

public interface IResetPasswordView {
    //重置密码成功接口
    void resetSuccess(String msg);
    //重置密码失败接口
    void resetFailed(String msg);
    //合法性检验错误
    void validateError(String msg);
}
