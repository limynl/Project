package com.limynl.project.fragment.other;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.limynl.project.R;
import com.limynl.project.adapter.ContentNewsAdapter;
import com.limynl.project.base.LazyLoadFragment;
import com.limynl.project.entity.TestContentNewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * email 1434117404@qq.com
 */

public class GwFragment extends LazyLoadFragment {
    @BindView(R.id.listview)
    ListView listView;
    private List<TestContentNewsBean> allDataList = new ArrayList<>();
    private ContentNewsAdapter adapter;

    private Unbinder unbinder;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_other_yule;
    }

    @Override
    protected void lazyLoad() {
        mContext = getContext();
        //        initData();
        String result = "{\n" +
                "  \"status\": 1,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 10,\n" +
                "      \"name\": \"C++远征之起航篇\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/550b86560001009406000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550b86560001009406000338.jpg\",\n" +
                "      \"description\": \"C++亮点尽在其中\",\n" +
                "      \"learner\": 84545\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 11,\n" +
                "      \"name\": \"Tony老师聊shell——运算符\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/551916790001125706000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/551916790001125706000338.jpg\",\n" +
                "      \"description\": \"Tony为你带来shell编程中的运算符！\",\n" +
                "      \"learner\": 18411\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 12,\n" +
                "      \"name\": \"如何使用高德云图在线制作属于你的地图\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/5518ecf20001cb4e06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5518ecf20001cb4e06000338.jpg\",\n" +
                "      \"description\": \"教你迅速使用云图进行基于LBS的开发。\",\n" +
                "      \"learner\": 56432\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 13,\n" +
                "      \"name\": \"鬼斧神工之正则表达式\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/5518bbe30001c32006000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5518bbe30001c32006000338.jpg\",\n" +
                "      \"description\": \"正则表达式是计算机编程语言界的鬼斧神工。\",\n" +
                "      \"learner\": 25210\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 14,\n" +
                "      \"name\": \"如何使用高德Windows Phone SDK进行基于LBS的开发\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/551380400001da9b06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/551380400001da9b06000338.jpg\",\n" +
                "      \"description\": \"教你迅速使用WP SDK进行基于LBS的开发\",\n" +
                "      \"learner\": 56445\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 15,\n" +
                "      \"name\": \"canvas实现星星闪烁特效\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/550a33b00001738a06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a33b00001738a06000338.jpg\",\n" +
                "      \"description\": \"使用HTML5实现轮播图片上的序列帧。\",\n" +
                "      \"learner\": 45658\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 16,\n" +
                "      \"name\": \"如何使用高德JS-API进行基于LBS的开发\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/5513a1b50001752806000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5513a1b50001752806000338.jpg\",\n" +
                "      \"description\": \"教你迅速用高德JS-API进行基于LBS的开发\",\n" +
                "      \"learner\": 15222\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 17,\n" +
                "      \"name\": \"Duang~MySQLi扩展库来袭\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/5513e20600017c1806000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/5513e20600017c1806000338.jpg\",\n" +
                "      \"description\": \"Duang~一起开启MySQLi的学习之旅吧!\",\n" +
                "      \"learner\": 46321\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 18,\n" +
                "      \"name\": \"如何使用高德Android SDK进行基于LBS的开发\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/550a78720001f37a06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a78720001f37a06000338.jpg\",\n" +
                "      \"description\": \"教你迅速使用Android SDK进行基于LBS的开发\",\n" +
                "      \"learner\": 12130\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 19,\n" +
                "      \"name\": \"高德地图组件快速入门\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/550a836c0001236606000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a836c0001236606000338.jpg\",\n" +
                "      \"description\": \"教你迅速使用地图组件进行基于LBS的开发\",\n" +
                "      \"learner\": 54540\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 20,\n" +
                "      \"name\": \"如何使用高德定位进行开发\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/550a87da000168db06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/550a87da000168db06000338.jpg\",\n" +
                "      \"description\": \"教你迅速使用定位进行基于LBS的开发\",\n" +
                "      \"learner\": 45665\n" +
                "    }\n" +
                "  ],\n" +
                "  \"msg\": \"成功\"\n" +
                "}";
        allDataList = getDataFromJson(result);
//        adapter = new ContentNewsAdapter(getContext(), allDataList);
        listView.setAdapter(adapter);
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

}