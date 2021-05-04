package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class _28_View_tolet extends AppCompatActivity {

    private EditText serach;
    private RecyclerView sellApartment,rentApartment;
    private LinearLayout sellNoData,rentNodata;

    private CharSequence search="";

    private ToletAdapter adapter;


    private DatabaseReference databaseReference,dbRef;

    private List<ToletInfo> list1,list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__28__view_tolet);

        serach = findViewById(R.id.search_home);

        rentApartment = findViewById(R.id.rentApartment28);
        sellApartment = findViewById(R.id.sellApartment28);

        sellNoData = findViewById(R.id.sellNoData);
        rentNodata = findViewById(R.id.rentNoData);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rent Database");

        serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        rentApartment();
        sellApartment();



    }


    private void rentApartment() {

        dbRef = databaseReference.child("Rent");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    rentNodata.setVisibility(View.VISIBLE);
                    rentApartment.setVisibility(View.GONE);

                }else {
                    rentNodata.setVisibility(View.GONE);
                    rentApartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        ToletInfo data = dataSnapshot.getValue(ToletInfo.class);
                        list1.add(data);
                    }
                    rentApartment.hasFixedSize();
                    rentApartment.setLayoutManager(new LinearLayoutManager(_28_View_tolet.this));
                    adapter = new ToletAdapter(_28_View_tolet.this,list1,"Rent");
                    rentApartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sellApartment() {

        dbRef = databaseReference.child("Sell");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    sellNoData.setVisibility(View.VISIBLE);
                    sellApartment.setVisibility(View.GONE);

                }else {
                    sellNoData.setVisibility(View.GONE);
                    sellApartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        ToletInfo data = dataSnapshot.getValue(ToletInfo.class);
                        list2.add(data);
                    }
                    sellApartment.hasFixedSize();
                    sellApartment.setLayoutManager(new LinearLayoutManager(_28_View_tolet.this));
                    adapter = new ToletAdapter(_28_View_tolet.this,list2,"Sell");
                    sellApartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}