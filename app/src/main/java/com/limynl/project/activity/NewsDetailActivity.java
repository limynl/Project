package com.limynl.project.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.limynl.project.R;
import com.limynl.project.adapter.ContentNewsAdapter;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.entity.TestContentNewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsDetailActivity extends TopBarBaseActivity  {
    @BindView(R.id.content)
    TextView contentTextView;
    private List<TestContentNewsBean> allDataList = new ArrayList<>();
    private ContentNewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail_news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("资讯详情");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String content = (String) bundle.getSerializable("content");
        contentTextView.setText(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
