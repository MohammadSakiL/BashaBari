package com.example.bashabari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class _5OwnerMenu extends AppCompatActivity {

    private LinearLayout main_layout,menu_layout;
    private RelativeLayout home_layout;
    private TextView txtAddTenant,txtSignoutOwner,txtTenantList,txtNotices;
    private ImageView btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__5_owner_menu);

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
                menu_layout.setVisibility(View.VISIBLE);
                home_layout.setVisibility(View.GONE);
            }
        });




        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_layout.getVisibility() == View.VISIBLE){
                    menu_layout.setVisibility(View.GONE);
                    home_layout.setVisibility(View.VISIBLE);

                }
            }
        });

        txtAddTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_5OwnerMenu.this,_7_Register_Tenants.class);
                startActivity(intent);
            }
        });

        txtNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_5OwnerMenu.this,_8_Notices.class);
                startActivity(intent);
            }
        });

        txtTenantList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_5OwnerMenu.this,_9_Manage_Tenants.class);
                startActivity(intent);
            }
        });

        txtSignoutOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_5OwnerMenu.this,_3Login.class);
                startActivity(intent);
            }
        });




    }
}
