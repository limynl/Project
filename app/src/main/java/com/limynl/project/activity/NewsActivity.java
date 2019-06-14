package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.adapter.ContentNewsAdapter;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.TestContentNewsBean;
import com.limynl.project.entity.healthy.CommonInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class NewsActivity extends TopBarBaseActivity  {
    @BindView(R.id.news_list)
    ListView newsList;
    private List<CommonInfo> allDataList = new ArrayList<>();
    private ContentNewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("资讯专栏");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        initData();
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content", allDataList.get(i).getContent());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initData(){
        UserDbHelper.setInstance(this);
        Integer userId = UserDbHelper.getInstance().getUserInfo().getId();
        OkUtil.post()
                .url(Constants.news)
                .addParam("userId", userId)
                .execute(new ResultCallback<Result<List<CommonInfo>>>() {
                    @Override
                    public void onSuccess(Result<List<CommonInfo>> response) {
                        List<CommonInfo> result = response.getData();
                        if (result == null || result.size() == 0) {
                            return;
                        }
                        if (allDataList.size() == 0) {
                            allDataList.addAll(result);
                            setData(allDataList);
                        } else {
                            allDataList.clear();
                            allDataList.addAll(result);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(NewsActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    /**
     * 初始化适配器
     */
    private void setData(List<CommonInfo> carBeanList) {
        adapter = new ContentNewsAdapter(this, allDataList);
        newsList.setAdapter(adapter);
    }

    /**
     * 用于解析json数据
     * @param json 相应的json数据
     * @return 返回json数据中对应的list集合
     */
    private List<TestContentNewsBean> getDataFromJson(String json){
        List<TestContentNewsBean> dataList = new ArrayList<TestContentNewsBean>();
        JSONObject jsonNews = null;
        TestContentNewsBean newsBean = null;
        try {
            jsonNews = new JSONObject(json);
//            jsonNews = jsonNews.optJSONObject("result");
            JSONArray jsonArrayNews = jsonNews.optJSONArray("data");
            for (int s = 0; s < jsonArrayNews.length(); s++) {
                jsonNews = jsonArrayNews.optJSONObject(s);
                newsBean = new TestContentNewsBean();
                newsBean.setImageUrl(jsonNews.optString("picSmall"));

                newsBean.setTitle(jsonNews.optString("name"));
                newsBean.setContent(jsonNews.optString("content"));
                newsBean.setSrc(jsonNews.optString("description"));
                newsBean.setBrowseNumber(jsonNews.optInt("learner"));
//                newsBean.setTime(jsonNews.optString("time"));
//                newsBean.setContentUrl(jsonNews.optString("url"));
                dataList.add(newsBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
