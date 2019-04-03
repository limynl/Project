package com.limynl.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.limynl.project.R;
import com.limynl.project.entity.healthy.CheckInfo;

import java.util.List;

public class ShowCarAdapter extends BaseAdapter {
    private Context context;
    private List<CheckInfo> data;
    btnClickListener listener;

    public ShowCarAdapter(Context context, List<CheckInfo> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.show_car_item, null);
            viewHolder.checkTime = (TextView) convertView.findViewById(R.id.check_time);
            viewHolder.checkWeight = (TextView) convertView.findViewById(R.id.check_weight);
            viewHolder.checkHeight = (TextView) convertView.findViewById(R.id.check_height);
            viewHolder.checkHeartBeat = (TextView) convertView.findViewById(R.id.check_hearbeat);
            viewHolder.checkRemarks = (TextView) convertView.findViewById(R.id.check_remarks);
            viewHolder.checkDetail = (Button) convertView.findViewById(R.id.check_detail);
            viewHolder.checkDelete = (Button) convertView.findViewById(R.id.check_delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CheckInfo info = data.get(position);
        if (info != null) {
            viewHolder.checkTime.setText(info.getTime());
            viewHolder.checkWeight.setText(info.getWeight() + "KG");
            viewHolder.checkHeight.setText(info.getHeight() + "CM");
            viewHolder.checkHeartBeat.setText(info.getHeartbeat() + "次/分");
            viewHolder.checkRemarks.setText(info.getRemarks());
        }else{
            Toast.makeText(context, "体检信息是空的", Toast.LENGTH_SHORT).show();
        }

        viewHolder.checkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.btnDeleteClick(position);
            }
        });

        viewHolder.checkDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.btnEditClick(position);
            }
        });


        return convertView;
    }

    class ViewHolder{
        public TextView checkTime;
        public TextView checkWeight;
        public TextView checkHeight;
        public TextView checkHeartBeat;
        public TextView checkRemarks;
        public Button checkDetail;
        public Button checkDelete;
    }

    public interface btnClickListener{
        public void btnDeleteClick(int position);

        public void btnEditClick(int position);
    }

    public void setBtnClickListener(btnClickListener btnListener) {
        this.listener = btnListener;
    }


}
