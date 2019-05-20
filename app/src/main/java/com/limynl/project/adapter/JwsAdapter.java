package com.limynl.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.entity.healthy.JwsInfo;

import java.util.List;

public class JwsAdapter extends BaseAdapter {
    private Context context;
    private List<JwsInfo> data;
    btnClickListener listener;

    public JwsAdapter(Context context, List<JwsInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.show_jws_item, null);
            viewHolder.jwsName = (TextView) convertView.findViewById(R.id.jws_name);
            viewHolder.jwsContent = (TextView) convertView.findViewById(R.id.jws_content);
            viewHolder.jwsTime = (TextView) convertView.findViewById(R.id.jws_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JwsInfo info = data.get(position);
        if (info != null) {
            viewHolder.jwsName.setText(info.getContent());
            viewHolder.jwsContent.setText(info.getBrif());
            viewHolder.jwsTime.setText(info.getTime());
        }else{
            Toast.makeText(context, "体检信息是空的", Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }

    class ViewHolder{
        public TextView jwsName;
        public TextView jwsContent;
        public TextView jwsTime;
    }

    public interface btnClickListener{
        public void btnDeleteClick(int position);

        public void btnEditClick(int position);
    }

    public void setBtnClickListener(btnClickListener btnListener) {
        this.listener = btnListener;
    }


}
