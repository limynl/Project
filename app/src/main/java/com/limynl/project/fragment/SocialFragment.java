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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.activity.AddCheckActivity;
import com.limynl.project.activity.DetailCheckActivity;
import com.limynl.project.adapter.ShowCarAdapter;
import com.limynl.project.base.LazyLoadFragment;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.healthy.CheckInfo;
import com.limynl.project.entity.healthy.HealthyUserInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class SocialFragment extends LazyLoadFragment {
    @BindView(R.id.show_car_listview)
    ListView listView;
    private Unbinder unbinder;
    private Context mContext;
    private HealthyUserInfo userInfo;

    private static final String FEED_TYPE = "feed_type";

    private ShowCarAdapter showCarAdapter;
    private List<CheckInfo> list = new ArrayList<>();

    private View customDialog;
    private Button out, cancel;
    private Dialog dialogOne;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_social;
    }

    @Override
    protected void lazyLoad() {
        mContext = getActivity();
        UserDbHelper.setInstance(mContext);
        userInfo = UserDbHelper.getInstance().getUserInfo();
        init();
    }

    public void init() {
        UserDbHelper.setInstance(mContext);
        Integer userId = UserDbHelper.getInstance().getUserInfo().getId();
        OkUtil.post()
                .url(Constants.userCheck)
                .addParam("userId", userId)
                .execute(new ResultCallback<Result<List<CheckInfo>>>() {
                    @Override
                    public void onSuccess(Result<List<CheckInfo>> response) {
                        List<CheckInfo> result = response.getData();
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
                        Toast.makeText(mContext, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    /**
     * 初始化适配器
     */
    private void setData(List<CheckInfo> carBeanList) {
        showCarAdapter = new ShowCarAdapter(mContext, carBeanList);
        showCarAdapter.setBtnClickListener(new ShowCarAdapter.btnClickListener() {
            @Override
            public void btnDeleteClick(final int position) {
                customDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_out, null);
                out = (Button) customDialog.findViewById(R.id.app_out);
                out.setText("删除");
                cancel = (Button) customDialog.findViewById(R.id.app_cancel);
                TextView message = (TextView) customDialog.findViewById(R.id.show_message);
                message.setText("是否要删除该体检!");
                dialogOne = new Dialog(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(customDialog);
                dialogOne = builder.create();
                dialogOne.show();
                out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkUtil.post()
                                .url(Constants.userDeleteCheck)
                                .addParam("id", list.get(position).getId())
                                .execute(new ResultCallback<Result<Boolean>>() {
                                    @Override
                                    public void onSuccess(Result<Boolean> response) {
                                        if(response.getData()){
                                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                            list.remove(position);
                                            showCarAdapter.notifyDataSetChanged();
                                            dialogOne.dismiss();
                                            return;
                                        }
                                        Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(Call call, Exception e) {
                                        Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                });
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogOne.dismiss();
                    }
                });
            }

            @Override
            public void btnEditClick(int position) {
                Intent intent = new Intent(mContext, DetailCheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("checkInfo", list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setAdapter(showCarAdapter);
    }

    @OnClick({R.id.check_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_add: {
                Intent intent = new Intent(mContext, AddCheckActivity.class);
                startActivityForResult(intent, 2002);
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2002){
            init();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}