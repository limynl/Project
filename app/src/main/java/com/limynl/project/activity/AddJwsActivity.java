package com.limynl.project.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.JwsInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.presenter.AddCheckPresenter;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AddJwsActivity extends TopBarBaseActivity {
    private AddCheckPresenter mAddCheckPresenter;

    @BindView(R.id.jws_name_var)
    EditText jws_name_var;
    @BindView(R.id.jws_time_var)
    EditText jws_time_var;
    @BindView(R.id.jws_brif_var)
    EditText jws_brif_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_jws;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("添加既往史");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

    }

    @OnClick({R.id.id_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_update_btn:{
                JwsInfo info = new JwsInfo();
                info.setContent(jws_name_var.getText().toString().trim());
                info.setTime(jws_time_var.getText().toString().trim());
                info.setBrif(jws_brif_var.getText().toString().trim());
                UserDbHelper.setInstance(this);
                info.setUserId(UserDbHelper.getInstance().getUserInfo().getId());
                Gson gson = new Gson();
                OkUtil.post()
                        .url(Constants.useraddJws)
                        .postJson(gson.toJson(info))
                        .execute(new ResultCallback<Result<Boolean>>() {
                            @Override
                            public void onSuccess(Result<Boolean> response) {
                                if (response.getData()) {
                                    Toast.makeText(AddJwsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    AddJwsActivity.this.finish();
                                    return;
                                }
                                Toast.makeText(AddJwsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                Toast.makeText(AddJwsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFinish() {

                            }
                        });
            }break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
