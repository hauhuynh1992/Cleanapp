package com.example.huynhphihau.cleanservice.dashboard;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.data.response.ReportData;
import com.example.huynhphihau.cleanservice.util.TimeFormatUtils;

import java.util.ArrayList;

/**
 * Created by phihau on 4/20/2017.
 */

public class ReportHistoryAdapter extends RecyclerView.Adapter<ReportHistoryAdapter.ReportHistoryHoler> {
    private ArrayList<ReportData> mData;
    OnTaskItemClickListener mItemClickListener;

    public ReportHistoryAdapter(OnTaskItemClickListener listener) {
        this.mData = new ArrayList<>();
        this.mItemClickListener = listener;
    }

    class ReportHistoryHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_date, txt_company, txt_bulding, txt_builing_level;
        public Context mContext;

        public ReportHistoryHoler(View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_rep_history_date);
            txt_company = itemView.findViewById(R.id.txt_req_history_rep_company);
            txt_bulding = itemView.findViewById(R.id.txt_req_history_rep_building);
            txt_builing_level = itemView.findViewById(R.id.txt_req_history_rep_building_level);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public ReportHistoryHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View historyView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_report_history, parent, false);
        return new ReportHistoryHoler(historyView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ReportHistoryHoler holder, int position) {
        // Check parameter
        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        ReportData reportData = mData.get(position);
        if (reportData == null) {
            return;
        }

        if(reportData.getIsRead() == BaseConfig.REPORT_UNREAD){
            holder.txt_date.setTextColor(holder.mContext.getColor(R.color.colorGreen));
        } else {
            holder.txt_date.setTextColor(holder.mContext.getColor(R.color.colorGray));
        }

        holder.txt_date.setText(TimeFormatUtils.formatDate(reportData.getCreateTime()));
        holder.txt_company.setText(reportData.getCompanyName());
        holder.txt_bulding.setText(reportData.getBuildingName());
//        holder.txt_builing_level.setText(reportData.getBuildingLevelName());
        holder.txt_builing_level.setText(reportData.getTitle());
    }


    @Override
    public int getItemCount() {
        return this.mData.size();
    }


    public ReportData getItem(int position) {
        return this.mData.get(position);
    }

    /**
     * clear data
     */
    public void clearData() {
        this.mData.clear();
        notifyDataSetChanged();
    }


    public void setData(ArrayList<ReportData> requestDatas) {
        this.mData.addAll(requestDatas);
        notifyDataSetChanged();
    }

    public interface OnTaskItemClickListener {
        void onItemClick(int postion);

    }

}