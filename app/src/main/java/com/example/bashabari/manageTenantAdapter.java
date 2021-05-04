package com.example.bashabari;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class manageTenantAdapter extends RecyclerView.Adapter<manageTenantAdapter.managetenantViewHolder> {

    private DatabaseReference databaseReference;





    private Context context;
    private List<tenantInfo> list;
    private List<tenantInfo> filterList;

    public manageTenantAdapter(Context context, List<tenantInfo> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
    }

    @NonNull
    @Override
    public managetenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenant_new_cardview,parent,false);
        return new managetenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull managetenantViewHolder holder, int position) {

        final tenantInfo item = filterList.get(position);

        holder.name.setText(item.getName());
        holder.flatNo.setText(item.getAddress());
        holder.phoneNumber.setText(item.getPhone_no());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Tenant Database");
                databaseReference.child(item.getPhone_no()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FancyToast.makeText(context,"Data delete Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        Intent intent = new Intent(context,_5OwnerMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FancyToast.makeText(context,"Something went wrong",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();

                    }
                });
            }
        });

        holder.nid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,_26_View_nid.class);
                intent.putExtra("phoneNumber",item.getPhone_no());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class managetenantViewHolder extends RecyclerView.ViewHolder {

        private TextView name,phoneNumber,flatNo;
        private ImageView tenantImage;
        private Button remove,nid;

        public managetenantViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tenantName);
            phoneNumber = itemView.findViewById(R.id.tenantPhoneNumber);
            flatNo = itemView.findViewById(R.id.tenantFlat);
            tenantImage = itemView.findViewById(R.id.tenantImage);
            remove = itemView.findViewById(R.id.removeTenant);
            nid = itemView.findViewById(R.id.tenantNid);

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
                    List<tenantInfo> listFilter = new ArrayList<>();
                    for(tenantInfo row: list){
                        if(row.getName().toLowerCase().contains(key.toLowerCase())){
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

                filterList = (List<tenantInfo>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
