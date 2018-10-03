package com.limynl.project.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.limynl.project.R;
import com.limynl.project.entity.Reply;
import com.limynl.project.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Reply Adapter
 */
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {

    private Context mContext;
    private List<Reply> mList;

    private OnItemListener mOnItemListener;

    public interface OnItemListener {
        void onItemClick(View view, Reply reply);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public ReplyAdapter(Context context, List<Reply> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.feed_reply_recycle_item, null);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReplyViewHolder holder, int position) {
        holder.bindItem(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ReplyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reply_info)
        AppCompatTextView mReplyInfo;
        private Reply mReply;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Context context, Reply reply) {
            mReply = reply;
            String replyStr = "{" + reply.getUser().getUsername() + "}回复{" + reply.getToUser().getUsername() + "}：" + reply.getCommentInfo();
            mReplyInfo.setText(Utils.colorFormat(replyStr));
        }

        @OnClick(R.id.reply_info)
        void onClick(View view){
            if (mOnItemListener != null) mOnItemListener.onItemClick(view, mReply);
        }
    }
}
