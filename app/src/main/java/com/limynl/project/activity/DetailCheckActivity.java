package com.limynl.project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.CheckInfo;

import butterknife.BindView;

public class DetailCheckActivity extends TopBarBaseActivity  {
    @BindView(R.id.detail_user_name)
    TextView userName;
    @BindView(R.id.check_time)
    TextView check_time;

    @BindView(R.id.check_height)
    TextView check_height;
    @BindView(R.id.check_hearbeat)
    TextView check_hearbeat;
    @BindView(R.id.check_blindness)
    TextView check_blindness;

    @BindView(R.id.check_version)
    TextView check_version;
    @BindView(R.id.check_pressure)
    TextView check_pressure;

    @BindView(R.id.check_weight)
    TextView check_weight;
    @BindView(R.id.check_bmi)
    TextView check_bmi;
    @BindView(R.id.check_body_percentage_fat)
    TextView check_body_percentage_fat;
    @BindView(R.id.check_body_fat)
    TextView check_body_fat;
    @BindView(R.id.check_liver_function_str)
    TextView check_liver_function_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail_check;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("体检报告");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        UserDbHelper.setInstance(this);

        Bundle bundle = getIntent().getExtras();
        CheckInfo info = (CheckInfo) bundle.getSerializable("checkInfo");

        userName.setText(UserDbHelper.getInstance().getUserInfo().getUsername());
        check_time.setText(info.getTime());
        check_height.setText(info.getHeight() + "CM");
        check_hearbeat.setText(info.getHeartbeat() + "次/分");
        check_blindness.setText(info.getBlindness());
        check_version.setText(info.getVision1() + "/" + info.getVision2());
        check_pressure.setText(info.getPressure1() + "/" + info.getPressure2());
        check_weight.setText(info.getWeight() + "KG");
        try{
            double bmi = Float.parseFloat(info.getWeight()) / Math.pow(Float.parseFloat(info.getHeight()) / 100, 2);
            check_bmi.setText(String.format("%.2f", bmi) + "KG/M²");
        }catch (Exception e){
            Log.e("DetailCheckActivity",  e + "");
        }
        check_body_percentage_fat.setText(info.getBodyPercentage());
        check_body_fat.setText(info.getBodyFat());
        check_liver_function_str.setText(info.getLiverFunction());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
