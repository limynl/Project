package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.adapter.JwsAdapter;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.HealthyUserInfo;
import com.limynl.project.entity.healthy.JwsInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class JwsActivity extends TopBarBaseActivity  {
    @BindView(R.id.jws_list)
    ListView jws_list;

    private HealthyUserInfo userInfo;
    private List<JwsInfo> list = new ArrayList<>();
    private JwsAdapter showCarAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_jws;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("既往病史");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        init();
    }

    public void init() {
        UserDbHelper.setInstance(this);
        Integer userId = UserDbHelper.getInstance().getUserInfo().getId();
        OkUtil.post()
                .url(Constants.jwsCheck)
                .addParam("userId", userId)
                .execute(new ResultCallback<Result<List<JwsInfo>>>() {
                    @Override
                    public void onSuccess(Result<List<JwsInfo>> response) {
                        List<JwsInfo> result = response.getData();
                        if (result == null || result.size() == 0) {
                            return;
                        }
                        if (list.size() == 0) {
                            list.addAll(result);
                            setData(list);
                        } else {
                            list.clear();
                            list.addAll(result);
                            showCarAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(JwsActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    @OnClick({R.id.jws_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jws_add:{
                Intent intent = new Intent(this, AddJwsActivity.class);
                startActivityForResult(intent, 2009);
            }break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2002){
            init();
        }
    }

    /**
     * 初始化适配器
     */
    private void setData(List<JwsInfo> carBeanList) {
        showCarAdapter = new JwsAdapter(this, carBeanList);
        jws_list.setAdapter(showCarAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
