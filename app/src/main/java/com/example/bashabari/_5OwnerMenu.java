package com.example.bashabari;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class _5OwnerMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout main_layout,menu_layout;
    private RelativeLayout home_layout;
    private TextView txtAddTenant,txtSignoutOwner,txtTenantList,txtNotices,nameTitle,addressTitle;
    private ImageView btnMenu,see_more_btn;

    private RecyclerView recyclerView;
    private requestViewAdapter adapter;
    private List<requestInfo> requestList;





    private DrawerLayout drawerLayout;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__5_owner_menu);

        requestRecyclerView();

        //setContentFromDatabase();









        drawerLayout = findViewById(R.id.page_layout_5);
        navigationView = findViewById(R.id.nav_view);


        navigationView.bringToFront();



        navigationView.setNavigationItemSelectedListener(this);









        recyclerView = findViewById(R.id.request_recyclerview_5);

        nameTitle = findViewById(R.id.nameTitle_5);
        addressTitle = findViewById(R.id.addressTitle_5);

        see_more_btn = findViewById(R.id.see_more_btn_5);

        main_layout = findViewById(R.id.main_layout_5);
        menu_layout = findViewById(R.id.menu_layout_5);
        home_layout = findViewById(R.id.home_layout_5);

        txtAddTenant = findViewById(R.id.txtAddTenant5);
        txtSignoutOwner = findViewById(R.id.txtSignoutOwner5);
        txtNotices = findViewById(R.id.txtNotices5);
        txtTenantList = findViewById(R.id.txtTenantList5);

        btnMenu = findViewById(R.id.btnMenu5);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        see_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_5OwnerMenu.this,_10_More_Requests.class);
                startActivity(intent);
            }
        });




    }

    private void requestRecyclerView() {
        recyclerView = findViewById(R.id.request_recyclerview_5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        adapter = new requestViewAdapter(this, requestList);
        recyclerView.setAdapter(adapter);

        Query query = FirebaseDatabase.getInstance().getReference("Requests Database")
                .orderByChild("owner").limitToLast(1)
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

    private void setContentFromDatabase() {
        nameTitle.setText(readFromFile("111nam111.txt"));
        addressTitle.setText(readFromFile("111add111.txt"));

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(_5OwnerMenu.this,_5OwnerMenu.class);
                startActivity(intent);
                break;
            case R.id.nav_add_tenant:
                Intent intent0 = new Intent(_5OwnerMenu.this,_7_Register_Tenants.class);
                startActivity(intent0);
                break;
            case R.id.nav_notice:
                Intent intent1 = new Intent(_5OwnerMenu.this,_8_Notices.class);
                startActivity(intent1);
                break;
            case R.id.nav_manage_tenants:
                Intent intent2 = new Intent(_5OwnerMenu.this,_9_Manage_Tenants.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                Intent intent3 = new Intent(_5OwnerMenu.this,_3Login.class);
                startActivity(intent3);
                break;
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
