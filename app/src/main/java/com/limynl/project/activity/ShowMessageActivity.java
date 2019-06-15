package com.limynl.project.activity;

import android.os.Bundle;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;

/**
 * Created by Lmy on 2019/6/15.
 * email 1434117404@qq.com
 */

public class ShowMessageActivity extends TopBarBaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.show_message_activity;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("在线报名");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
