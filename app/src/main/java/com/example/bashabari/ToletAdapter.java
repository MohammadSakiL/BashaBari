package com.example.bashabari;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ToletAdapter extends RecyclerView.Adapter<ToletAdapter.toletViewHolder> {

    private Context context;
    private List<ToletInfo> list;
    private List<ToletInfo> filterList;
    private String type;

    public ToletAdapter(Context context, List<ToletInfo> list,String type) {
        this.context = context;
        this.list = list;
        this.filterList = list;
        this.type = type;
    }

    @NonNull
    @Override
    public toletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tolet_cardview,parent,false);
        return new toletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull toletViewHolder holder, int position) {

        final ToletInfo toletInfo = filterList.get(position);

        holder.price.setText("BDT "+toletInfo.getPrice());
        holder.location.setText(toletInfo.getLocation());
        holder.flatNo.setText("Flat No. "+toletInfo.getFlat_no());
        holder.number.setText("Contact: "+ toletInfo.getContact_number());

        holder.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toletInfo.getContact_number()));
                context.startActivity(intent);
            }
        });

        try {
            Picasso.get().load(toletInfo.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class toletViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView price,location,number,flatNo;

        public toletViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cImage);
            price = itemView.findViewById(R.id.cPrice);
            location = itemView.findViewById(R.id.cLocation);
            number = itemView.findViewById(R.id.cPhoneNumber);
            flatNo = itemView.findViewById(R.id.cFlatNo);


        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if(key.isEmpty()){
                    filterList = list;
                }else {
                    List<ToletInfo> listFilter = new ArrayList<>();
                    for(ToletInfo row: list){
                        if(row.getLocation().toLowerCase().contains(key.toLowerCase())){
                            listFilter.add(row);
                        }
                    }
                    filterList = listFilter;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {

                filterList = (List<ToletInfo>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
