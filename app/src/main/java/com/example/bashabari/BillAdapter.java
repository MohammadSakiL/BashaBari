package com.example.bashabari;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.billViewHolder> {

    private List<BillInfo> list;
    private Context context;

    public BillAdapter() {
    }

    public BillAdapter(List<BillInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public billViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_cardview,parent,false);
        return new billViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull billViewHolder holder, int position) {

        final BillInfo billInfo = list.get(position);
        holder.hRent.setText(billInfo.getHomeRent());
        holder.wBill.setText(billInfo.getWaterBill());
        holder.gBill.setText(billInfo.getGasBill());
        holder.oBill.setText(billInfo.getOtherBill());
        holder.tBill.setText(billInfo.getTotalBill());
        holder.month.setText(billInfo.getMonth());

        holder.payBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,_14_PayBill.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class billViewHolder extends RecyclerView.ViewHolder {

        private TextView hRent,wBill,gBill,oBill,tBill,month;
        private Button payBill;

        public billViewHolder(@NonNull View itemView) {
            super(itemView);

            hRent = itemView.findViewById(R.id.txtHomeRent2);
            wBill = itemView.findViewById(R.id.txtWaterBill2);
            gBill = itemView.findViewById(R.id.txtGasBill2);
            oBill = itemView.findViewById(R.id.txtOtherBill2);
            tBill = itemView.findViewById(R.id.txtTotalBill2);
            month = itemView.findViewById(R.id.txtmonth1);
            payBill = itemView.findViewById(R.id.btnPayBill23);
        }
    }
}
