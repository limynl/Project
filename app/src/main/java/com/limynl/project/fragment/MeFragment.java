package com.limynl.project.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.activity.FeedBackActivity;
import com.limynl.project.activity.HealthyReportActivity;
import com.limynl.project.activity.UpdateActivity;
import com.limynl.project.base.LazyLoadFragment;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.utils.Utils;

import be.webelite.ion.IconView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends LazyLoadFragment {
    @BindView(R.id.id_username)
    TextView userName;
    @BindView(R.id.id_user_sex_male)
    IconView userGenderMale;
    @BindView(R.id.id_user_sex_female)
    IconView userGenderFemale;
    @BindView(R.id.setting_retreat)
    Button retreat;
    private Unbinder unbinder;
    private Context mContext;

    private View customDialog;
    private Button out, cancel;
    private Dialog dialogOne;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void lazyLoad() {
        mContext = getActivity();
        UserDbHelper.setInstance(mContext);
        setUserInfo();
    }

    @OnClick({R.id.id_avatar, R.id.setting_retreat, R.id.link_me, R.id.check_update, R.id.feed_back, R.id.healthy_report})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.healthy_report: {
                Intent intent = new Intent(getContext(),HealthyReportActivity.class);
                startActivity(intent);
            }
            break;
            // 修改个人信息
            case R.id.id_avatar: {
                Intent intent = new Intent(getContext(),UpdateActivity.class);
                startActivityForResult(intent, 2001);
            }
            break;
            case R.id.link_me: {
                boolean isWpa = Utils.wpaQQ(getActivity(), "2256684232");
                if (!isWpa) {
                    Toast.makeText(mContext, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.check_update: {
                Toast.makeText(mContext, "当前已是最新版本!", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.feed_back: {
                Intent intent = new Intent(mContext,FeedBackActivity.class);
                startActivity(intent);
            }
            break;
            // 退出登录
            case R.id.setting_retreat: {
                customDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_out, null);
                out = (Button) customDialog.findViewById(R.id.app_out);
                cancel = (Button) customDialog.findViewById(R.id.app_cancel);
                dialogOne = new Dialog(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(customDialog);
                dialogOne = builder.create();
                dialogOne.show();
                out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserDbHelper.getInstance().saveLoginState(false);
                        getActivity().finish();
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        dialogOne.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogOne.dismiss();
                    }
                });
            }
            break;
        }
    }

    private void setUserInfo(){
        userName.setText(UserDbHelper.getInstance().getUserInfo().getUsername());
        String gender = UserDbHelper.getInstance().getUserInfo().getGender();
        if("男".equals(gender)){
            userGenderMale.setVisibility(View.VISIBLE);
            userGenderFemale.setVisibility(View.GONE);
        }else{
            userGenderFemale.setVisibility(View.VISIBLE);
            userGenderMale.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2001){
            setUserInfo();
        }
    }
}