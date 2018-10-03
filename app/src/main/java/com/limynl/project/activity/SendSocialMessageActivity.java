package com.limynl.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.adapter.SendSocialMessageAdapter;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.constants.Constants;
import com.limynl.project.entity.Feed;
import com.limynl.project.entity.result.Result;
import com.limynl.project.utils.ImageUtil;
import com.limynl.project.utils.okhttp.OkUtil;
import com.limynl.project.utils.okhttp.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

/**
 * email 1434117404@qq.com
 */

public class SendSocialMessageActivity extends TopBarBaseActivity {
    @BindView(R.id.feed_info)
    AppCompatEditText mMoodInfo;
    @BindView(R.id.iv_submit)
    ImageView mIvSubmit;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SendSocialMessageAdapter mPhotoSelAdapter;
    private List<String> mPhotos = new ArrayList<>();
    private String mInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_send_social_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.send_social_message));
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        setLoading("发布中...");
        initRecycleView();
    }

    private void initRecycleView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(SendSocialMessageActivity.this, 3));
        mPhotoSelAdapter = new SendSocialMessageAdapter(mPhotos);
        mRecyclerView.setAdapter(mPhotoSelAdapter);
        mPhotoSelAdapter.setOnItemClickListener(new SendSocialMessageAdapter.OnItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                if (mPhotos.get(position).equals(SendSocialMessageAdapter.mPhotoAdd)) {
                    mPhotos.remove(position);
                    PhotoPicker.builder()
                            .setPhotoCount(6)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setSelected((ArrayList<String>) mPhotos)
                            .setPreviewEnabled(false)
                            .start(SendSocialMessageActivity.this, PhotoPicker.REQUEST_CODE);
                } else {
                    if (mPhotos.contains(SendSocialMessageAdapter.mPhotoAdd))
                        mPhotos.remove(SendSocialMessageAdapter.mPhotoAdd);
                    PhotoPreview.builder()
                            .setPhotos((ArrayList<String>) mPhotos)
                            .setCurrentItem(position)
                            .setShowDeleteButton(true)
                            .start(SendSocialMessageActivity.this);
                }
            }

            @Override
            public void onDelete(int position) {
                mPhotos.remove(position);
                mPhotoSelAdapter.setPhotos(mPhotos);
            }
        });
    }

    @OnClick(R.id.iv_submit)
    public void onClick() {
        mInfo = mMoodInfo.getText().toString().trim();
        if (TextUtils.isEmpty(mInfo)) {
            Toast.makeText(this, "多少还是写点东西吧~~~", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPhotos.size() <= 1) {
//            postSaveFeed(mPhotos);
            Toast.makeText(SendSocialMessageActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
//            postUpload(mPhotos);
            Toast.makeText(SendSocialMessageActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    // 上传图片
    private void postUpload(List<String> photos) {
        removePhotoAdd(photos);

        // 压缩图片
        photos = ImageUtil.compressorImage(this, photos);

        OkUtil.post()
                .url(Constants.uploadFeedImage)
                .addFiles("file", ImageUtil.pathToImageFile(photos))
                .execute(new ResultCallback<Result<List<String>>>() {
                    @Override
                    public void onSuccess(Result<List<String>> response) {
                        String code = response.getCode();
                        if ("00100".equals(code)) {
                            Toast.makeText(SendSocialMessageActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                            addPhotoAdd(mPhotos);
                            return;
                        }
                        if (!"00000".equals(code)) {
                            Toast.makeText(SendSocialMessageActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                            addPhotoAdd(mPhotos);
                            return;
                        }
                        // 发送动态
                        postSaveFeed(response.getData());
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(SendSocialMessageActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                        addPhotoAdd(mPhotos);
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(SendSocialMessageActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                        addPhotoAdd(mPhotos);
                    }
                });
    }

    // 发布动态
    private void postSaveFeed(List<String> uploadImg) {
        removePhotoAdd(uploadImg);
        OkUtil.post()
                .url(Constants.saveFeed)
                .addParam("userId", 1)
                .addParam("feedInfo", mInfo)
                .addUrlParams("photoList", uploadImg)
                .execute(new ResultCallback<Result<Feed>>() {
                    @Override
                    public void onSuccess(Result<Feed> response) {
                        dismissLoading();
                        String code = response.getCode();
                        if (!"00000".equals(code)) {
                            Toast.makeText(SendSocialMessageActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                            addPhotoAdd(mPhotos);
                            return;
                        }
                        mMoodInfo.setText(null);
                        Toast.makeText(SendSocialMessageActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        dismissLoading();
                        Toast.makeText(SendSocialMessageActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                        addPhotoAdd(mPhotos);
                    }

                    @Override
                    public void onFinish() {
                        dismissLoading();
                        Toast.makeText(SendSocialMessageActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                        addPhotoAdd(mPhotos);
                    }
                });
    }

    // 添加添加图片按钮
    private void addPhotoAdd(List<String> photList) {
        if (!photList.contains(SendSocialMessageAdapter.mPhotoAdd)) {
            photList.add(SendSocialMessageAdapter.mPhotoAdd);
        }
    }

    // 去除添加图片按钮
    private void removePhotoAdd(List<String> photList) {
        if (photList.contains(SendSocialMessageAdapter.mPhotoAdd)) {
            photList.remove(SendSocialMessageAdapter.mPhotoAdd);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case PhotoPicker.REQUEST_CODE:
                    case PhotoPreview.REQUEST_CODE:
                        mPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        break;
                }
            }
        }
        mPhotoSelAdapter.setPhotos(mPhotos);
    }

    @Override
    public void onBackPressed() {
        // 此处监听回退，通知首页刷新
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.GO_INDEX, R.id.id_tab_rank);//社区
        intent.putExtras(bundle);
        setResult(Constants.ACTIVITY_PUBLISH, intent);
        finish();
    }
}
