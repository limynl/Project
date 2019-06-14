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

public class JkFragment extends LazyLoadFragment {

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
        String result = "{\"status\":1,\"data\":[{\"id\":1,\"name\":\"Tony\\u8001\\u5e08\\u804ashell\\u2014\\u2014\\u73af\\u5883\\u53d8\\u91cf\\u914d\\u7f6e\\u6587\\u4ef6\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/55237dcc0001128c06000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/55237dcc0001128c06000338.jpg\",\"description\":\"\\u4e3a\\u4f60\\u5e26\\u6765shell\\u4e2d\\u7684\\u73af\\u5883\\u53d8\\u91cf\\u914d\\u7f6e\\u6587\\u4ef6\",\"learner\":12312},{\"id\":2,\"name\":\"\\u6570\\u5b66\\u77e5\\u8bc6\\u5728CSS\\u52a8\\u753b\\u4e2d\\u7684\\u5e94\\u7528\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/55249cf30001ae8a06000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/55249cf30001ae8a06000338.jpg\",\"description\":\"\\u6570\\u5b66\\u77e5\\u8bc6\\u4e0eCSS\\u7ed3\\u5408\\u5b9e\\u73b0\\u9177\\u70ab\\u6548\\u679c\",\"learner\":45625},{\"id\":3,\"name\":\"Oracle\\u6570\\u636e\\u5e93\\u5f00\\u53d1\\u5fc5\\u5907\\u5229\\u5668\\u4e4bPL\\/SQL\\u57fa\\u7840\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/5523711700016d1606000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/5523711700016d1606000338.jpg\",\"description\":\"Oracle\\u6570\\u636e\\u5e93\\u9ad8\\u7ea7\\u5f00\\u53d1\\u5fc5\\u5907\\u7684\\u57fa\\u7840\\u3002\",\"learner\":41236},{\"id\":4,\"name\":\"Android\\u89c1\\u8bc1\\u6d88\\u606f\\u63a8\\u9001\\u65f6\\u523b\\u8fdb\\u9636\\u7bc7\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/551e470500018dd806000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/551e470500018dd806000338.jpg\",\"description\":\"Android\\u6d88\\u606f\\u63a8\\u9001\\u5c31\\u5728\\u773c\\u524d\\uff0cCome on\",\"learner\":45456},{\"id\":5,\"name\":\"Avalon\\u63a2\\u7d22\\u4e4b\\u65c5\\u57fa\\u7840\\u6559\\u7a0b\\u2014\\u2014\\u590d\\u6742\\u7ed1\\u5b9a\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/551de0570001134f06000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/551de0570001134f06000338.jpg\",\"description\":\"\\u524d\\u7aef\\u8ff7\\u60a8MVVM\\u6846\\u67b6\\uff0cAvalon\\u590d\\u6742\\u7ed1\\u5b9a\\u5c5e\\u6027\\u7bc7\\u3002\",\"learner\":56556},{\"id\":6,\"name\":\"Android-Service\\u7cfb\\u5217\\u4e4b\\u65ad\\u70b9\\u7eed\\u4f20\\u4e0b\\u8f7d\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/552640c300018a9606000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/552640c300018a9606000338.jpg\",\"description\":\"\\u60f3\\u5347\\u804c\\u52a0\\u85aa\\u4e48\\uff1f\\u672c\\u7ae0\\u8bfe\\u7a0b\\u4f60\\u503c\\u5f97\\u62e5\\u6709\",\"learner\":48996},{\"id\":7,\"name\":\"JUnit\\u2014Java\\u5355\\u5143\\u6d4b\\u8bd5\\u5fc5\\u5907\\u5de5\\u5177\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/551b92340001c9f206000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/551b92340001c9f206000338.jpg\",\"description\":\"Java\\u5355\\u5143\\u6d4b\\u8bd5\\u5229\\u5668!\",\"learner\":13210},{\"id\":8,\"name\":\"\\u7ec6\\u8bf4Java\\u591a\\u7ebf\\u7a0b\\u4e4b\\u5185\\u5b58\\u53ef\\u89c1\\u6027\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/5518c3d7000175af06000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/5518c3d7000175af06000338.jpg\",\"description\":\"\\u7528\\u4e24\\u79cd\\u65b9\\u5f0f\\u5b9e\\u73b0\\u5185\\u5b58\\u53ef\\u89c1\\u6027\",\"learner\":15051},{\"id\":9,\"name\":\"CSS\\u52a8\\u753b\\u5b9e\\u7528\\u6280\\u5de7\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/551b98ae0001e57906000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/551b98ae0001e57906000338.jpg\",\"description\":\"\\u6559\\u4f60\\u4f7f\\u7528CSS\\u5b9e\\u73b0\\u60ca\\u8273\\u7684\\u52a8\\u753b\\u6548\\u679c\\uff01\",\"learner\":15210},{\"id\":10,\"name\":\"C++\\u8fdc\\u5f81\\u4e4b\\u8d77\\u822a\\u7bc7\",\"picSmall\":\"http:\\/\\/img.mukewang.com\\/550b86560001009406000338-300-170.jpg\",\"picBig\":\"http:\\/\\/img.mukewang.com\\/550b86560001009406000338.jpg\",\"description\":\"C++\\u4eae\\u70b9\\u5c3d\\u5728\\u5176\\u4e2d\",\"learner\":84545}],\"msg\":\"\\u6210\\u529f\"}";
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
