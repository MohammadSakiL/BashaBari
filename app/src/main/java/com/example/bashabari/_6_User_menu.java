package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class _6_User_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ImageView menu_btn;
    private ImageView see_more_6;

    private TextView name_title, address_title;

    private RecyclerView notice_recyclerView;
    private noticeViewAdapter adapter;
    private List<noticeInfo> noticeList;

    private DrawerLayout drawerLayout;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__6_user_menu);


        noticeRecycler();




        menu_btn = findViewById(R.id.btnMenu6);
        see_more_6 = findViewById(R.id.see_more_btn_6);
        name_title= findViewById(R.id.nameTitle_6);
        address_title= findViewById(R.id.addressTitle_6);

        name_title.setText(readFromFile("111nam111.txt").trim());
        address_title.setText(readFromFile("111add111.txt").trim());



        drawerLayout = findViewById(R.id.page_layout_6);
        navView = findViewById(R.id.tenantNav);

        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);




        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        see_more_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_6_User_menu.this,_11_More_Notices.class);
                startActivity(intent);
            }
        });




    }

        private void noticeRecycler() {
            notice_recyclerView = findViewById(R.id.request_recyclerview_6);
            notice_recyclerView.setHasFixedSize(true);
            notice_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            noticeList = new ArrayList<>();
            adapter = new noticeViewAdapter(this, noticeList);
            notice_recyclerView.setAdapter(adapter);

            Query query = FirebaseDatabase.getInstance().getReference("Notice Database")
                    .orderByChild("phone_no").limitToLast(30)
                    .equalTo(readFromFile("111t_ow111.txt").trim());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    noticeList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            noticeInfo not = snapshot.getValue(noticeInfo.class);
                            noticeList.add(not);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tenat_profile:
                Intent intent = new Intent(_6_User_menu.this,Update_tenant_profile.class);
                startActivity(intent);
                break;
            case R.id.tenant_message:
                Intent intent0 = new Intent(_6_User_menu.this,_17RecivedMessage.class);
                startActivity(intent0);
                break;
            case R.id.tenant_request:
                Intent intent1 = new Intent(_6_User_menu.this,_12_Requests.class);
                startActivity(intent1);
                break;
            case R.id.nav_bill:
                Intent intent6 = new Intent(_6_User_menu.this,_24_See_bill.class);
                startActivity(intent6);
                break;
            case R.id.tenant_payment:
                Intent intent2 = new Intent(_6_User_menu.this,_14_PayBill.class);
                startActivity(intent2);
                break;
            case R.id.tenant_logout:
                FileOutputStream fos0 = null;
                try {

                    fos0 = openFileOutput("369sta369.txt", MODE_PRIVATE);
                    fos0.write("logged_out".getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent3 = new Intent(_6_User_menu.this,_3Login.class);
                startActivity(intent3);
                break;

        }


        return true;
    }


    private String readFromFile(String File_Name){

        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
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