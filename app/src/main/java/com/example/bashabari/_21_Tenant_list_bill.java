package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class _21_Tenant_list_bill extends AppCompatActivity {

    private ImageView imgarrowBack;


    DatabaseReference tenantRef;
    private RecyclerView recycleViewItem;
    private List<tenantInfo> tenantList;
    private tenantListAdapter adapter;
    private tenantListAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__21__tenant_list_bill);

        tenantRef = FirebaseDatabase.getInstance().getReference().child("Tenant Database");

        tenantsRecyclerView();



        imgarrowBack = findViewById(R.id.imgarrowBack21);
        imgarrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_21_Tenant_list_bill.this,_5OwnerMenu.class);
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_21);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_21_Tenant_list_bill.this, _21_Tenant_list_bill.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    private void tenantsRecyclerView(){
        setOnClickListener();
        recycleViewItem = findViewById(R.id.recycleViewItem21);
        tenantList = new ArrayList<>();
        adapter = new tenantListAdapter(this,tenantList,listener);

        recycleViewItem.setHasFixedSize(true);
        recycleViewItem.setLayoutManager(new LinearLayoutManager(this));
        recycleViewItem.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Tenant Database").orderByChild("owner").equalTo(readFromFile("111pho111.txt").trim());



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //tenantList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        tenantInfo ten = snapshot.getValue(tenantInfo.class);
                        tenantList.add(ten);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setOnClickListener() {
        listener = new tenantListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(),_20_Bill_owner.class);
                intent.putExtra("name",tenantList.get(position).getName());
                intent.putExtra("phone_number",tenantList.get(position).getPhone_no());
                startActivity(intent);
            }
        };
    }

    private String readFromFile(String File_Name) {

        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 = openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;

    }

}