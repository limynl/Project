package com.limynl.project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.limynl.project.R;
import com.limynl.project.utils.ContentUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * email 1434117404@qq.com
 */

public class SendSocialMessageAdapter extends RecyclerView.Adapter<SendSocialMessageAdapter.SendSocialMessageViewHolder>{

    public static final String mPhotoAdd = "file:///android_asset/icon_photo_add.png";
    private List<String> mPhotos;

    private OnItemClickListener mOnItemClickListener;

    public SendSocialMessageAdapter(List<String> photos) {
        this.mPhotos = photos;
        if (mPhotos.size() < 6 && !mPhotos.contains(mPhotoAdd)) mPhotos.add(mPhotoAdd);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public SendSocialMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.send_photo_recycle_item, null);
        return new SendSocialMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SendSocialMessageViewHolder holder, int position) {
        holder.bindItem(mPhotos.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public void setPhotos(List<String> photos) {
        this.mPhotos = photos;
        if (mPhotos.size() < 6 && !mPhotos.contains(mPhotoAdd)) mPhotos.add(mPhotoAdd);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onPhotoClick(int position);
        void onDelete(int position);
    }

    class SendSocialMessageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.iv_delete)
        ImageView mIvDelete;
        private int mPosition;

        public SendSocialMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(String photoUrl, final int position) {
            mPosition = position;
            if (mPhotos.get(position).equals(mPhotoAdd)) {
                mIvDelete.setVisibility(View.GONE);
            } else {
                mIvDelete.setVisibility(View.VISIBLE);
            }

            ContentUtil.loadImage(mIvPhoto , photoUrl);
        }

        @OnClick({R.id.iv_photo, R.id.iv_delete})
        public void onClick(View view){
            switch (view.getId()) {
                case R.id.iv_photo:
                    if (mOnItemClickListener != null) mOnItemClickListener.onPhotoClick(mPosition);
                    break;
                case R.id.iv_delete:
                    if (mOnItemClickListener != null) mOnItemClickListener.onDelete(mPosition);
                    break;
            }
        }
    }
}
