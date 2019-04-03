package com.limynl.project.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.presenter.UpdatePresenter;
import com.limynl.project.view.IRegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateActivity extends TopBarBaseActivity implements IRegisterView{
    private static final String TAG = "UpdateActivity";
    private UpdatePresenter mUpdatePresenter;

    @BindView(R.id.user_message_nick)
    EditText mUsername;
    @BindView(R.id.user_phone)
    EditText mPhoneNumber;
    @BindView(R.id.user_message_gender)
    TextView mGender;

    @BindView(R.id.id_update_btn)
    Button mRegisterBtn;

    private static String gender = "男";

    private Dialog dialog;//弹框
    private Button chooseFromCamera;//选择按钮一
    private Button chooseFromPhoto;//选择按钮二
    private Button cancelDialog;//取消按钮
    private View viewDialog;//弹框视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_update;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("修改信息");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        mUpdatePresenter = new UpdatePresenter(this, this);

        UserDbHelper.setInstance(this);
        mUsername.setText(UserDbHelper.getInstance().getUserInfo().getUsername());
        mPhoneNumber.setText(UserDbHelper.getInstance().getUserInfo().getPhone());
        mGender.setText(UserDbHelper.getInstance().getUserInfo().getGender());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.user_gender_select, R.id.id_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_gender_select: {
                showDialog("男", "女");
                chooseFromCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGender.setText("男");
                        dialog.dismiss();
                    }
                });

                chooseFromPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGender.setText("女");
                        dialog.dismiss();
                    }
                });

                cancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();//显示对话框主题
            }break;
            case R.id.id_update_btn:
                mUpdatePresenter.update(mUsername.getText().toString(), mPhoneNumber.getText().toString(), mGender.getText().toString());
                break;
        }
    }

    private void showDialog(String value1, String value2) {
        viewDialog = View.inflate(this,R.layout.photo_choose_dialog, null);
        chooseFromCamera = (Button) viewDialog.findViewById(R.id.choose_one);
        chooseFromCamera.setText(value1);
        chooseFromPhoto = (Button) viewDialog.findViewById(R.id.choose_two);
        chooseFromPhoto.setText(value2);
        cancelDialog = (Button) viewDialog.findViewById(R.id.cancel);

        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(viewDialog, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = window.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
