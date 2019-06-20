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

import com.limynl.project.R;
import com.limynl.project.activity.UpdateActivity;
import com.limynl.project.base.LazyLoadFragment;
import com.limynl.project.db.UserDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends LazyLoadFragment {
    @BindView(R.id.id_username)
    TextView userName;
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
        return R.layout.fragment_me;
    }

    @Override
    protected void lazyLoad() {
        mContext = getActivity();
        UserDbHelper.setInstance(mContext);
    }

    @OnClick({R.id.id_about_info, R.id.id_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_about_info: {//关于我们
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.id_login_out: {//退出登录
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
}