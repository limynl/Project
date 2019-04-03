package com.limynl.project.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.utils.textview.WavyLineView;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends TopBarBaseActivity{
    @BindView(R.id.release_wavyLine)
    WavyLineView mWavyLine;
    @BindView(R.id.feed_back_content)
    EditText feedBackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("意见反馈");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        // 波浪线设置
        mWavyLine.setColor(Color.parseColor("#01B090"));
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);//ScreenUtil.dp2px(initStrokeWidth)
    }

    @OnClick({R.id.id_feed_back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_feed_back_btn:{
                Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
                this.finish();
            }break;
        }
    }
}
