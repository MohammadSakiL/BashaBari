package com.example.bashabari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noticeViewAdapter extends RecyclerView.Adapter<noticeViewAdapter.noticeViewHolder> {

    private Context nContext;
    private List<noticeInfo> noticeList;

    public noticeViewAdapter(Context nContext, List<noticeInfo> noticeList) {
        this.nContext = nContext;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public noticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nContext).inflate(R.layout.notice_cardview,parent,false);

        return new noticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull noticeViewHolder holder, int position) {
        noticeInfo nInfo =noticeList.get(position);

        holder.nameShow.setText(nInfo.getName());
        holder.dateShow.setText(nInfo.getDate());
        holder.textShow.setText(nInfo.getText());

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class noticeViewHolder extends RecyclerView.ViewHolder {

        TextView nameShow, dateShow, textShow;

        public noticeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameShow = itemView.findViewById(R.id.name_notices);
            dateShow = itemView.findViewById(R.id.date_notices);
            textShow = itemView.findViewById(R.id.text_notices);
        }
    }
}
