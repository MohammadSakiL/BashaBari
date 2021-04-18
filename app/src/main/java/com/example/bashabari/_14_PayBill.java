package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class _14_PayBill extends AppCompatActivity {

    private ImageView back;
    private CardView bkash;
    private CardView nagad;
    private CardView visa;
    private CardView rocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__14__pay_bill);

        back = findViewById(R.id.arrow_btn_14);
        bkash = findViewById(R.id.bkashlogo);
        nagad = findViewById(R.id.nagadlogo);
        visa = findViewById(R.id.visalogo);
        rocket = findViewById(R.id.rocketlogo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_14_PayBill.this,_6_User_menu.class);
                startActivity(intent);
            }
        });

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_14_PayBill.this,_15_Bkash.class);
                startActivity(intent);
            }
        });

        nagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_14_PayBill.this,_16_Nagad.class);
                startActivity(intent);
            }
        });

        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_14_PayBill.this, _19_Visa.class);
                startActivity(intent);
            }
        });

        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_14_PayBill.this,_18_Rocket.class);
                startActivity(intent);
            }
        });



    }
}