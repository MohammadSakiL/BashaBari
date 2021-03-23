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

public class _9_Manage_Tenants extends AppCompatActivity {

    private ImageView imgarrowBack;


    DatabaseReference tenantRef;
    private RecyclerView recycleViewItem;
    private List<tenantInfo> tenantList;
    private tenantListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__9__manage__tenants);

        tenantRef = FirebaseDatabase.getInstance().getReference().child("Tenant Database");

        tenantsRecyclerView();



        imgarrowBack = findViewById(R.id.imgarrowBack9);
        imgarrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_9_Manage_Tenants.this,_5OwnerMenu.class);
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_9);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_9_Manage_Tenants.this, _9_Manage_Tenants.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void tenantsRecyclerView(){
        recycleViewItem = findViewById(R.id.recycleViewItem9);
        tenantList = new ArrayList<>();
        adapter = new tenantListAdapter(this,tenantList);

        recycleViewItem.setHasFixedSize(true);
        recycleViewItem.setLayoutManager(new LinearLayoutManager(this));
        recycleViewItem.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Tenant Database").orderByChild("owner").equalTo("01867780751");

//    query.addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            tenantList.clear();
//            if(snapshot.exists()){
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    tenantInfo tentant = dataSnapshot.getValue(tenantInfo.class);
//                    tenantList.add(tentant);
//                }
//                adapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear();
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





}