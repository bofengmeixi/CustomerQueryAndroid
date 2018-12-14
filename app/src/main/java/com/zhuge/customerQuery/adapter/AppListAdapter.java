package com.zhuge.customerQuery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuge.customerQuery.Constants;
import com.zhuge.customerQuery.R;
import com.zhuge.customerQuery.model.bean.AppInfo;

import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.VH> {

    List<AppInfo> appList;
    public AppListAdapter(List<AppInfo> appList){
        this.appList=appList;
    }
    AppInfo appInfo;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_app_list,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
            appInfo=appList.get(i);
            String isDelete=isDelete(appInfo.getIs_delete());
            vh.tv_is_delete.setText(Constants.APP_STATUS +isDelete);
            vh.tv_event_sum.setText(Constants.EVENT_SUM+appInfo.getEvent_sum());
            String stop=translateStop(appInfo.getStop());
            vh.tv_app_name.setText(appInfo.getApp_name());
            vh.tv_stop.setText(Constants.STOP+stop);

        /*vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(appInfo.getId());
            }
        });*/
    }

    private String translateStop(String stop){
        if ("1".equals(stop))
            return "已停止";
        else if ("0".equals(stop))
            return "正常";
        else
            return "";
    }

    private String isDelete(String isDelete){
        if ("0".equals(isDelete))
            return "正常";
        else if ("1".equals(isDelete))
            return "已删除";
        return "未知";
    }


    @Override
    public int getItemCount() {
        if (appList!=null)
            return appList.size();
        return 0;
    }

    public static class VH extends RecyclerView.ViewHolder{
        public final TextView tv_is_delete;
        public final TextView tv_app_name;
        public final TextView tv_event_sum;
        public final TextView tv_stop;
        public VH(View view){
            super(view);
            tv_is_delete =view.findViewById(R.id.tv_is_delete);
            tv_app_name=view.findViewById(R.id.tv_app_name);
            tv_event_sum=view.findViewById(R.id.tv_event_sum);
            tv_stop=view.findViewById(R.id.tv_stop);
        }
    }

}
