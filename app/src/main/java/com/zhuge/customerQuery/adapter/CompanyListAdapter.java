package com.zhuge.customerQuery.adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.zhuge.analysis.stat.ZhugeSDK;
import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.R;

import com.zhuge.customerQuery.listener.OnItemClickListener;
import com.zhuge.customerQuery.model.bean.CompanyInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.VH> {

    List<CompanyInfo> companyList;
    OnItemClickListener onItemClickListener;

    public CompanyListAdapter(List<CompanyInfo> companyList, OnItemClickListener onItemClickListener) {
        this.companyList = companyList;
        this.onItemClickListener = onItemClickListener;
    }

    CompanyInfo companyInfo;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company_list, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
        companyInfo = companyList.get(i);
        final String companyName = companyInfo.getCompany_name();
        final String companyId = companyInfo.getId();
        final String eventLimit = companyInfo.getDiscount_event_limit();
        final String stopTime = companyInfo.getVersion_stop_time();
        vh.tvCompanyName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        vh.tvCompanyName.getPaint().setAntiAlias(true);
        vh.tvCompanyName.setText(companyName);
        final String version = translateVersion(companyInfo.getVersion());
        vh.tvVersion.setText(version);
        vh.tvCompanyId.setText(Constants.ID + companyId);
        vh.tvEventLimit.setText(Constants.DISCOUNT_LINMIT_EVENT + eventLimit);
        vh.tvStopTime.setText(Constants.VERSION_STOP_TIME + stopTime);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("公司名称", companyName);
                    jsonObject.put("公司id", companyId);
                    jsonObject.put("版本", version);
                    jsonObject.put("月事件量", eventLimit);
                    jsonObject.put("到期时间", stopTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ZhugeSDK.getInstance().track(vh.itemView.getContext().getApplicationContext(), "公司详情", jsonObject);

                onItemClickListener.onItemClick(companyId);
            }
        });
    }

    private String translateVersion(String version) {
        if ("1".equals(version))
            return "过期版";
        else if ("9".equals(version))
            return "基础版";
        else if ("10".equals(version))
            return "专业版";
        else if ("11".equals(version))
            return "企业版";
        else
            return "";
    }

    @Override
    public int getItemCount() {
        if (companyList != null)
            return companyList.size();
        return 0;
    }

    public static class VH extends RecyclerView.ViewHolder {
        public final TextView tvCompanyName;
        public final TextView tvVersion;
        public final TextView tvCompanyId;
        public final TextView tvEventLimit;
        public final TextView tvStopTime;

        public VH(View view) {
            super(view);
            tvCompanyName = view.findViewById(R.id.tv_company_name);
            tvVersion = view.findViewById(R.id.tv_version);
            tvCompanyId = view.findViewById(R.id.tv_company_id);
            tvEventLimit = view.findViewById(R.id.tv_event_limit);
            tvStopTime = view.findViewById(R.id.tv_stop_time);
        }
    }

}
