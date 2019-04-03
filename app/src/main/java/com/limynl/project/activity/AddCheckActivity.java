package com.limynl.project.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.entity.healthy.CheckInfo;
import com.limynl.project.presenter.AddCheckPresenter;
import com.limynl.project.view.IRegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCheckActivity extends TopBarBaseActivity implements IRegisterView {
    private AddCheckPresenter mAddCheckPresenter;

    @BindView(R.id.weight)
    EditText weight;
    @BindView(R.id.height)
    EditText height;
    @BindView(R.id.version1)
    EditText version1;
    @BindView(R.id.version2)
    EditText version2;
    @BindView(R.id.pressure1)
    EditText pressure1;
    @BindView(R.id.pressure2)
    EditText pressure2;
    @BindView(R.id.heartbeat)
    EditText heartbeat;
    @BindView(R.id.blindness)
    EditText blindness;
    @BindView(R.id.liver_function)
    EditText liver_function;
    @BindView(R.id.vital_capacity)
    EditText vital_capacity;
    @BindView(R.id.oral_cavity)
    EditText oral_cavity;
    @BindView(R.id.body_percentage)
    EditText body_percentage;
    @BindView(R.id.body_fat)
    EditText body_fat;
    @BindView(R.id.conclusion)
    EditText conclusion;
    @BindView(R.id.remarks)
    EditText remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_check;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("添加体检");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        mAddCheckPresenter = new AddCheckPresenter(this, this);
    }

    @OnClick({R.id.id_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_update_btn:{
                CheckInfo info = new CheckInfo();
                info.setWeight(weight.getText().toString().trim());
                info.setHeight(height.getText().toString().trim());
                info.setVision1(version1.getText().toString().trim());
                info.setVision2(version2.getText().toString().trim());
                info.setPressure1(pressure1.getText().toString().trim());
                info.setPressure2(pressure2.getText().toString().trim());
                info.setHeartbeat(heartbeat.getText().toString().trim());
                info.setBlindness(blindness.getText().toString().trim());
                info.setLiverFunction(liver_function.getText().toString().trim());
                info.setVitalCapacity(vital_capacity.getText().toString().trim());
                info.setOralCavity(oral_cavity.getText().toString().trim());
                info.setBodyPercentage(body_percentage.getText().toString().trim());
                info.setBodyFat(body_fat.getText().toString().trim());
                info.setConclusion(conclusion.getText().toString().trim());
                info.setRemarks(remarks.getText().toString().trim());
                mAddCheckPresenter.addCheck(info);
            }break;
        }
    }

    @Override
    public void registerSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        this.finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void registerFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validateError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
