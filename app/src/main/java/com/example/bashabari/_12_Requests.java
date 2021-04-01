package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

public class _12_Requests extends AppCompatActivity {

    private ImageView back_arrow_btn;
    private ImageView add_rq_btn;
    private EditText edit_rq_field;
    public DatabaseReference requestRef;

    private RecyclerView recyclerView;
    private requestViewAdapter adapter;
    private List<requestInfo> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__12__requests);

        requestRecyclerView();

        back_arrow_btn = findViewById(R.id.back_arrow_btn_12);
        add_rq_btn = findViewById(R.id.add_rq_btn_12);
        edit_rq_field = findViewById(R.id.edit_rq_box_12);

        requestRef = FirebaseDatabase.getInstance().getReference().child("Requests Database");


        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8 = new Intent(_12_Requests.this, _6_User_menu.class);
                startActivity(intent8);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        add_rq_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final String text_req, owner_no, phone_no, name, date, solveStat;
                text_req = edit_rq_field.getText().toString().trim();
                name = readFromFile("111nam111.txt").trim();
                phone_no = readFromFile("111pho111.txt").trim();
                owner_no = readFromFile("111t_ow111.txt").trim();


                if (text_req.isEmpty())
                    edit_rq_field.setError("The request field is empty");

                else {
                    date = getTodaysDate();
                    solveStat = "[Solved: No]";
                    saveToDatabase(date, name, owner_no, phone_no, solveStat, text_req);
                    edit_rq_field.setText(null);
                }
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_12);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_12_Requests.this, _12_Requests.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void requestRecyclerView() {
        recyclerView = findViewById(R.id.request_recyclerview_12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        adapter = new requestViewAdapter(this, requestList);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Requests Database")
                .orderByChild("phone_no")
                .equalTo(readFromFile("111pho111.txt").trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        requestInfo req = snapshot.getValue(requestInfo.class);
                        requestList.add(req);
                    }
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveToDatabase(final String date, final String name, final String owner_no, final String phone_no, final String solveStat, final String text_req) {

        requestRef.child(phone_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestInfo usrinf = new requestInfo(date, name, owner_no, phone_no, solveStat, text_req);
                requestRef.push().setValue(usrinf);


                Toast.makeText(getApplicationContext(), "Request has been sent", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' h:mm a");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        return dateString;
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


    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}