package com.limynl.project.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.activity.SendSocialMessageActivity;
import com.limynl.project.activity.SocialMessageDetailActivity;
import com.limynl.project.adapter.SocialMessageAdapter;
import com.limynl.project.base.LazyLoadFragment;
import com.limynl.project.constants.Constants;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.entity.Feed;
import com.limynl.project.entity.PageInfo;
import com.limynl.project.entity.User;
import com.limynl.project.entity.UserInfo;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.cl.library.loadmore.LoadMord;
import me.cl.library.loadmore.OnLoadMoreListener;
import me.cl.library.recycle.ItemAnimator;
import me.cl.library.recycle.ItemDecoration;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

public class SocialFragment extends LazyLoadFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Unbinder unbinder;
    private Context mContext;
    private UserInfo userInfo;

    private static final String FEED_TYPE = "feed_type";

    private List<Feed> mList = new ArrayList<>();
    private SocialMessageAdapter mAdapter;

    private int mPage = 1;
    private int mCount = 10;
    private final int MOD_REFRESH = 1;
    private final int MOD_LOADING = 2;
    private int RefreshMODE = 0;

    public static SocialFragment newInstance(String feedType) {
        SocialFragment fragment = new SocialFragment();
        Bundle args = new Bundle();
        args.putString(FEED_TYPE, feedType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mType = getArguments().getString(FEED_TYPE);
        }
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

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new ItemAnimator());
        ItemDecoration itemDecoration = new ItemDecoration(ItemDecoration.VERTICAL, 10, Color.parseColor("#f2f2f2"));
        // 隐藏最后一个item的分割线
        itemDecoration.setGoneLast(true);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new SocialMessageAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        initEvent();
        getMoodList(mPage, mCount);
    }

    private void initEvent() {
        //刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshMODE = MOD_REFRESH;
                mPage = 1;
                getMoodList(mPage, mCount);
            }
        });

        //item点击
        mAdapter.setOnItemListener(new SocialMessageAdapter.OnItemListener() {
            @Override
            public void onItemClick(View view, Feed feed, int position) {
                switch (view.getId()) {
                    case R.id.user_img:
                        goToUser(feed.getUser());
                        break;
                    case R.id.feed_card:
                    case R.id.feed_comment_layout:
                        gotoMood(feed);
                        break;
                    case R.id.feed_like_layout:
                        if (feed.isLike()) return;
                        // 未点赞点赞
                        postAddLike(feed, position);
                        break;
                }
            }

            @Override
            public void onPhotoClick(ArrayList<String> photos, int position) {

                PhotoPreview.builder()
                        .setPhotos(photos)
                        .setCurrentItem(position)
                        .setShowDeleteButton(false)
                        .start(getActivity());
            }
        });

        //滑动监听
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (mAdapter.getItemCount() < 4) return;

                RefreshMODE = MOD_LOADING;
                mAdapter.updateLoadStatus(LoadMord.LOAD_MORE);

                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMoodList(mPage, mCount);
                    }
                },1000);
            }
        });
    }

    //个人相关
    private void goToUser(User user) {
        Toast.makeText(mContext, "个人相关", Toast.LENGTH_SHORT).show();
    }

    // 设置数据
    private void setData(List<Feed> data){
        mAdapter.setData(data);
    }

    // 更新数据
    public void updateData(List<Feed> data) {
        mAdapter.addData(data);
    }

    // 刷新数据
    private void onRefresh(){
        RefreshMODE = MOD_REFRESH;
        mPage = 1;
        getMoodList(mPage, mCount);
    }

    // 前往动态详情
    private void gotoMood(Feed feed) {
        Intent intent = new Intent(getActivity(), SocialMessageDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("feed", feed);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.ACTIVITY_MOOD);
    }

    // 点赞
    private void postAddLike(final Feed feed, final int position) {
        Toast.makeText(mContext, "点赞", Toast.LENGTH_SHORT).show();
    }

    // 获取动态列表
    private void getMoodList(int pageNum, int pageSize) {
        if (!mSwipeRefreshLayout.isRefreshing() && RefreshMODE == MOD_REFRESH) mSwipeRefreshLayout.setRefreshing(true);
//        String uid = SPUtil.build().getString(Constants.USER_ID);
        OkUtil.post()
                .url(Constants.API_URL)
                .addParam("userId", "6c0f824f03594fac9f4a156b3baf99b6")
                .addParam("pageNum", pageNum)
                .addParam("pageSize", pageSize)
                .execute(new ResultCallback<Result<PageInfo<Feed>>>() {
                    @Override
                    public void onSuccess(Result<PageInfo<Feed>> response) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        String code = response.getCode();
                        if (!"00000".equals(code)) {
                            mAdapter.updateLoadStatus(LoadMord.LOAD_NONE);
//                            showToast(R.string.toast_get_feed_error);
                            Toast.makeText(mContext, "数据获取失败！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        PageInfo<Feed> page = response.getData();
                        Integer size = page.getSize();
                        if (size == 0) {
                            mAdapter.updateLoadStatus(LoadMord.LOAD_NONE);
                            return;
                        }
                        mPage++;
                        List<Feed> list = page.getList();
                        switch (RefreshMODE) {
                            case MOD_LOADING:
                                updateData(list);
                                break;
                            default:
                                setData(list);
                                break;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.updateLoadStatus(LoadMord.LOAD_NONE);
//                        showToast(R.string.toast_get_feed_error);
                        Toast.makeText(mContext, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.updateLoadStatus(LoadMord.LOAD_NONE);
//                        showToast(R.string.toast_get_feed_error);
                        Toast.makeText(mContext, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick({R.id.send_social})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_social:{
                Intent intent = new Intent(getActivity(), SendSocialMessageActivity.class);
                startActivityForResult(intent, Constants.ACTIVITY_PUBLISH);
            }break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}