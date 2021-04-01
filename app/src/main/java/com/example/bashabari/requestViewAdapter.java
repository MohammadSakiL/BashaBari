package com.example.bashabari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class requestViewAdapter extends RecyclerView.Adapter<requestViewAdapter.requestViewHolder> {

    private Context requestContext;
    private List<requestInfo> requestList;

    public requestViewAdapter(Context requestContext, List<requestInfo> requestList) {
        this.requestContext = requestContext;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public requestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(requestContext).inflate(R.layout.requests_cardview, parent, false);
        return new requestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull requestViewHolder holder, int position) {
        requestInfo req = requestList.get(position);
        holder.nameShow.setText(req.getName());
        holder.dateShow.setText(req.getDate());
        holder.textShow.setText(req.getText());
        holder.solveShow.setText(req.getSolveStat());

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class requestViewHolder extends RecyclerView.ViewHolder {
        TextView nameShow, dateShow, textShow,solveShow;
        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            nameShow = itemView.findViewById(R.id.name_rv_requests);
            dateShow = itemView.findViewById(R.id.date_rv_requests);
            textShow = itemView.findViewById(R.id.text_rv_requests);
            solveShow = itemView.findViewById(R.id.solve_rv_requests);

        }
    }
}
