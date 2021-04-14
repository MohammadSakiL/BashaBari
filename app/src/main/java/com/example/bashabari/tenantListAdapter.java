package com.example.bashabari;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class tenantListAdapter extends RecyclerView.Adapter<tenantListAdapter.tenantViewHolder> {

    private List<tenantInfo> tenantList;
    private Context context;
    private RecyclerViewClickListener listener;

    public tenantListAdapter(Context context,List<tenantInfo> tenantList,RecyclerViewClickListener listener){
        this.context =context;
        this.tenantList =tenantList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public tenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenant_list_cardview,parent,false);
        return new tenantListAdapter.tenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tenantViewHolder holder, int position) {
        tenantInfo tenantInfo =tenantList.get(position);
        holder.tenantListNameCardview.setText(tenantInfo.getName());
        holder.getTenantListPhoneNumberCardview.setText(tenantInfo.getPhone_no());
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }

    public class tenantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView getTenantListPhoneNumberCardview;
        CheckBox tenantListNameCardview;

        public tenantViewHolder(@NonNull View itemView) {
            super(itemView);
            tenantListNameCardview = itemView.findViewById(R.id.tenantListNameCardview);
            getTenantListPhoneNumberCardview = itemView.findViewById(R.id.tenantListPhoneNumberCardview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }


}