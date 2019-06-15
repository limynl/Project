package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;

import butterknife.OnClick;


public class MainListActivity extends TopBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.main_list_activity;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("驾校报名及宣传系统");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.id_xuanchuan_info, R.id.id_baoming_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_xuanchuan_info: {//宣传页面
                Intent intent = new Intent(this, PropagandaActivity.class);
                startActivity(intent);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.id_baoming_info: {//在线报名
                Intent intent = new Intent(this, ShowMessageActivity.class);
                startActivity(intent);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }
}
