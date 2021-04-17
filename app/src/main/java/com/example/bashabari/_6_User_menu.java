package com.example.bashabari;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.List;

public class _6_User_menu extends AppCompatActivity {

    LinearLayout main_menu_layout, main_menu_layout_right;
    RelativeLayout home_layout;
    private ImageView menu_btn;
    private ImageView see_more_6;
    private TextView bill_6;
    private TextView request_6, signout_btn, pay_6;
    private TextView name_title, address_title;

    private RecyclerView recyclerView;
    private noticeViewAdapter adapter;
    private List<noticeInfo> noticeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__6_user_menu);



        main_menu_layout = findViewById(R.id.menu_layout_6);
        home_layout = findViewById(R.id.home_layout_6);
        main_menu_layout_right = findViewById(R.id.main_menu_layout_6);
        menu_btn = findViewById(R.id.btnMenu6);
        signout_btn = findViewById(R.id.txtSignoutOwner6);
        request_6 = findViewById(R.id.txtRqst6);
        pay_6=findViewById(R.id.txtpay6);
        see_more_6 = findViewById(R.id.see_more_btn_6);
        name_title= findViewById(R.id.nameTitle_6);
        address_title= findViewById(R.id.addressTitle_6);



        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_menu_layout.setVisibility(View.VISIBLE);
                home_layout.setVisibility(view.GONE);
            }
        });

        main_menu_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(main_menu_layout.getVisibility() == View.VISIBLE){
                    main_menu_layout.setVisibility(View.GONE);
                    home_layout.setVisibility(view.VISIBLE);
                }
            }
        });

        see_more_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6_User_menu.this, _11_More_Notices.class);
                startActivity(intent_6);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        request_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6_User_menu.this, _12_Requests.class);
                startActivity(intent_6);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        pay_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_6 = new Intent(_6_User_menu.this, _14_PayBill.class);
                startActivity(intent_6);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                FileOutputStream fos0 = null;
                try {

                    fos0 = openFileOutput("369sta369.txt", MODE_PRIVATE);
                    fos0.write("logged_out".getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent_06 = new Intent(_6_User_menu.this, _3Login.class);
                startActivity(intent_06);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }
}