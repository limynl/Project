package com.limynl.project.view;

/**
 * email 1434117404@qq.com
 */

public interface IRegisterView {
    //注册成功接口
    void registerSuccess(String msg);
    //注册失败接口
    void registerFailed(String msg);
    //合法性检验错误
    void validateError(String msg);
}
