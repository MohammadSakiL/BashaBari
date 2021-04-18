package com.example.bashabari;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.messageViewHolder> {

    private List<messageInfo> messageInfoListList;
    private Context context;

    public Message_Adapter(List<messageInfo> messageInfoListList, Context context) {
        this.messageInfoListList = messageInfoListList;
        this.context = context;
    }



    @NonNull
    @Override
    public messageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_cardview,parent,false);
        return new messageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull messageViewHolder holder, int position) {

        messageInfo mInfo = messageInfoListList.get(position);
        holder.message.setText(mInfo.getMessage());
        holder.date.setText(mInfo.getDate());

    }

    @Override
    public int getItemCount() {
        return messageInfoListList.size();
    }

    public class messageViewHolder extends RecyclerView.ViewHolder {

        private TextView message,date;
        public messageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.owner_message);
            date = itemView.findViewById(R.id._owner_message_date);

        }
    }
}
